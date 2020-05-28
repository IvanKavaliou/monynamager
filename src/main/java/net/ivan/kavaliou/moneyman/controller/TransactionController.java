package net.ivan.kavaliou.moneyman.controller;

import lombok.extern.slf4j.Slf4j;
import net.ivan.kavaliou.moneyman.forms.TransactionAddForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Slf4j
@Controller
public class TransactionController {

    @PostMapping("/transaction/add")
    public String addTransaction(@Valid TransactionAddForm form, BindingResult bindingResult, Model model) {
        log.info("TransactionController::addTransaction form={}", form);

        return "main";
    }
}