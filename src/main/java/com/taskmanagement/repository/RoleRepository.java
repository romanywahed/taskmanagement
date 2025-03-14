package com.taskmanagement.repository;

import com.taskmanagement.entity.Roles;
import com.taskmanagement.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Roles,Long> {

    Optional<Roles> findByName(RoleName name);
}
