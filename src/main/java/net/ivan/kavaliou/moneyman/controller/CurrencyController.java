package net.ivan.kavaliou.moneyman.controller;

import lombok.extern.slf4j.Slf4j;
import net.ivan.kavaliou.moneyman.service.UsersService;
import net.ivan.kavaliou.moneyman.utils.enums.AmountType;
import net.ivan.kavaliou.moneyman.utils.enums.CaseInsensitiveEnumEditor;
import net.ivan.kavaliou.moneyman.utils.enums.CurrencyType;
import net.ivan.kavaliou.moneyman.utils.enums.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class CurrencyController {

    @Autowired
    UsersService usersService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(CurrencyType.class, new CaseInsensitiveEnumEditor(CurrencyType.class));
    }

    @GetMapping(path="/currency/delete/{currency}")
    public String deleteCurrency(@PathVariable CurrencyType currency, Model model){
        log.debug("CurrencyController::deleteCurrency {}"+currency);
        String error = new String();
        if(usersService.deleteCurrency(currency)){
            return "redirect:/main";
        }
        log.debug("CurrencyController::deleteCurrency error {}"+error);
        model.addAttribute("error", error);
        return "redirect:/main?deleteCurrencyError=true";
    }
}
