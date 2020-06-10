package net.ivan.kavaliou.moneyman.validation;

import net.ivan.kavaliou.moneyman.forms.TransactionForm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class TransactionValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return TransactionForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        TransactionForm form = (TransactionForm) target;
        if (StringUtils.isEmpty(form.getName())){
           errors.rejectValue("name", "", "error.notBlank");
        }
    }
}
