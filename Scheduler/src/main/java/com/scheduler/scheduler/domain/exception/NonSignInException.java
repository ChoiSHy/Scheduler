package com.scheduler.scheduler.domain.exception;

public class NonSignInException extends RuntimeException{
    public NonSignInException(){
        super("로그인 되어있지 않습니다.");
    }
}
