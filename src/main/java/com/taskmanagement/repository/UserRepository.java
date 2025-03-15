package com.taskmanagement.repository;

import com.taskmanagement.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    @Query("SELECT user FROM Users user JOIN FETCH user.roles WHERE user.username = :username")
    Optional<Users> findByUsername(@Param("username") String username);
    @Query("SELECT user FROM Users user  WHERE user.username = :username")
    Optional<Users> findUserByUsername(@Param("username") String username);
    @Query("SELECT u.id FROM Users u WHERE u.username = :username")
    Optional<Long> findIdByUsername(@Param("username") String username);

}
