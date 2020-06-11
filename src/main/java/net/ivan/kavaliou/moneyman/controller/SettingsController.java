package net.ivan.kavaliou.moneyman.controller;

import lombok.extern.slf4j.Slf4j;
import net.ivan.kavaliou.moneyman.model.persistence.User;
import net.ivan.kavaliou.moneyman.service.CurrencyService;
import net.ivan.kavaliou.moneyman.service.TransactionCategoryService;
import net.ivan.kavaliou.moneyman.service.TransactionService;
import net.ivan.kavaliou.moneyman.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class SettingsController {
    private static final String VIEW = "settings";

    @Autowired
    TransactionService tService;

    @Autowired
    UsersService usersService;

    @Autowired
    TransactionCategoryService tCategoryService;

    @Autowired
    CurrencyService currencyService;

    @GetMapping("/settings")
    public String main(Authentication auth, Model model) {
        log.info("SettingsController::main");
        User user = usersService.getAuthUser();
        model.addAttribute("user", user);
        return VIEW; //view
    }
}