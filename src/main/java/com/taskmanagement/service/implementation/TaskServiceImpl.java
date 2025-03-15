package com.taskmanagement.service.implementation;

import com.taskmanagement.entity.Tasks;
import com.taskmanagement.entity.Users;
import com.taskmanagement.enums.Priority;
import com.taskmanagement.enums.TaskStatus;
import com.taskmanagement.repository.TasksRepository;
import com.taskmanagement.repository.UserRepository;
import com.taskmanagement.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TasksRepository tasksRepository;
    private final UserRepository usersRepository;

    @Override
    public Tasks createTask(Tasks task, String username) {
        Users user = usersRepository.findUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        task.setCreatedBy(user);
        return tasksRepository.save(task);
    }

    @Override
    public Tasks updateTask(Long taskId, Tasks updatedTask) {
        return  tasksRepository.findById(taskId).map(
                task -> {
                    task.setTitle(updatedTask.getTitle());
                    task.setDescription(updatedTask.getDescription());
                    task.setStatus(updatedTask.getStatus());
                    task.setDueDate(updatedTask.getDueDate());
                    task.setPriority(updatedTask.getPriority());
                   return   tasksRepository.save(task);
                }).orElseThrow(() ->new RuntimeException("Task not exists"));
    }

    @Override
    public boolean deleteTask(Long taskId) {
        if (tasksRepository.existsById(taskId)) {
            tasksRepository.deleteById(taskId);
            return true;
        }
        return false;
    }


    @Override
    public List<Tasks> listTasks(String username,TaskStatus status, Priority priority, LocalDate dueDate, String sortBy) {
        Long userId = usersRepository.findIdByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Pageable pageable = PageRequest.of(0, 10, Sort.by("dueDate").descending());
        Page<Tasks> tasksPage = tasksRepository.filtersTasks(userId, status, priority, dueDate, pageable);
        List<Tasks> tasks = tasksPage.getContent();

        return tasks;
    }
}
