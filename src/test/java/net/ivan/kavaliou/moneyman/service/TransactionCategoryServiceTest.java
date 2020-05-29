package net.ivan.kavaliou.moneyman.service;

import net.ivan.kavaliou.moneyman.model.persistence.TransactionCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
@Sql(scripts = "classpath:db/populatedb.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class TransactionCategoryServiceTest {

    @Autowired
    TransactionCategoryService service;

    @Autowired
    TransactionTypesService transactionTypesService;

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
    public void addNewIncomeCategoryTest(){
        TransactionCategory new_income_category = TransactionCategory.builder()
                                                    .name("New income category")
                                                    .build();
        TransactionCategory new_income = service.saveIncomeCategory(new_income_category);
        assertTrue(new_income.getId() != null);
    }

    @Test
    @WithMockUser("user@user.ru")
    public void getCategoryTest(){
        Optional<TransactionCategory> exsistCategory = service.get(100008);
        assertTrue(exsistCategory.isPresent());
        Optional<TransactionCategory> otherUserCategory = service.get(100013);
        assertFalse(otherUserCategory.isPresent());
    }

    @Test
    @WithMockUser("user@user.ru")
    public void updateCategoryTest(){
        TransactionCategory category = service.get(100008).get();
        category.setName("New name");
        service.save(category);
        TransactionCategory categoryAfterUpdate = service.get(100008).get();
        assertTrue(categoryAfterUpdate.getName().equals("New name"));
    }

    @Test
    @WithMockUser("user@user.ru")
    public void deleteTest(){
        assertTrue(service.delete(100008));
    }

    @Test
    @WithMockUser("user@user.ru")
    public void deleteAnotherUserTest(){
        assertFalse(service.delete(100013));
    }
}