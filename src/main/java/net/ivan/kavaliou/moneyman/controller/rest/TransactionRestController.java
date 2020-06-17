package net.ivan.kavaliou.moneyman.controller.rest;

import lombok.extern.slf4j.Slf4j;
import net.ivan.kavaliou.moneyman.exceptions.NotFoundException;
import net.ivan.kavaliou.moneyman.exceptions.ServiceException;
import net.ivan.kavaliou.moneyman.forms.AccountForm;
import net.ivan.kavaliou.moneyman.forms.AmountForm;
import net.ivan.kavaliou.moneyman.forms.TransactionCategoryForm;
import net.ivan.kavaliou.moneyman.forms.TransactionForm;
import net.ivan.kavaliou.moneyman.model.persistence.Currency;
import net.ivan.kavaliou.moneyman.model.persistence.Transaction;
import net.ivan.kavaliou.moneyman.service.CurrencyService;
import net.ivan.kavaliou.moneyman.service.TransactionCategoryService;
import net.ivan.kavaliou.moneyman.service.TransactionService;
import net.ivan.kavaliou.moneyman.service.UsersService;
import net.ivan.kavaliou.moneyman.utils.DateTimeUtils;
import net.ivan.kavaliou.moneyman.utils.Messages;
import net.ivan.kavaliou.moneyman.utils.enums.AmountType;
import net.ivan.kavaliou.moneyman.utils.enums.CaseInsensitiveEnumEditor;
import net.ivan.kavaliou.moneyman.utils.enums.CurrencyType;
import net.ivan.kavaliou.moneyman.utils.enums.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/rest")
public class TransactionRestController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private TransactionCategoryService transactionCategoryService;

    @Autowired
    private Messages messages;

    @PostMapping("/transactions")
    public TransactionForm addTransaction(@RequestBody @Valid TransactionForm form){
        LocalDateTime date = LocalDateTime.now();
        try{
            date =  DateTimeUtils.parseDate(form.getDate(), DateTimeUtils.LDT_INPUT_FORMAT);
        } catch (Exception e){
            try{
                date =  DateTimeUtils.parseDate(form.getDate(), DateTimeUtils.LDT_INPUT_FORMAT_WITHOUT_T);
            } catch (Exception ex){throw new ServiceException(messages.get("error.cannot.parse.date"));}
        }

        Transaction t = Transaction.builder()
                .id(form.getId())
                .currency(currencyService.get(form.getCurrencyType()).get())
                .date(date)
                .value(form.getValue())
                .transactionCategory(transactionCategoryService.get(form.getIdTransactionCategory()).get())
                .name(form.getName())
                .build();
        transactionService.add(t);
        form.setId(t.getId());
        return form;
    }

    @GetMapping("/trnsactions")
    public List<TransactionForm> getAllTransactions() {
        log.info("TransactionRestController::getAllTransactions {}", usersService.getAuthUser().getId());
        return convertTransactionsToTransactionsForm(transactionService.getAll());
    }

    @GetMapping("/trnsactions/delete/{id}")
    public boolean delete(@PathVariable Integer id) {
        log.info("TransactionRestController::delete id = {}", id);
        return transactionService.delete(id);
    }

    @GetMapping("/trnsactions/income")
    public List<TransactionForm> getAllIncome() {
        log.info("TransactionRestController::getAllIncome {}", usersService.getAuthUser().getId());
        return convertTransactionsToTransactionsForm(transactionService.getAllIncome());
    }

    @GetMapping("/trnsactions/expenses")
    public List<TransactionForm> getAllExpenses() {
        log.info("TransactionRestController::getAllExpenses {}", usersService.getAuthUser().getId());
        return convertTransactionsToTransactionsForm(transactionService.getAllExpenses());
    }

    @GetMapping("/transactions/accounts")
    public List<AccountForm> getAccounts(){
        List<AccountForm> result = new ArrayList<>();
        usersService.getAuthUser().getUserCurrencys().forEach(currency -> {
            result.add(AccountForm.builder()
                    .currencyType(currency.getCurrencyType())
                    .expenses(getAmount(TransactionType.EXPENSES, currency.getCurrencyType()))
                    .income(getAmount(TransactionType.INCOME, currency.getCurrencyType()))
                    .build());
        });
        return result;
    }

    private List<TransactionForm> convertTransactionsToTransactionsForm(List<Transaction> list){
        List<TransactionForm> transactionFormList = new ArrayList<>();
        list.forEach(t -> transactionFormList.add(TransactionForm.builder()
                .id(t.getId())
                .currencyType(t.getCurrency().getCurrencyType())
                .value(t.getValue())
                .transactionCategory(TransactionCategoryForm.builder()
                        .id(t.getTransactionCategory().getId())
                        .name(t.getTransactionCategory().getName())
                        .transactionType(t.getTransactionCategory().getTransactionType().getTransactionType())
                        .build())
                .name(t.getName())
                .date(DateTimeUtils.parseDate(t.getDate()))
                .build()));
        return  transactionFormList.stream().sorted(Comparator.comparing(TransactionForm::getDate, Comparator.nullsLast(Comparator.reverseOrder()))).collect(Collectors.toList());
    }

    @GetMapping("/amount/{type}/{currency}")
    public AmountForm getAmount(@PathVariable TransactionType type, @PathVariable CurrencyType currency){
        AmountForm result = AmountForm.builder().build();
        Optional<Currency> cur = currencyService.get(currency);
        if (cur.isPresent()) {
            if (usersService.getAuthUser().getUserCurrencys().contains(currencyService.get(currency).get())) {
                if (type.equals(TransactionType.INCOME)) {
                    return AmountForm.builder()
                            .day(transactionService.getIncomeDaylyAmount(currency).toString())
                            .week(transactionService.getIncomeWeeklyAmount(currency).toString())
                            .month(transactionService.getIncomeMonthAmount(currency).toString())
                            .balance(calculatealance(currency).toString())
                            .build();
                }

                if (type.equals(TransactionType.EXPENSES)) {
                   return AmountForm.builder()
                            .day(transactionService.getExpensesDaylyAmount(currency).toString())
                            .week(transactionService.getExpensesWeeklyAmount(currency).toString())
                            .month(transactionService.getExpensesMonthAmount(currency).toString())
                            .balance(calculatealance(currency).toString())
                            .build();
                }
            }
        }
        throw new NotFoundException("Customad asdasdasdasd NOt Found");
    }

    @GetMapping("/amount/balance/{currency}")
    public String getBalance(@PathVariable CurrencyType currency){
        Optional<Currency> cur = currencyService.get(currency);
        if (cur.isPresent()) {
            if (usersService.getAuthUser().getUserCurrencys().contains(currencyService.get(currency).get())) {
                return calculatealance(currency).toString();
            }
        }
        throw new NotFoundException("Currency not found exception");
    }

    private BigDecimal calculatealance(CurrencyType currency){
        return transactionService.getIncomeAmount(currency).subtract(transactionService.getExpensesAmount(currency));
    }

    @GetMapping("/amount/{amount}/{type}/{currency}")
    public String getAmount(@PathVariable AmountType amount,@PathVariable TransactionType type, @PathVariable CurrencyType currency){
        Optional<Currency> cur = currencyService.get(currency);
        if (cur.isPresent()){
            if (usersService.getAuthUser().getUserCurrencys().contains(currencyService.get(currency).get())){
                if (type.equals(TransactionType.INCOME)){
                    if (amount.equals(AmountType.FULL)){
                        return transactionService.getIncomeAmount(currency).toString();
                    }
                    if (amount.equals(AmountType.WEEK)){
                        return transactionService.getIncomeWeeklyAmount(currency).toString();
                    }

                    if (amount.equals(AmountType.MONTH)){
                        return transactionService.getIncomeMonthAmount(currency).toString();
                    }
                }
                if (type.equals(TransactionType.EXPENSES)){
                    if (amount.equals(AmountType.FULL)){
                        return transactionService.getExpensesAmount(currency).toString();
                    }
                    if (amount.equals(AmountType.WEEK)){
                        return transactionService.getExpensesWeeklyAmount(currency).toString();
                    }

                    if (amount.equals(AmountType.MONTH)){
                        return transactionService.getExpensesMonthAmount(currency).toString();
                    }
                }
            }
        }
       throw new NotFoundException("Not found ex");
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(TransactionType.class, new CaseInsensitiveEnumEditor(TransactionType.class));
        binder.registerCustomEditor(CurrencyType.class, new CaseInsensitiveEnumEditor(CurrencyType.class));
        binder.registerCustomEditor(AmountType.class, new CaseInsensitiveEnumEditor(AmountType.class));
    }

}
