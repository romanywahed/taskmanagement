package com.taskmanagement.entity;

import com.taskmanagement.enums.RoleName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(nullable = false)
//    private String role;

//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private Users user;

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private RoleName name;

    @ManyToMany(mappedBy = "roles",fetch = FetchType.LAZY)
    private Set<Users> users = new HashSet<>();
}