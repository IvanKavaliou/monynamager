package net.ivan.kavaliou.moneyman.validation;

import net.ivan.kavaliou.moneyman.forms.RegistrationForm;
import net.ivan.kavaliou.moneyman.service.CurrencyService;
import net.ivan.kavaliou.moneyman.service.UsersService;
import net.ivan.kavaliou.moneyman.utils.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class RegistrationValidator implements Validator {

    @Autowired
    private UsersService usersService;

    @Autowired
    CurrencyService currencyService;

    @Autowired
    Messages messages;

    @Override
    public boolean supports(Class<?> aClass) {
        return RegistrationForm.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        RegistrationForm form = (RegistrationForm) o;
        if (usersService.findByEmail(form.getEmail()).isPresent()){
            errors.rejectValue("email", "", messages.get("error.registration.userExsist"));
        }

        if (!currencyService.get(form.getCurrency()).isPresent()){
            errors.rejectValue("currency", "", messages.get("error.wrongValue"));
        }

        if (!form.getPassword().equals(form.getPasswordRepeat())){
            errors.rejectValue("password", "",messages.get("error.passwordMatch"));
        }

    }
}
