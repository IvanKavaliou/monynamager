package net.ivan.kavaliou.moneyman.service;

import net.ivan.kavaliou.moneyman.model.persistence.Transaction;
import net.ivan.kavaliou.moneyman.utils.enums.CurrencyType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Sql(scripts = "classpath:db/populatedb.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class TransactionServiceTest {

    @Autowired
    TransactionService service;

    @Test
    @WithMockUser("user@user.ru")
    public void getAllIncomeTest(){
        List<Transaction> incoms = service.getAllIncome();
        incoms.stream().forEach(System.out::println);
    }

    @Test
    @WithMockUser("user@user.ru")
    public void getAllExpensesTest(){
        List<Transaction> expenses = service.getAllExpenses();
        expenses.stream().forEach(System.out::println);
    }

    @Test
    @WithMockUser("user@user.ru")
    public void getAmountTest(){
        assertEquals(service.getExpensesAmount(CurrencyType.USD).compareTo(BigDecimal.valueOf(155)),0);
        assertEquals(service.getIncomeAmount(CurrencyType.USD).compareTo(BigDecimal.valueOf(3200)),0);
    }

    @Test
    @WithMockUser("user@user.ru")
    public void getWeeklyAmountTest(){
        System.out.println("================================Expenses: "+service.getExpensesWeeklyAmount(CurrencyType.USD));
        System.out.println("================================Income: "+service.getIncomeWeeklyAmount(CurrencyType.USD));
    }

    @Test
    @WithMockUser("user@user.ru")
    public void deleteTest(){
        int trans_size_before = service.getAll().size();
        assertTrue(service.delete(100017));
        int trans_size_after = service.getAll().size();
        assertEquals(trans_size_after, (trans_size_before - 1));
    }

    @Test
    @WithMockUser("user@user.ru")
    public void deleteAnotherUserTest(){
        int trans_size_before = service.getAll().size();
        assertFalse(service.delete(100023));
        int trans_size_after = service.getAll().size();
        assertEquals(trans_size_after, trans_size_before);
    }

    @Test
    @WithMockUser("user@user.ru")
    public void updateTransaNameTest(){
        Transaction transaction = service.get(100017).get();
        String new_name = "New trans name";
        transaction.setName(new_name);
        service.update(transaction);
        Transaction transaction_after_update = service.get(100017).get();
        assertEquals(new_name, transaction_after_update.getName());
    }

    @Test
    @WithMockUser("user@user.ru")
    public void updateTransaNameOtherUserTest(){
        Transaction transaction = service.get(100017).get();
        String new_name = "New trans name";
        transaction.setId(100023);
        transaction.setName(new_name);
        service.update(transaction);
        Transaction transaction_after_update = service.get(100017).get();
        assertNotEquals(new_name, transaction_after_update.getName());
    }

}