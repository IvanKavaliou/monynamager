package net.ivan.kavaliou.moneyman.service;

import lombok.extern.slf4j.Slf4j;
import net.ivan.kavaliou.moneyman.exceptions.NotFoundException;
import net.ivan.kavaliou.moneyman.exceptions.ServiceException;
import net.ivan.kavaliou.moneyman.model.persistence.Currency;
import net.ivan.kavaliou.moneyman.model.persistence.User;
import net.ivan.kavaliou.moneyman.repository.UsersRepository;
import net.ivan.kavaliou.moneyman.utils.Messages;
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
    Messages messages;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    CurrencyService currencyService;

    @Autowired
    TransactionService transactionService;

    public Currency addCurrency(CurrencyType currencyType){
        User user = getAuthUser();
        Optional<Currency> currency = currencyService.get(currencyType);
        if (currency.isPresent()){
            if(!user.getUserCurrencys().contains(currency.get())){
                user.getUserCurrencys().add(currency.get());
                usersRepository.save(user);
            } else {
                throw new ServiceException(messages.get("error.currency.add.exsist"));
            }
        } else {
            throw new NotFoundException(messages.get("error.currency.notExisit"));
        }
        return currency.isPresent() ? currency.get() : new Currency();
    }

    public boolean deleteCurrency(CurrencyType currencyType){
        User user = getAuthUser();
        Optional<Currency> currency = currencyService.get(currencyType);
        if (currency.isPresent()){
            if(user.getUserCurrencys().contains(currency.get())){
                if (user.getUserCurrencys().size() > 1){
                    if (transactionService.getAllByCurrency(currencyType).size() == 0){
                        user.getUserCurrencys().remove(currency.get());
                        usersRepository.save(user);
                        return true;
                    } else {
                        throw new ServiceException(messages.get("error.currency.delete.transactions"));
                    }
                } else {
                    throw new NotFoundException(messages.get("error.currency.last"));
                }
            } else {
                throw new NotFoundException(messages.get("error.currency.notExisit"));
            }
        }
        return false;
    }

    public Optional<User> findByEmail(String email){
        return usersRepository.findByEmail(email);
    }

    public User getUserByEmail(String email){
        return usersRepository.findOneByEmail(email);
    }

    public Integer getUserIdByEmail(String email){
        return usersRepository.findOneByEmail(email).getId();
    }

    public User save(User user){
        return usersRepository.save(user);
    }

    public User getAuthUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       return getUserByEmail(authentication.getName());
    }
}
