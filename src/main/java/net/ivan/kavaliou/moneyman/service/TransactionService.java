package net.ivan.kavaliou.moneyman.service;

import net.ivan.kavaliou.moneyman.exceptions.NotFoundException;
import net.ivan.kavaliou.moneyman.model.persistence.Currency;
import net.ivan.kavaliou.moneyman.model.persistence.Transaction;
import net.ivan.kavaliou.moneyman.model.persistence.TransactionCategory;
import net.ivan.kavaliou.moneyman.repository.TransactionRepository;
import net.ivan.kavaliou.moneyman.utils.DateTimeUtils;
import net.ivan.kavaliou.moneyman.utils.Messages;
import net.ivan.kavaliou.moneyman.utils.enums.CurrencyType;
import net.ivan.kavaliou.moneyman.utils.enums.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
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

    @Autowired
    CurrencyService currencyService;

    @Autowired
    Messages messages;

    public Optional<Transaction> get(Integer id){
        return repository.findByUserAndId(usersService.getAuthUser(),id);
    }

    public List<Transaction> getAll(){
        return getAll(DateTimeUtils.MIN_DATE, DateTimeUtils.MAX_DATE);
    }

    public List<Transaction> getAll(LocalDateTime startDate, LocalDateTime endDate){
        return repository.findByUserAndDateBetween(usersService.getAuthUser(), startDate, endDate);
    }

    public List<Transaction> getAllByCurrency(CurrencyType currency){
        List<Transaction> result = new ArrayList<>();
        Optional<Currency> cur = currencyService.get(currency);
        if (cur.isPresent()){
            result =  repository.findByUserAndCurrency(usersService.getAuthUser(), cur.get());
        } else {
            throw new NotFoundException(messages.get("error.currency.notExisit"));
        }
        return result;
    }

    public List<Transaction> getAllByCategory(TransactionCategory tc){
        return repository.findByUserAndTransactionCategory(usersService.getAuthUser(), tc);
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

    public BigDecimal getIncomeDaylyAmount(CurrencyType currency){
        return getAmount(TransactionType.INCOME, currency, LocalDate.now().atTime(LocalTime.MIN),  LocalDate.now().atTime(LocalTime.MAX));
    }

    public BigDecimal getExpensesDaylyAmount(CurrencyType currency){
        return getAmount(TransactionType.EXPENSES, currency, LocalDate.now().atTime(LocalTime.MIN),  LocalDate.now().atTime(LocalTime.MAX));
    }

    public BigDecimal getIncomeAmount(CurrencyType currency){
        return getAmount(TransactionType.INCOME, currency, DateTimeUtils.MIN_DATE, DateTimeUtils.MAX_DATE);
    }

    public BigDecimal getExpensesAmount(CurrencyType currency){
        return getAmount(TransactionType.EXPENSES, currency, DateTimeUtils.MIN_DATE, DateTimeUtils.MAX_DATE);
    }

    public BigDecimal getIncomeWeeklyAmount(CurrencyType currency){
        return getAmount(TransactionType.INCOME, currency,LocalDateTime.now().with(LocalTime.MIDNIGHT).minusDays(7) ,LocalDateTime.now().plusDays(1).with(LocalTime.MIDNIGHT));
    }

    public BigDecimal getExpensesWeeklyAmount(CurrencyType currency){
        return getAmount(TransactionType.EXPENSES, currency,LocalDateTime.now().with(LocalTime.MIDNIGHT).minusDays(7) ,LocalDateTime.now().plusDays(1).with(LocalTime.MIDNIGHT));
    }

    public BigDecimal getIncomeMonthAmount(CurrencyType currency){
        LocalDateTime.now().getMonth().maxLength();
        return getAmount(TransactionType.INCOME, currency,
                            DateTimeUtils.getStartOfMount(),
                            DateTimeUtils.getEndOfMount());
    }

    public BigDecimal getExpensesMonthAmount(CurrencyType currency){
        return getAmount(TransactionType.EXPENSES, currency,
                DateTimeUtils.getStartOfMount(),
                DateTimeUtils.getEndOfMount());
    }

    public Transaction add(Transaction transaction){
       // transaction.setDate(LocalDateTime.now());
        return update(transaction);
    }

    public boolean delete(Integer id){
        Optional<Transaction> transaction = get(id);
        if (transaction.isPresent()){
            repository.delete(transaction.get());
            return true;
        }
        throw new NotFoundException(messages.get("error.trnsaction.notFound"));
    }

    public Transaction update(Transaction transaction){
        transaction.setUser(usersService.getAuthUser());
        return repository.save(transaction);
    }
}
