package com.taskmanagement.controller;

import com.taskmanagement.entity.Tasks;
import com.taskmanagement.enums.Priority;
import com.taskmanagement.enums.TaskStatus;
import com.taskmanagement.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("api/v1/tasks")
@RequiredArgsConstructor
public class TasksController {
    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<Tasks> createTask(@RequestBody Tasks task, Principal principal) {
        String username = principal.getName();
        Tasks savedTask = taskService.createTask(task,username);

        return ResponseEntity.ok(savedTask);
    }


    @PutMapping("/{taskId}")
    public ResponseEntity<Tasks>updateTasks(@PathVariable Long taskId, @RequestBody Tasks updateTask){
        return ResponseEntity.ok(taskService.updateTask(taskId, updateTask));
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Boolean> deleteTask(@PathVariable Long taskId) {
        boolean isDeleted = taskService.deleteTask(taskId);
        return ResponseEntity.ok(isDeleted);
    }


    @GetMapping
    public ResponseEntity<List<Tasks>> getAllTasks(@RequestParam(required = false)TaskStatus status, @RequestParam(required = false)Priority priority,
                                                   @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dueDate, @RequestParam(defaultValue = "dueDate") String sortBy, Principal principal) {
        String username = principal.getName();
        return ResponseEntity.ok(taskService.listTasks(username,status, priority, dueDate, sortBy));
    }

}
