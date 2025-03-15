package com.taskmanagement.service;

import com.taskmanagement.entity.Tasks;
import com.taskmanagement.entity.Users;
import com.taskmanagement.enums.Priority;
import com.taskmanagement.enums.TaskStatus;
import org.springframework.scheduling.config.Task;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDate;
import java.util.List;


public interface TaskService {

    Tasks createTask(Tasks task, String userName);
    Tasks updateTask(Long taskId, Tasks task);
    boolean deleteTask(Long taskId);

    List<Tasks> listTasks(String username,TaskStatus status, Priority priority, LocalDate dueDate,String sortBy);

}
