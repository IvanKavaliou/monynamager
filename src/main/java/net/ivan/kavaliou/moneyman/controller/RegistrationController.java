package net.ivan.kavaliou.moneyman.controller;

import lombok.extern.slf4j.Slf4j;
import net.ivan.kavaliou.moneyman.forms.RegistrationForm;
import net.ivan.kavaliou.moneyman.model.persistence.User;
import net.ivan.kavaliou.moneyman.service.UsersService;
import net.ivan.kavaliou.moneyman.utils.ControllerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Map;

@Slf4j
@Controller
public class RegistrationController {


    @Autowired
    Environment env;

    @Autowired
    UsersService usersService;

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @PostMapping("/registration")
    public String registrUser(@Valid RegistrationForm form, BindingResult bindingResult, Model model){
        log.info("RegistrationController::registrUser {}", form);

        if (!form.getPassword().equals(form.getPasswordRepeat())){
            model.addAttribute("errorPasswordMatch", "error.passwordMatch");
            return "/registration";
        }

        if(bindingResult.hasErrors()){
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);
            return "/registration";
        }

     //   usersService.registerUser(user, model);
        if (model.getAttribute("error") != null){
  //          log.info("RegistrationController::registrUser - Error {}, {}", model.get("error"), user);
            return "/registration";
        }
        return "redirect:/login";
    }
}
