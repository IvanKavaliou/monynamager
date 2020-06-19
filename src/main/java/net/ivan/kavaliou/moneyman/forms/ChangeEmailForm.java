package net.ivan.kavaliou.moneyman.forms;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class ChangeEmailForm {

    @Email(message = "{error.email.format}")
    @NotBlank(message = "{error.notBlank}")
    private String email;
}
