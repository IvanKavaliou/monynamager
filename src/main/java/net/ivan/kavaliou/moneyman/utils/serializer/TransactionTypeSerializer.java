package net.ivan.kavaliou.moneyman.utils.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import net.ivan.kavaliou.moneyman.utils.enums.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;


import java.io.IOException;

public class TransactionTypeSerializer extends JsonSerializer<TransactionType> {

    @Autowired
    MessageSource messageSource;

    @Override
    public void serialize(TransactionType transactionType, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(messageSource.getMessage("label.transactionType."+transactionType.name(), new String[] {}, LocaleContextHolder.getLocale()));
    }
}
