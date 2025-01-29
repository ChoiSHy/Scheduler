package com.scheduler.scheduler.presentation.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@AllArgsConstructor
@Builder
@Getter
public class UserInfoResponseDto {
    private String name;
    private String email;
    private Date birthDate;
}
