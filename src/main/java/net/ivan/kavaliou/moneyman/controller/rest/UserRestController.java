package net.ivan.kavaliou.moneyman.controller.rest;

import lombok.extern.slf4j.Slf4j;
import net.ivan.kavaliou.moneyman.exceptions.ServiceException;
import net.ivan.kavaliou.moneyman.forms.PasswordChangeForm;
import net.ivan.kavaliou.moneyman.service.UsersService;
import net.ivan.kavaliou.moneyman.utils.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Slf4j
@RestController
@RequestMapping("/rest")
public class UserRestController {

    @Autowired
    UsersService usersService;

    @Autowired
    Messages messages;

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
}
