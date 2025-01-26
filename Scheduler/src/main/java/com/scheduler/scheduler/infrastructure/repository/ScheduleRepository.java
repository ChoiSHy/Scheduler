package com.scheduler.scheduler.infrastructure.repository;

import com.scheduler.scheduler.domain.Schedule.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ScheduleRepository extends JpaRepository<Schedule,UUID> {
}
