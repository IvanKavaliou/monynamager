package net.ivan.kavaliou.moneyman.controller.rest;

import lombok.extern.slf4j.Slf4j;
import net.ivan.kavaliou.moneyman.model.persistence.Transaction;
import net.ivan.kavaliou.moneyman.service.TransactionService;
import net.ivan.kavaliou.moneyman.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class TransactionRestController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UsersService usersService;

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
}
