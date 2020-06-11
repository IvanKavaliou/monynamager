package net.ivan.kavaliou.moneyman.forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.ivan.kavaliou.moneyman.utils.enums.TransactionType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionCategoryForm {

    private Integer id;

    @NotEmpty(message = "error.notBlank")
    private String name;

    @NotNull(message = "error.notBlank")
    private TransactionType transactionType;
}
