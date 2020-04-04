package net.ivan.kavaliou.moneyman.service;

import lombok.extern.slf4j.Slf4j;
import net.ivan.kavaliou.moneyman.model.persistence.Currency;
import net.ivan.kavaliou.moneyman.model.persistence.User;
import net.ivan.kavaliou.moneyman.repository.UsersRepository;
import net.ivan.kavaliou.moneyman.utils.enums.CurrencyType;
import net.ivan.kavaliou.moneyman.utils.enums.UserRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;


import java.util.Collections;
import java.util.Map;

@Slf4j
@Service
public class UsersService {

    @Autowired
    Environment env;

    @Autowired
    UsersRepository usersRepository;

    public User registerUser(User user, Map<String, Object> model){
        log.info("UsersService::registrUser - {}", user);
        if (usersRepository.findByEmail(user.getEmail())!= null){
            model.put("error", env.getProperty("error.registration.userExsist"));
            return user;
        }

        user.setEnabled(true);
        user.setRoles(Collections.singleton(UserRoles.USER));
        user.setCurrency(Currency.builder().id(1).currencyType(CurrencyType.USD).build());
        log.info("UsersService::registrUser - {}", user);
        return usersRepository.save(user);
    }

    public User getUserByEmail(String email){
        return usersRepository.findOneByEmail(email);
    }

    public Integer getUserIdByEmail(String email){
        return usersRepository.findOneByEmail(email).getId();
    }
}
