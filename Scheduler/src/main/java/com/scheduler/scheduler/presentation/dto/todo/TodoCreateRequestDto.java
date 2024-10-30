package com.scheduler.scheduler.presentation.dto.todo;

import com.scheduler.scheduler.domain.ToDo.Todo;

import java.util.Date;

public class TodoCreateRequestDto {
    private String comment;
    private Date dueDate;

    public Todo toEntity(){
        return Todo.builder()
                .comment(comment)
                .dueDate(dueDate)
                .build();
    }
}
