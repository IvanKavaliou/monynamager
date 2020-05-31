package net.ivan.kavaliou.moneyman.service;

import lombok.extern.slf4j.Slf4j;
import net.ivan.kavaliou.moneyman.forms.RegistrationForm;
import net.ivan.kavaliou.moneyman.model.persistence.Currency;
import net.ivan.kavaliou.moneyman.model.persistence.User;
import net.ivan.kavaliou.moneyman.repository.UsersRepository;
import net.ivan.kavaliou.moneyman.utils.enums.UserRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
public class RegistrationService {
    @Autowired
    Environment env;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    CurrencyService currencyService;

    public User registerUser(RegistrationForm form){
        log.info("RegistrationService::registrUser - {}", form);
        User user = new User();
        user.setEmail(form.getEmail());
        user.setPassword(form.getPassword());
        user.setRoles(Collections.singleton(UserRoles.USER));
        Currency currency = currencyService.get(form.getCurrency()).get();
        user.setCurrency(currency);
        Set<Currency> currencySet = new HashSet<>();
        currencySet.add(currency);
        user.setUserCurrencys(currencySet);
        log.info("UsersService::registrUser - {}", user);
        return usersRepository.save(user);
    }
}
