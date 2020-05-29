package net.ivan.kavaliou.moneyman.service;

import lombok.extern.slf4j.Slf4j;
import net.ivan.kavaliou.moneyman.model.persistence.Currency;
import net.ivan.kavaliou.moneyman.model.persistence.User;
import net.ivan.kavaliou.moneyman.repository.UsersRepository;
import net.ivan.kavaliou.moneyman.utils.enums.CurrencyType;
import net.ivan.kavaliou.moneyman.utils.enums.UserRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import java.util.Collections;
import java.util.Optional;

@Slf4j
@Service
public class UsersService {

    @Autowired
    Environment env;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    CurrencyService currencyService;

    public Optional<Currency> addCurrency(CurrencyType currencyType){
        User user = getAuthUser();
        Optional<Currency> currency = currencyService.get(currencyType);
        if (currency.isPresent()){
            if(!user.getUserCurrencys().contains(currency.get())){
                user.getUserCurrencys().add(currency.get());
                usersRepository.save(user);
            }
        }
        return currency;
    }

    public boolean deleteCurrency(CurrencyType currencyType){
        User user = getAuthUser();
        Optional<Currency> currency = currencyService.get(currencyType);
        if (currency.isPresent()){
            if(user.getUserCurrencys().contains(currency.get())){
                user.getUserCurrencys().remove(currency.get());
                usersRepository.save(user);
                return true;
            }
        }
        return false;
    }

    public User registerUser(User user, ModelMap model){
        log.info("UsersService::registrUser - {}", user);
        if (usersRepository.findByEmail(user.getEmail())!= null){
            model.put("error", env.getProperty("error.registration.userExsist"));
            return user;
        }

        user.setEnabled(true);
        user.setRoles(Collections.singleton(UserRoles.USER));
        user.setCurrency(Currency.builder().id(100000).currencyType(CurrencyType.USD).build());
        user.setUserCurrencys(Collections.singleton(Currency.builder().id(100000).currencyType(CurrencyType.USD).build()));
        log.info("UsersService::registrUser - {}", user);
        return usersRepository.save(user);
    }

    public User getUserByEmail(String email){
        return usersRepository.findOneByEmail(email);
    }

    public Integer getUserIdByEmail(String email){
        return usersRepository.findOneByEmail(email).getId();
    }

    public User update(User user){
        return usersRepository.save(user);
    }

    public User getAuthUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       return getUserByEmail(authentication.getName());
    }
}
