package net.ivan.kavaliou.moneyman.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class ServiceException  extends RuntimeException{
    public ServiceException(String message){
        super(message);
    }
}
