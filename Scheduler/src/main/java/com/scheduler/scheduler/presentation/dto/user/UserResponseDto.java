package com.scheduler.scheduler.presentation.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@AllArgsConstructor
@Builder
@Getter
public class UserResponseDto {
    String name;
    String email;
    Date birthDate;
}
