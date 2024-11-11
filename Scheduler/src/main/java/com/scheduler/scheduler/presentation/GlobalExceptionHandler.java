package com.scheduler.scheduler.presentation;

import com.scheduler.scheduler.domain.exception.InaccessibleException;
import com.scheduler.scheduler.domain.exception.IncorrectSignInException;
import com.scheduler.scheduler.presentation.dto.sign.ExceptionMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class GlobalExceptionHandler {
    private final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = InaccessibleException.class)
    public ResponseEntity<ExceptionMessage> InaccessibleExceptionHandler(InaccessibleException e) {
        LOGGER.error("ExceptionHandler 호출, {}, {}", e.getCause(), e.getMessage());
        return ResponseEntity
                .status(401)
                .body(new ExceptionMessage(
                        HttpStatus.BAD_REQUEST.getReasonPhrase(), "400",e.getMessage()));
    }

    @ExceptionHandler(value = IncorrectSignInException.class)
    public ResponseEntity<ExceptionMessage> ExceptionHandler(InaccessibleException e) {
        LOGGER.error("ExceptionHandler 호출, {}, {}", e.getCause(), e.getMessage());
        return generateResponseEntity();
    }

    private ResponseEntity<ExceptionMessage> generateResponseEntity() {
        HttpHeaders responseHeaders = new HttpHeaders();
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ExceptionMessage errorMsg = new ExceptionMessage(httpStatus.getReasonPhrase(), "400", "에러 발생");
        return new ResponseEntity<>(errorMsg, responseHeaders, httpStatus);
    }
}
