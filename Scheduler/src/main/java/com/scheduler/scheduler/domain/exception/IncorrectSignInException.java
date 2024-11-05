package com.scheduler.scheduler.domain.exception;

public class IncorrectSignInException extends RuntimeException{
    public IncorrectSignInException(String msg){
        super(msg);
    }

    public IncorrectSignInException() {
        super("회원정보가 일치하지 않습니다.");
    }
}
