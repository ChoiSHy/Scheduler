package com.scheduler.scheduler.presentation.dto.user;

import lombok.Getter;

import java.util.Date;
@Getter
public class UserModifyRequestDto {
    private String name;
    private Date BirthDate;
    private String password;
}
