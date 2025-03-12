package com.taskmanagement.entity;

import com.taskmanagement.enums.Priority;
import com.taskmanagement.enums.TaskStatus;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {

    @Id
    @GeneratedValue
    private UUID id;

    private String title;
    private String description;
    
    @Enumerated(EnumType.STRING)
    private TaskStatus status;
    
    @Enumerated(EnumType.STRING)
    private Priority priority;
    
    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = UUID.randomUUID();
        }
    }
}