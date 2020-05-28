package net.ivan.kavaliou.moneyman.service;

import net.ivan.kavaliou.moneyman.model.persistence.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
}