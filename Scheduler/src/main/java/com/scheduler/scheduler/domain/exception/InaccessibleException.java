package com.scheduler.scheduler.domain.exception;

public class InaccessibleException extends RuntimeException{
    public InaccessibleException(String msg){
        super(msg);
    }
}
