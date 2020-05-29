package net.ivan.kavaliou.moneyman.service;

import net.ivan.kavaliou.moneyman.model.persistence.TransactionCategory;
import net.ivan.kavaliou.moneyman.repository.TransactionCategoryRepository;
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

    public Optional<TransactionCategory> get(Integer id){
        return repository.findByUserAndId(usersService.getAuthUser(), id);
    }

    public boolean delete(Integer id){
        Optional<TransactionCategory> category = get(id);
        if (category.isPresent()){
            repository.delete(category.get());
            return true;
        }
        return false;
    }

    public List<TransactionCategory> getExpenses(){
        return repository.findByUserAndAndTransactionType(usersService.getAuthUser(), transactionTypesService.getExpenses());
    }

    public List<TransactionCategory> getIncomes(){
        return repository.findByUserAndAndTransactionType(usersService.getAuthUser(), transactionTypesService.getIncome());
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
