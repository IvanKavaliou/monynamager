package net.ivan.kavaliou.moneyman.service;

import net.ivan.kavaliou.moneyman.model.persistence.TransactionCategory;
import net.ivan.kavaliou.moneyman.repository.TransactionCategoryRepository;
import net.ivan.kavaliou.moneyman.utils.enums.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionCategoryService {

    @Autowired
    private TransactionCategoryRepository repository;

    @Autowired
    UsersService usersService;

    @Autowired
    TransactionTypesService transactionTypesService;

    public List<TransactionCategory> getExpenses(){
        return repository.findByUserAndAndTransactionType(usersService.getAuthUser(), transactionTypesService.getExpenses());
    }

    public List<TransactionCategory> getIncomes(){
        return repository.findByUserAndAndTransactionType(usersService.getAuthUser(), transactionTypesService.getIncome());
    }

    public TransactionCategory add(TransactionCategory transactionCategory){
        transactionCategory.setUser(usersService.getAuthUser());
        return repository.save(transactionCategory);
    }
}
