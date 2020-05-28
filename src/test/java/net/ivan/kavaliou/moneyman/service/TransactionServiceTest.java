package net.ivan.kavaliou.moneyman.service;

import net.ivan.kavaliou.moneyman.model.persistence.Transaction;
import net.ivan.kavaliou.moneyman.utils.enums.CurrencyType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import java.util.List;


@SpringBootTest
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
        System.out.println("================================Expenses: "+service.getExpensesAmount(CurrencyType.USD));
        System.out.println("================================Income: "+service.getIncomeAmount(CurrencyType.USD));
    }

    @Test
    @WithMockUser("user@user.ru")
    public void getWeeklyAmountTest(){
        System.out.println("================================Expenses: "+service.getExpensesWeeklyAmount(CurrencyType.USD));
        System.out.println("================================Income: "+service.getIncomeWeeklyAmount(CurrencyType.USD));
    }
}