package net.ivan.kavaliou.moneyman.controller;

import lombok.extern.slf4j.Slf4j;
import net.ivan.kavaliou.moneyman.model.persistence.User;
import net.ivan.kavaliou.moneyman.service.TransactionService;
import net.ivan.kavaliou.moneyman.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
public class MainController {
    private static final String MAIN_VIEW = "main";

    @Autowired
    TransactionService transactionService;

    @Autowired
    UsersService usersService;

    @Autowired
    Environment env;

    @GetMapping("/main")
    public String main(Authentication auth, Model model) {
        log.info("MainController::main");
        User user = usersService.getAuthUser();
        model.addAttribute("message", env.getProperty("welcome.message"));
        model.addAttribute("user", user);
        model.addAttribute("transactions", transactionService.getAllByUserId(user.getId()));
        return MAIN_VIEW; //view
    }
}
