package net.ivan.kavaliou.moneyman.controller;

import lombok.extern.slf4j.Slf4j;
import net.ivan.kavaliou.moneyman.forms.TransactionForm;
import net.ivan.kavaliou.moneyman.validation.TransactionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Slf4j
@Controller
public class TransactionController {

    @Autowired
    TransactionValidator validator;

    @PostMapping("/transaction/add")
    public String addTransaction( @Valid TransactionForm form, BindingResult bindingResult, Model model) {
        validator.validate(form, bindingResult);
        log.info("TransactionController::addTransaction form={}", form);
        return "redirect:/main";
    }
}