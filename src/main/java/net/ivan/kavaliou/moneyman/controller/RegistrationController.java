package net.ivan.kavaliou.moneyman.controller;

import lombok.extern.slf4j.Slf4j;
import net.ivan.kavaliou.moneyman.forms.RegistrationForm;
import net.ivan.kavaliou.moneyman.model.persistence.User;
import net.ivan.kavaliou.moneyman.service.CurrencyService;
import net.ivan.kavaliou.moneyman.service.RegistrationService;
import net.ivan.kavaliou.moneyman.service.UsersService;
import net.ivan.kavaliou.moneyman.utils.ControllerUtils;
import net.ivan.kavaliou.moneyman.validation.RegistrationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Map;

@Slf4j
@Controller
public class RegistrationController {

    @Autowired
    RegistrationService registrationService;

    @Autowired
    CurrencyService currencyService;

    @Autowired
    RegistrationValidator validator;


    @GetMapping("/registration")
    public String registration(RegistrationForm registrationForm, Model model){
        model.addAttribute("currencyList",currencyService.getAllCurrency());
        return "registration";
    }

    @PostMapping("/registration")
    public String registrUser(@Valid RegistrationForm form, BindingResult bindingResult, Model model){
        validator.validate(form, bindingResult);
        log.info("RegistrationController::registrUser {}", form);
        if(bindingResult.hasErrors()){
            model.addAttribute("currencyList",currencyService.getAllCurrency());
            return "/registration";
        }

        registrationService.registerUser(form);
        return "redirect:/login?reg=true";
    }
}
