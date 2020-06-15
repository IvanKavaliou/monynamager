package net.ivan.kavaliou.moneyman.forms;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class PasswordChangeForm {

    @NotBlank (message = "error.notBlank")
    String oldPassword;

    @Size(min = 4, max = 16, message = "error.password.size")
    @NotBlank (message = "error.notBlank")
    String password;

    @NotBlank(message = "error.notBlank")
    String passwordRepeat;
}
