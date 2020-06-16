package net.ivan.kavaliou.moneyman.forms;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.ivan.kavaliou.moneyman.utils.enums.CurrencyType;

@Data
@Builder
public class AccountForm {
    private CurrencyType currencyType;
    private AmountForm income;
    private AmountForm expenses;
}
