package net.ivan.kavaliou.moneyman.forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationForm {
    @NotBlank(message = "error.notBlank")
    private String email;

    @NotBlank (message = "error.notBlank")
    private String password;

    @NotBlank (message = "error.notBlank")
    private String passwordRepeat;

    @AssertTrue (message = "error.agrements")
    private boolean agrements;
}
