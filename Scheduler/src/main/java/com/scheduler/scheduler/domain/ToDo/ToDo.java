package com.scheduler.scheduler.domain.ToDo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.UUID;

@Entity
@NoArgsConstructor
public class ToDo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;
    @NotNull
    String comment;
    @NotNull
    @DateTimeFormat(pattern = "YYYY-MM-DD")
    Date dueDate;


}
