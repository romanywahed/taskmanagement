package com.taskmanagement.configuration;

import com.taskmanagement.security.CustomUserDetailsService;
import com.taskmanagement.security.JwtRequestFilter;
import com.taskmanagement.security.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfiguration {
    private final CustomUserDetailsService customUserDetailsService;

    private final JwtUtil jwtUtil;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.csrf().disable()
                .authorizeHttpRequests()
                .antMatchers("/api/v1/auth/**").permitAll() // Allow public access to auth endpoints
                .antMatchers("/api/v1/tasks/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN") // Require authentication for tasks
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(new JwtRequestFilter(customUserDetailsService,jwtUtil), UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        String encodedRawPassword = passwordEncoder().encode("1111");
      //  System.out.println("Encoded Raw Password : " + encodedRawPassword);
        return new ProviderManager(List.of(new DaoAuthenticationProvider() {{
            setUserDetailsService(customUserDetailsService);
            setPasswordEncoder(new BCryptPasswordEncoder());
        }}));
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
