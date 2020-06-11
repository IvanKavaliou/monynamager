package net.ivan.kavaliou.moneyman.service;

import net.ivan.kavaliou.moneyman.exceptions.NotFoundException;
import net.ivan.kavaliou.moneyman.exceptions.ServiceException;
import net.ivan.kavaliou.moneyman.model.persistence.TransactionCategory;
import net.ivan.kavaliou.moneyman.repository.TransactionCategoryRepository;
import net.ivan.kavaliou.moneyman.utils.Messages;
import net.ivan.kavaliou.moneyman.utils.enums.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionCategoryService {

    @Autowired
    private TransactionCategoryRepository repository;

    @Autowired
    UsersService usersService;

    @Autowired
    TransactionTypesService transactionTypesService;

    @Autowired
    TransactionService transactionService;

    @Autowired
    Messages messages;

    public Optional<TransactionCategory> get(Integer id){
        return repository.findByUserAndId(usersService.getAuthUser(), id);
    }

    public boolean delete(Integer id){
        Optional<TransactionCategory> category = get(id);
        if (category.isPresent()){
            if (transactionService.getAllByCategory(category.get()).size() == 0){
                repository.delete(category.get());
                return true;
            }
            throw new ServiceException(messages.get("error.category.transactions.exsist"));
        }
        throw new NotFoundException(messages.get("error.category.notFound"));
    }

    public List<TransactionCategory> getExpenses(){
        return repository.findByUserAndAndTransactionType(usersService.getAuthUser(), transactionTypesService.getExpenses());
    }

    public List<TransactionCategory> getIncomes(){
        return repository.findByUserAndAndTransactionType(usersService.getAuthUser(), transactionTypesService.getIncome());
    }

    public TransactionCategory add(TransactionCategory transactionCategory){
        if (repository.findOneByUserAndAndTransactionTypeAndName(usersService.getAuthUser(),transactionCategory.getTransactionType(),transactionCategory.getName()).isPresent()){
            throw new ServiceException("error.category.exsist");
        }
        return save(transactionCategory);
    }

    public TransactionCategory save(TransactionCategory transactionCategory){
        transactionCategory.setUser(usersService.getAuthUser());
        return repository.save(transactionCategory);
    }

    public TransactionCategory saveIncomeCategory(TransactionCategory transactionCategory){
        transactionCategory.setTransactionType(transactionTypesService.getIncome());
        return save(transactionCategory);
    }

    public TransactionCategory saveExpensesCategory(TransactionCategory transactionCategory){
        transactionCategory.setTransactionType(transactionTypesService.getIncome());
        return save(transactionCategory);
    }
}
