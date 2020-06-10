package net.ivan.kavaliou.moneyman.utils;

import net.ivan.kavaliou.moneyman.config.CustomLocaleResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Locale;

@Component
public class Messages {

    @Autowired
    private MessageSource messageSource;

    private MessageSourceAccessor accessor;

    @PostConstruct
    private void init() {
        accessor = new MessageSourceAccessor(messageSource, new CustomLocaleResolver().getDefaultLocale());
    }

    public String get(String code) {
        return accessor.getMessage(code);
    }

}