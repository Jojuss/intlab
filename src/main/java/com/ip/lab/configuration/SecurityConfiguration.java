package com.ip.lab.configuration;

import com.ip.lab.configuration.jwt.JwtFilter;
import com.ip.lab.user.controller.UserController;
import com.ip.lab.user.controller.UserSignupMvcController;
import com.ip.lab.user.model.UserRole;
import com.ip.lab.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfiguration {
    private final Logger log = LoggerFactory.getLogger(SecurityConfiguration.class);
    private static final String LOGIN_URL = "/login";
    public static final String SPA_URL_MASK = "/{path:[^\\.]*}";
    private UserService userService;
    private JwtFilter jwtFilter;

    public SecurityConfiguration(UserService userService) {
        this.userService = userService;
        this.jwtFilter = new JwtFilter(userService);
        createAdminOnStartup();
        createTestUsersOnStartup();
    }

    private void createAdminOnStartup() {
        final String admin = "admin";
        if (userService.findByLogin(admin) == null) {
            log.info("Admin user successfully created");
            userService.createUser(admin, admin, admin, UserRole.ADMIN);
        }
    }

    private void createTestUsersOnStartup() {
        final String[] userNames = {"user1", "user2", "user3"};
        for (String user : userNames) {
            if (userService.findByLogin(user) == null) {
                log.info("User %s successfully created".formatted(user));
                userService.createUser(user, user, user, UserRole.USER);
            }
        }
    }

    @Bean
    public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
        http.cors()
                .and()
                .csrf().disable()
                .authorizeHttpRequests((a) -> a
                        .requestMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/**").hasRole("ADMIN")
                        .requestMatchers("/api/**").authenticated()
                        .requestMatchers(HttpMethod.POST, UserController.URL_LOGIN).permitAll())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .anonymous().and().authorizeHttpRequests((a) ->
                        a.requestMatchers(LOGIN_URL, UserSignupMvcController.SIGNUP_URL, "/h2-console/**")
                                .permitAll().requestMatchers("/users").hasRole("ADMIN").anyRequest().authenticated())
                .formLogin()
                .loginPage(LOGIN_URL).permitAll()
                .and()
                .logout().permitAll()
                .logoutSuccessUrl("/login")
                .and()
                .userDetailsService(userService);

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/css/**", "/js/**", "/templates/**", "/webjars/**", "/styles/**");    }
}
