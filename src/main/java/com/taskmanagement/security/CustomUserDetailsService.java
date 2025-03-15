package com.taskmanagement.security;

import com.taskmanagement.entity.Users;
import com.taskmanagement.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users =userRepository.findByUsername(username).orElseThrow(() ->new UsernameNotFoundException("users not exist "+username));
        System.out.println("users" +users.getUsername());
        return new org.springframework.security.core.userdetails.User(
                users.getUsername(),
                users.getPassword(),
                users.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName().name()))
                        .collect(Collectors.toList()));

    }
}
