package net.ivan.kavaliou.moneyman.service;

import net.ivan.kavaliou.moneyman.model.persistence.Transaction;
import net.ivan.kavaliou.moneyman.repository.TransactionRepository;
import net.ivan.kavaliou.moneyman.utils.enums.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository repository;

    @Autowired
    UsersService usersService;

    public List<Transaction> getAll(){
        return repository.getAllByIdUsers(usersService.getAuthUser().getId());
    }

    private List<Transaction> getAllByType(TransactionType type) {
        List<Transaction> result = getAll();
        return result.stream()
                .filter(t -> t.getTransactionCategory().getTransactionType().getTransactionType().equals(type))
                .collect(Collectors.toList());
    }

    public List<Transaction> getAllIncome() {
        return getAllByType(TransactionType.INCOME);
    }

    public List<Transaction> getAllExpenses() {
        return getAllByType(TransactionType.EXPENSES);
    }

}
