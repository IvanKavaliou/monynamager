package net.ivan.kavaliou.moneyman.controller.rest;

import net.ivan.kavaliou.moneyman.model.persistence.Transaction;
import net.ivan.kavaliou.moneyman.service.TransactionService;
import net.ivan.kavaliou.moneyman.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TransactionRestController {


    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UsersService usersService;

    @RequestMapping(path="/trnsactions", method= RequestMethod.GET)
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllByUserId(usersService.getAuthUser().getId());
    }
}
