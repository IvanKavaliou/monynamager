package net.ivan.kavaliou.moneyman.service;

import net.ivan.kavaliou.moneyman.model.persistence.Currency;
import net.ivan.kavaliou.moneyman.utils.enums.CurrencyType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class CurrencyServiceTest {

    @Autowired
    private CurrencyService currencyService;

    @Test
    public void getAll(){
        List<Currency> allCurrency = currencyService.getAllCurrency();
        System.out.println(allCurrency);
    }

    @Test
    public void getByTypeTest(){
        Currency usd = currencyService.get(CurrencyType.USD).get();
        Currency rub = currencyService.get(CurrencyType.RUB).get();
        assertTrue(usd.getCurrencyType() == CurrencyType.USD);
        assertTrue(rub.getCurrencyType() == CurrencyType.RUB);
    }
}
