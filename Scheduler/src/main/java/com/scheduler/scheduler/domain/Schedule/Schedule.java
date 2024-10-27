package com.scheduler.scheduler.domain.Schedule;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @NotNull
    @DateTimeFormat(pattern = "YYYY-MM-DD hh:mm")
    private LocalDateTime sTime;
    @NotNull
    @DateTimeFormat(pattern = "YYYY-MM-DD hh:mm")
    private LocalDateTime eTime;

    @Column(nullable = false)
    private String title;
    @Column(nullable = true)
    private String content;

}
