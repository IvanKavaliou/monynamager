package net.ivan.kavaliou.moneyman.service;

import net.ivan.kavaliou.moneyman.model.persistence.TransactionTypes;
import net.ivan.kavaliou.moneyman.repository.TransactionTypesRepository;
import net.ivan.kavaliou.moneyman.utils.enums.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionTypesService {

    @Autowired
    TransactionTypesRepository repository;

    private TransactionTypes get(TransactionType type){
        return repository.findByTransactionType(type);
    }

    public TransactionTypes getIncome(){
        return get(TransactionType.INCOME);
    }

    public TransactionTypes getExpenses(){
        return get(TransactionType.EXPENSES);
    }
}
