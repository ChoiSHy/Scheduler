package com.scheduler.scheduler.application;

import com.scheduler.scheduler.presentation.dto.todo.TodoCreateRequestDto;
import com.scheduler.scheduler.presentation.dto.todo.TodoResponseDto;

import java.util.UUID;

public interface TodoService{
    TodoResponseDto findById(UUID id);
    TodoResponseDto save(TodoCreateRequestDto requestDto);

}
