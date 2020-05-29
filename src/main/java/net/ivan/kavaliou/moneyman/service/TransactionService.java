package net.ivan.kavaliou.moneyman.service;

import net.ivan.kavaliou.moneyman.model.persistence.Transaction;
import net.ivan.kavaliou.moneyman.repository.TransactionRepository;
import net.ivan.kavaliou.moneyman.utils.DateTimeUtils;
import net.ivan.kavaliou.moneyman.utils.enums.CurrencyType;
import net.ivan.kavaliou.moneyman.utils.enums.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository repository;

    @Autowired
    UsersService usersService;

    public Optional<Transaction> get(Integer id){
        return repository.findByUserAndId(usersService.getAuthUser(),id);
    }

    public List<Transaction> getAll(){
        return getAll(DateTimeUtils.MIN_DATE, DateTimeUtils.MAX_DATE);
    }

    public List<Transaction> getAll(LocalDateTime startDate, LocalDateTime endDate){
        return repository.findByUserAndDateBetween(usersService.getAuthUser(), startDate, endDate);
    }

    private List<Transaction> getAllByType(TransactionType type, LocalDateTime start, LocalDateTime end) {
        return getAll().stream()
                     .filter(t -> t.getDate().isAfter(start) && t.getDate().isBefore(end))
                     .filter(t -> t.getTransactionCategory().getTransactionType().getTransactionType().equals(type))
                     .collect(Collectors.toList());
    }

    public List<Transaction> getAllIncome() {
        return getAllByType(TransactionType.INCOME, DateTimeUtils.MIN_DATE, DateTimeUtils.MAX_DATE);
    }

    public List<Transaction> getAllExpenses() {
        return getAllByType(TransactionType.EXPENSES, DateTimeUtils.MIN_DATE, DateTimeUtils.MAX_DATE);
    }

    private BigDecimal getAmount(TransactionType type, CurrencyType currency, LocalDateTime start, LocalDateTime end){
        return getAllByType(type, start, end).stream()
                                 .filter(t -> t.getCurrency().getCurrencyType().equals(currency))
                                 .map(t -> t.getValue())
                                 .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getIncomeAmount(CurrencyType currency){
        return getAmount(TransactionType.INCOME, currency, DateTimeUtils.MIN_DATE, DateTimeUtils.MAX_DATE);
    }

    public BigDecimal getExpensesAmount(CurrencyType currency){
        return getAmount(TransactionType.EXPENSES, currency, DateTimeUtils.MIN_DATE, DateTimeUtils.MAX_DATE);
    }

    public BigDecimal getIncomeWeeklyAmount(CurrencyType currency){
        return getAmount(TransactionType.INCOME, currency,LocalDateTime.now().with(LocalTime.MIDNIGHT).minusDays(7) ,LocalDateTime.now().with(LocalTime.MIDNIGHT));
    }

    public BigDecimal getExpensesWeeklyAmount(CurrencyType currency){
        return getAmount(TransactionType.EXPENSES, currency,LocalDateTime.now().with(LocalTime.MIDNIGHT).minusDays(7) ,LocalDateTime.now().with(LocalTime.MIDNIGHT));
    }

    public Transaction add(Transaction transaction){
        transaction.setDate(LocalDateTime.now());
        return update(transaction);
    }

    public boolean delete(Integer id){
        Optional<Transaction> transaction = get(id);
        if (transaction.isPresent()){
            repository.delete(transaction.get());
            return true;
        }
        return false;
    }

    public Transaction update(Transaction transaction){
        transaction.setUser(usersService.getAuthUser());
        return repository.save(transaction);
    }
}
