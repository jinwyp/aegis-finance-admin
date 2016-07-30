package com.yimei.domain.common.exception;

/**
 * Created by jack on 14/12/5.
 */
//@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException{
    private String message;

    public NotFoundException(){

    }

    public NotFoundException(String message){
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
