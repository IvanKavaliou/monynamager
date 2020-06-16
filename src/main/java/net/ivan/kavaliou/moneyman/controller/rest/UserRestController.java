package net.ivan.kavaliou.moneyman.controller.rest;

import lombok.extern.slf4j.Slf4j;
import net.ivan.kavaliou.moneyman.exceptions.NotFoundException;
import net.ivan.kavaliou.moneyman.exceptions.ServiceException;
import net.ivan.kavaliou.moneyman.forms.LoginForm;
import net.ivan.kavaliou.moneyman.forms.PasswordChangeForm;
import net.ivan.kavaliou.moneyman.forms.RegistrationForm;
import net.ivan.kavaliou.moneyman.model.persistence.User;
import net.ivan.kavaliou.moneyman.service.RegistrationService;
import net.ivan.kavaliou.moneyman.service.UsersService;
import net.ivan.kavaliou.moneyman.utils.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/rest")
public class UserRestController {

    @Autowired
    UsersService usersService;

    @Autowired
    Messages messages;

    @Autowired
    RegistrationService registrationService;

    @PostMapping("/change/email")
    @ResponseStatus(HttpStatus.OK)
    public String changeEmail(@RequestBody @Valid @NotBlank(message = "error.notBlank") String email){
        log.debug("UserRestController::changeEmail user = {}, email = {}", usersService.getAuthUser(), email);
        if (email.trim().toLowerCase().equals(usersService.getAuthUser())){
            throw new ServiceException("error.email.nochange");
        }
        return usersService.changeEmail(email).getEmail();
    }

    @PostMapping("/change/password")
    @ResponseStatus(HttpStatus.OK)
    public void changePassword(@RequestBody @Valid PasswordChangeForm form){
        log.debug("UserRestController::changePassword user = {}, form = {}", usersService.getAuthUser(), form);
        usersService.chanhePassword(form);
    }

    @PostMapping("/login")
    public User login(@RequestBody @Valid LoginForm form){
        log.debug("UserRestController::login form = {}", form);
        Optional<User> user = usersService.findByEmail(form.getEmail());
        if (user.isPresent()){
            if (user.get().getPassword().equals(form.getPassword())){
                return user.get();
            }
        }
        throw new ServiceException(messages.get("error.invalidLogin"));
    }

    @PostMapping("/registration")
    public User register(@RequestBody @Valid RegistrationForm form){
        log.debug("UserRestController::register form = {}", form);

        Optional<User> user = usersService.findByEmail(form.getEmail());
        if (user.isPresent()){
            throw new ServiceException(messages.get("error.registration.userExsist"));
        }
        if (!form.getPassword().equals(form.getPasswordRepeat())){
            throw new ServiceException(messages.get("error.passwordMatch"));
        }
        return registrationService.registerUser(form);
    }
}
