package net.ivan.kavaliou.moneyman.service;

import net.ivan.kavaliou.moneyman.model.persistence.Currency;
import net.ivan.kavaliou.moneyman.model.persistence.User;
import net.ivan.kavaliou.moneyman.utils.enums.CurrencyType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Sql(scripts = "classpath:db/populatedb.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class UsersServiceTest {

    @Autowired
    UsersService service;

    @Autowired
    CurrencyService currencyService;

    @Test
    @WithMockUser("admin@admin.ru")
    public void addCurrencyTest(){
        int size_before = service.getAuthUser().getUserCurrencys().size();
        service.addCurrency(CurrencyType.PLN);
        int size_after = service.getAuthUser().getUserCurrencys().size();
        assertTrue(size_before != size_after);
    }

    @Test
    @WithMockUser("admin@admin.ru")
    public void deleteCurrencyTest(){
        int size_before = service.getAuthUser().getUserCurrencys().size();
        service.deleteCurrency(CurrencyType.USD);
        int size_after = service.getAuthUser().getUserCurrencys().size();
        assertEquals(size_after, (size_before - 1));
    }

    @Test
    @WithMockUser("admin@admin.ru")
    public void deleteNotExsisCurrencyTest(){
        int size_before = service.getAuthUser().getUserCurrencys().size();
        assertFalse(service.deleteCurrency(CurrencyType.BYN));
        int size_after = service.getAuthUser().getUserCurrencys().size();
        assertEquals(size_after, size_before);
    }

    @Test
    @WithMockUser("admin@admin.ru")
    public void updateUserCurrencyTest(){
        User user = service.getAuthUser();
        Currency currency_before = user.getCurrency();
        assertEquals(currency_before.getId(), currencyService.get(CurrencyType.USD).get().getId());
        user.setCurrency(currencyService.get(CurrencyType.EUR).get());
        service.save(user);
        assertEquals(service.getAuthUser().getCurrency().getId(), currencyService.get(CurrencyType.EUR).get().getId());
    }

}