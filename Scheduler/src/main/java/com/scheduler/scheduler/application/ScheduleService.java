package com.scheduler.scheduler.application;

import com.scheduler.scheduler.presentation.dto.Schedule.ScheduleCreateDto;
import com.scheduler.scheduler.presentation.dto.Schedule.ScheduleResponseDto;
import com.scheduler.scheduler.presentation.dto.Schedule.ScheduleUpdateDto;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
public interface ScheduleService {
    ScheduleResponseDto createNewSchedule(ScheduleCreateDto createDto);
    ScheduleResponseDto findById(UUID uuid);
    ScheduleResponseDto findByUser();
    ScheduleResponseDto updateSchedule(ScheduleUpdateDto updateDto);
}
