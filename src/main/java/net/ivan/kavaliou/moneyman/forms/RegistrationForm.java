package net.ivan.kavaliou.moneyman.forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.ivan.kavaliou.moneyman.model.persistence.Currency;
import net.ivan.kavaliou.moneyman.utils.enums.CurrencyType;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationForm {

    @Email
    @NotBlank(message = "error.notBlank")
    private String email;

    @Size(min = 4, max = 16, message = "error.password.size")
    @NotBlank (message = "error.notBlank")
    private String password;

    @NotBlank (message = "error.notBlank")
    private String passwordRepeat;

    @AssertTrue (message = "error.agrements")
    private boolean agrements;

    @NotNull (message = "error.notBlank")
    private CurrencyType currency;
}
