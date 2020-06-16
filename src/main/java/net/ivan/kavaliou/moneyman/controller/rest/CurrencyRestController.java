package net.ivan.kavaliou.moneyman.controller.rest;

import lombok.extern.slf4j.Slf4j;
import net.ivan.kavaliou.moneyman.model.persistence.Currency;
import net.ivan.kavaliou.moneyman.service.CurrencyService;
import net.ivan.kavaliou.moneyman.service.UsersService;
import net.ivan.kavaliou.moneyman.utils.enums.CurrencyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/rest")
public class CurrencyRestController {

    @Autowired
    UsersService usersService;

    @Autowired
    CurrencyService currencyService;

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

    @GetMapping("/currency/list")
    public Set<Currency> getCurrencys(){
        return usersService.getAuthUser().getUserCurrencys();
    }

}
