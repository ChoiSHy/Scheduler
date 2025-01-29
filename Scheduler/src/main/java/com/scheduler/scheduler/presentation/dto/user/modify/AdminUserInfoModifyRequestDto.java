package com.scheduler.scheduler.presentation.dto.user.modify;

import lombok.Getter;

import java.util.Date;
@Getter
public class AdminUserInfoModifyRequestDto extends UserInfoModifyRequestDto{
    private String email;
}
