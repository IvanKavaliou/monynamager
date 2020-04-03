package net.ivan.kavaliou.moneyman.controller;

import lombok.extern.slf4j.Slf4j;
import net.ivan.kavaliou.moneyman.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
public class MainController {
    private static final String MAIN_VIEW = "main";

    @Autowired
    TransactionService transactionService;

    @Autowired
    Environment env;

    @GetMapping("/main")
    public String main(@RequestParam(name = "user_id", required = false) Integer user_id, Model model) {

        model.addAttribute("message", env.getProperty("welcome.message"));
        model.addAttribute("transactions", transactionService.getAllByUserId(user_id));
     //   model.addAttribute("currencyList", currencyService.getAllCurrency());
        return MAIN_VIEW; //view
    }
}
