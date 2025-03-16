package com.taskmanagement.controller;

import com.taskmanagement.entity.Tasks;
import com.taskmanagement.enums.Priority;
import com.taskmanagement.enums.TaskStatus;
import com.taskmanagement.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@CrossOrigin(origins = "*")
public class TasksController {

    private final TaskService taskService;

    @PostMapping
    @Operation(summary = "Create a new task", description = "Creates a task and assigns it to the authenticated user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task created successfully",
                    content = @Content(schema = @Schema(implementation = Tasks.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<Tasks> createTask(
            @RequestBody(description = "Task object to be created", required = true, content = @Content(schema = @Schema(implementation = Tasks.class)))
            @org.springframework.web.bind.annotation.RequestBody Tasks task,
            Principal principal) {

        String username = principal.getName();
        Tasks savedTask = taskService.createTask(task, username);
        return ResponseEntity.ok(savedTask);
    }

    @PutMapping("/{taskId}")
    @Operation(summary = "Update a task", description = "Updates a task based on its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task updated successfully",
                    content = @Content(schema = @Schema(implementation = Tasks.class))),
            @ApiResponse(responseCode = "404", description = "Task not found")
    })
    public ResponseEntity<Tasks> updateTasks(
            @PathVariable Long taskId,
            @RequestBody(description = "Updated task data", required = true, content = @Content(schema = @Schema(implementation = Tasks.class)))
            @org.springframework.web.bind.annotation.RequestBody Tasks updateTask) {

        return ResponseEntity.ok(taskService.updateTask(taskId, updateTask));
    }

    @DeleteMapping("/{taskId}")
    @Operation(summary = "Delete a task", description = "Deletes a task based on its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Task not found")
    })
    public ResponseEntity<Boolean> deleteTask(@PathVariable Long taskId) {
        boolean isDeleted = taskService.deleteTask(taskId);
        return ResponseEntity.ok(isDeleted);
    }

    @GetMapping
    @Operation(summary = "Retrieve all tasks", description = "Retrieves all tasks based on filters.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tasks retrieved successfully",
                    content = @Content(schema = @Schema(implementation = Tasks.class))),
            @ApiResponse(responseCode = "403", description = "Unauthorized access")
    })
    public ResponseEntity<List<Tasks>> getAllTasks(
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(required = false) Priority priority,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dueDate,
            @RequestParam(defaultValue = "dueDate") String sortBy,
            Principal principal) {

        String username = principal.getName();
        return ResponseEntity.ok(taskService.listTasks(username, status, priority, dueDate, sortBy));
    }
}
