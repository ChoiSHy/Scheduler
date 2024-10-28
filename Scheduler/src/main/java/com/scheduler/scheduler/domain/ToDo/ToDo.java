package com.scheduler.scheduler.domain.ToDo;

import com.scheduler.scheduler.domain.User.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.UUID;

@Entity
@NoArgsConstructor
public class ToDo {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private UUID id;
    @Column(nullable = false)
    private String comment;
    @Column(nullable = false)
    @DateTimeFormat(pattern = "YYYY-MM-DD")
    private Date dueDate;
    @Column(nullable = false)
    private Boolean isDone;
    @ManyToOne
    @NotNull
    private User user;

}
