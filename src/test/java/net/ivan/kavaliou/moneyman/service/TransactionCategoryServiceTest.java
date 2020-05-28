package net.ivan.kavaliou.moneyman.service;

import net.ivan.kavaliou.moneyman.model.persistence.TransactionCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;


@SpringBootTest
class TransactionCategoryServiceTest {

    @Autowired
    TransactionCategoryService service;

    @Test
    @WithMockUser("user@user.ru")
    public void getTest(){
        List<TransactionCategory> incoms = service.getIncomes();
        List<TransactionCategory> expenses = service.getExpenses();

        incoms.stream().forEach(System.out::println);
        expenses.stream().forEach(System.out::println);
    }

    @Test
    @WithMockUser("user@user.ru")
    public void addTest(){

    }
}