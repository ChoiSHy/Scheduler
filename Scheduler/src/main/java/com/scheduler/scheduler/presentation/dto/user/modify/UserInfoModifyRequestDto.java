package com.scheduler.scheduler.presentation.dto.user.modify;

import lombok.Getter;

import java.util.Date;
@Getter
public abstract class UserInfoModifyRequestDto {
    private String name;
    private Date BirthDate;
    private String password;
}
