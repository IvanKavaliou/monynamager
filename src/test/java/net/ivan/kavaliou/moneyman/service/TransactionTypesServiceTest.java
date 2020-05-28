package net.ivan.kavaliou.moneyman.service;

import net.ivan.kavaliou.moneyman.model.persistence.TransactionTypes;
import net.ivan.kavaliou.moneyman.utils.enums.TransactionType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TransactionTypesServiceTest {

    @Autowired
    private TransactionTypesService service;

    @Test
    public void getTransactionTypesTest(){
        TransactionTypes incomeType = service.getIncome();
        TransactionTypes expensesType = service.getExpenses();
        assertTrue(incomeType.getTransactionType() == TransactionType.INCOME);
        assertTrue(expensesType.getTransactionType() == TransactionType.EXPENSES);
    }
}