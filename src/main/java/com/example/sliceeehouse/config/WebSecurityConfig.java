
package com.example.sliceeehouse.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.userdetails.User;

import com.example.sliceeehouse.repository.UserRepository;

@Configuration
public class WebSecurityConfig {

    private final UserRepository userRepository;

    public WebSecurityConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
        .authorizeHttpRequests(auth -> auth

            // PUBLIC PAGES
            .requestMatchers(
                    "/", "/index", "/about",
                    "/login", "/register",
                    "/pizza/**",
                    "/cheese", "/pepperoni", "/vegie",
                    "/mania", "/coffee"
            ).permitAll()

            // STATIC FILES
            .requestMatchers(
                    "/css/**",
                    "/js/**",
                    "/images/**"
            ).permitAll()

            // USER LOGIN REQUIRED
        .requestMatchers(
    "/cart/**",
    "/order/**",
    "/profile/**",
    "/checkout/**",
    "/wishlist/**",
    "/review/**"
).authenticated()

            // ADMIN ONLY
            .requestMatchers("/admin/**").hasAuthority("ADMIN")

            // EVERYTHING ELSE
            .anyRequest().permitAll()
        )

        // LOGIN PAGE
        .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/", true)
                .permitAll()
        )

        // LOGOUT
        .logout(logout -> logout
                .logoutSuccessUrl("/")
                .permitAll()
        )

        // CSRF disabled (for development)
        .csrf(csrf -> csrf.disable());

        return http.build();
    }


    // PASSWORD ENCODER
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    // USER LOGIN SERVICE
    @Bean
    public UserDetailsService userDetailsService() {

        return username -> userRepository.findByUsername(username)
                .map(user -> User.withUsername(user.getUsername())
                        .password(user.getPassword())
                        .authorities(user.getRole())
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}

