package net.ivan.kavaliou.moneyman.controller.rest;

import lombok.extern.slf4j.Slf4j;
import net.ivan.kavaliou.moneyman.model.persistence.Currency;
import net.ivan.kavaliou.moneyman.service.UsersService;
import net.ivan.kavaliou.moneyman.utils.Messages;
import net.ivan.kavaliou.moneyman.utils.enums.CurrencyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/rest")
public class CurrencyRestController {

    @Autowired
    UsersService usersService;

    @GetMapping(path="/currency/delete/{currency}")
    public Boolean deleteCurrency(@PathVariable CurrencyType currency){
        log.debug("CurrencyController::deleteCurrency {}"+currency);
       return usersService.deleteCurrency(currency);
    }

    @GetMapping(path="/currency/add/{currency}")
    public Currency addCurrency(@PathVariable CurrencyType currency){
        log.debug("CurrencyController::addCurrency {}" + currency);
        return usersService.addCurrency(currency);
    }

}
