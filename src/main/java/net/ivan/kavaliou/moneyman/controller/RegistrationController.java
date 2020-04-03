package net.ivan.kavaliou.moneyman.controller;

import lombok.extern.slf4j.Slf4j;
import net.ivan.kavaliou.moneyman.model.persistence.User;
import net.ivan.kavaliou.moneyman.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Slf4j
@Controller
public class RegistrationController {


    @Autowired
    UsersService usersService;

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @PostMapping("/registration")
    public String registrUser(User user, Map<String, Object> model){
        log.info("RegistrationController::registrUser {}", user);
        usersService.registerUser(user, model);
        if (model.get("errors") != null){
            log.info("RegistrationController::registrUser - Error {}, {}",model.get("error"), user);
            return "/registration";
        }
        return "redirect:/login";
    }
}
