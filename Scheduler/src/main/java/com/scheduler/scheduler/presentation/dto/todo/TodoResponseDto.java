package com.scheduler.scheduler.presentation.dto.todo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TodoResponseDto {
    private UUID id;
    private String comment;
    private Date dueDate;
    private Boolean isDone;
    private String user_name;
}
