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
    private TransactionTypesService tTypesService;

   /* public List<TransactionCategory> getExpensesCategorys(Integer userId){
        return repository.findByIdTransactionTypesAndIdUsers(tTypesService.get(TransactionType.EXPENSES).getId(), userId);
    }*/
}
