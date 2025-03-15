package com.taskmanagement.repository;

import com.taskmanagement.entity.Tasks;
import com.taskmanagement.enums.Priority;
import com.taskmanagement.enums.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TasksRepository extends JpaRepository<Tasks, Long> {

    List<Tasks> findByPriority(Priority priority);

    List<Tasks> findByStatus(TaskStatus status);

    List<Tasks> findByDueDate(LocalDate dueDate);
    @Query("SELECT task FROM Tasks task WHERE task.createdBy.id = :userId AND " +
            "(:status IS NULL OR task.status = :status) AND " +
            "(:priority IS NULL OR task.priority = :priority) AND " +
            "(:dueDate IS NULL OR task.dueDate = :dueDate)")
    Page<Tasks> filtersTasks(@Param("userId") Long userId,
                             @Param("status") TaskStatus status,
                             @Param("priority") Priority priority,
                             @Param("dueDate") LocalDate dueDate,
                             Pageable pageable);



    @Query(value = "SELECT * FROM tasks WHERE created_by = UUID_TO_BIN('6b3cea61-002a-11f0-b6ef-960003b8de72')",nativeQuery = true)
    Page<Tasks> findByUserId( Pageable pageable);
}