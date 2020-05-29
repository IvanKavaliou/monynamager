package net.ivan.kavaliou.moneyman.service;

import net.ivan.kavaliou.moneyman.model.persistence.Currency;
import net.ivan.kavaliou.moneyman.repository.CurrencyRepository;
import net.ivan.kavaliou.moneyman.utils.enums.CurrencyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CurrencyService {

    @Autowired(required=true)
    private CurrencyRepository currencyRepository;

    public List<Currency> getAllCurrency(){
        return currencyRepository.findAll();
    }

    public Optional<Currency> get(CurrencyType type){
        return currencyRepository.findByCurrencyType(type);
    }
}
