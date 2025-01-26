package com.scheduler.scheduler.presentation.controller;

import com.scheduler.scheduler.application.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor

public class ScheduleController {
    private final Logger LOGGER = LoggerFactory.getLogger(ScheduleController.class);
    ScheduleService scheduleService;



}
