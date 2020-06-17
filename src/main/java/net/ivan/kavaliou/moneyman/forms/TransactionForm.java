package net.ivan.kavaliou.moneyman.forms;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.ivan.kavaliou.moneyman.utils.enums.CurrencyType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionForm {

    private Integer id;

    @NotNull(message = "{error.notBlank}")
    private CurrencyType currencyType;

    @NotNull (message = "{error.value.not.blank}")
    private BigDecimal value;

    @NotNull (message = "{error.notBlank}")
    private Integer idTransactionCategory;

    private TransactionCategoryForm transactionCategory;

    @NotNull
    @NotBlank(message = "{error.description.not.blank}")
    private String name;

    private String date;
}
