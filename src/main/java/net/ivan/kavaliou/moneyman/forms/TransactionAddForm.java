package net.ivan.kavaliou.moneyman.forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.ivan.kavaliou.moneyman.model.persistence.TransactionCategory;
import net.ivan.kavaliou.moneyman.utils.enums.CurrencyType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionAddForm {
    private Integer id_user;
    private CurrencyType currencyType;
    private float value;
    private TransactionCategory transactionCategory;
    private String name;
}
