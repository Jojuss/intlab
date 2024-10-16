package com.ip.lab.user.service;

import com.ip.lab.configuration.jwt.JwtException;
import com.ip.lab.configuration.jwt.JwtsProvider;
import com.ip.lab.user.model.User;
import com.ip.lab.user.model.UserDto;
import com.ip.lab.user.model.UserRole;
import com.ip.lab.user.repository.UserRepository;
import com.ip.lab.util.validation.ValidationException;
import com.ip.lab.util.validation.ValidatorUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Objects;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ValidatorUtil validatorUtil;
    private final JwtsProvider jwtProvider;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       ValidatorUtil validatorUtil,
                       JwtsProvider jwtProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.validatorUtil = validatorUtil;
        this.jwtProvider = jwtProvider;
    }

    public Page<User> findAllPages(int page, int size) {
        return userRepository.findAll(PageRequest.of(page - 1, size, Sort.by("id").ascending()));
    }

    public User findByLogin(String login) {
        return userRepository.findOneByLoginIgnoreCase(login);
    }

    public User createUser(String login, String password, String passwordConfirm) {
        return createUser(login, password, passwordConfirm, UserRole.USER);
    }

    public User createUser(String login, String password, String passwordConfirm, UserRole role) {
        if (findByLogin(login) != null) {
            throw new ValidationException(String.format("User '%s' already exists", login));
        }
        final User user = new User(login, passwordEncoder.encode(password), role);
        validatorUtil.validate(user);
        if (!Objects.equals(password, passwordConfirm)) {
            throw new ValidationException("Passwords not equals");
        }
        return userRepository.save(user);
    }

    public String loginAndGetToken(UserDto userDto) {
        final User user = findByLogin(userDto.getLogin());
        if (user == null) {
            throw new UserNotFoundException(userDto.getLogin());
        }
        if (!passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
            throw new UserNotFoundException(user.getLogin());
        }
        return jwtProvider.generateToken(user.getLogin(), user.getRole().name());
    }

    public UserDetails loadUserByToken(String token) throws UsernameNotFoundException {
        if (!jwtProvider.validateToken(token)) {
            throw new JwtException("Bad token");
        }
        final String userLogin = jwtProvider.getLogin(token);
        if (userLogin.isEmpty()) {
            throw new JwtException("Token is not contain Login");
        }
        return loadUserByUsername(userLogin);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User userEntity = findByLogin(username);
        if (userEntity == null) {
            throw new UsernameNotFoundException(username);
        }
        return new org.springframework.security.core.userdetails.User(
                userEntity.getLogin(), userEntity.getPassword(), Collections.singleton(userEntity.getRole()));
    }
}
