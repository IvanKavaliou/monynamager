package net.ivan.kavaliou.moneyman.controller.rest;

import lombok.extern.slf4j.Slf4j;
import net.ivan.kavaliou.moneyman.exceptions.NotFoundException;
import net.ivan.kavaliou.moneyman.model.persistence.Currency;
import net.ivan.kavaliou.moneyman.model.persistence.Transaction;
import net.ivan.kavaliou.moneyman.service.CurrencyService;
import net.ivan.kavaliou.moneyman.service.TransactionService;
import net.ivan.kavaliou.moneyman.service.UsersService;
import net.ivan.kavaliou.moneyman.utils.enums.AmountType;
import net.ivan.kavaliou.moneyman.utils.enums.CaseInsensitiveEnumEditor;
import net.ivan.kavaliou.moneyman.utils.enums.CurrencyType;
import net.ivan.kavaliou.moneyman.utils.enums.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
public class TransactionRestController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private CurrencyService currencyService;

    @RequestMapping(path="/trnsactions", method= RequestMethod.GET)
    public List<Transaction> getAllTransactions() {
        log.info("TransactionRestController::getAllTransactions {}", usersService.getAuthUser().getId());
        return transactionService.getAll();
    }

    @RequestMapping(path="/trnsactions/income", method= RequestMethod.GET)
    public List<Transaction> getAllIncome() {
        log.info("TransactionRestController::getAllIncome {}", usersService.getAuthUser().getId());
        return transactionService.getAllIncome();
    }

    @RequestMapping(path="/trnsactions/expenses", method= RequestMethod.GET)
    public List<Transaction> getAllExpenses() {
        log.info("TransactionRestController::getAllExpenses {}", usersService.getAuthUser().getId());
        return transactionService.getAllExpenses();
    }

    @RequestMapping(path="/amount/{amount}/{type}/{currency}", method= RequestMethod.GET)
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
                }
                if (type.equals(TransactionType.EXPENSES)){
                    if (amount.equals(AmountType.FULL)){
                        return transactionService.getExpensesAmount(currency).toString();
                    }
                    if (amount.equals(AmountType.WEEK)){
                        return transactionService.getExpensesWeeklyAmount(currency).toString();
                    }
                }
            }
        }
       throw new NotFoundException();
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(TransactionType.class, new CaseInsensitiveEnumEditor(TransactionType.class));
        binder.registerCustomEditor(CurrencyType.class, new CaseInsensitiveEnumEditor(CurrencyType.class));
        binder.registerCustomEditor(AmountType.class, new CaseInsensitiveEnumEditor(AmountType.class));
    }

}
