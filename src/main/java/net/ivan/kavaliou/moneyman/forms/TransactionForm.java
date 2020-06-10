package net.ivan.kavaliou.moneyman.forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.ivan.kavaliou.moneyman.utils.enums.CurrencyType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionForm {

    private Integer id;

    @NotNull(message = "error.notBlank")
    private CurrencyType currencyType;

    @NotNull (message = "error.notBlank")
    private BigDecimal value;

    @NotNull (message = "error.notBlank")
    private Integer idTransactionCategory;

    @NotNull
    @NotBlank(message = "error.notBlank")
    private String name;

    @NotNull
    String date;
}
