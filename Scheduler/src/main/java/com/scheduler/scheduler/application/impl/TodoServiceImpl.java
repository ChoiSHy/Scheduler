package com.scheduler.scheduler.application.impl;

import com.scheduler.scheduler.application.TodoService;
import com.scheduler.scheduler.infrastructure.repository.TodoRepository;
import com.scheduler.scheduler.presentation.dto.todo.TodoCreateRequestDto;
import com.scheduler.scheduler.presentation.dto.todo.TodoResponseDto;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {
    private TodoRepository todoRepository;
    private Logger LOGGER = LoggerFactory.getLogger(TodoServiceImpl.class);
    @Override
    public TodoResponseDto findById(UUID id) {

        return null;
    }

    @Override
    public TodoResponseDto save(TodoCreateRequestDto requestDto) {

        return null;
    }

}
