package com.scheduler.scheduler.application;

import com.scheduler.scheduler.domain.ToDo.Todo;
import com.scheduler.scheduler.presentation.dto.todo.TodoCreateRequestDto;
import com.scheduler.scheduler.presentation.dto.todo.TodoResponseDto;
import com.scheduler.scheduler.presentation.dto.user.request.UserCreateRequestDto;
import com.scheduler.scheduler.presentation.dto.user.response.UserCreateResponseDto;
import com.scheduler.scheduler.presentation.dto.user.response.UserResponseDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.UUID;

public interface TodoService{
    TodoResponseDto findById(UUID id);
    TodoResponseDto save(TodoCreateRequestDto requestDto);

}
