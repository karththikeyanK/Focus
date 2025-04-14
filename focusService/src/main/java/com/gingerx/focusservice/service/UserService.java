package com.gingerx.focusservice.service;

import com.gingerx.focusservice.dto.UserRequest;
import com.gingerx.focusservice.dto.UserResponse;
import com.gingerx.focusservice.dtoMapper.UserDtoMapper;
import com.gingerx.focusservice.entity.User;
import com.gingerx.focusservice.enums.ActiveStatus;
import com.gingerx.focusservice.exception.DataNotFoundException;
import com.gingerx.focusservice.exception.DuplicationException;
import com.gingerx.focusservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User Register(UserRequest userRequest) {
        log.info("UserService::Register()::Creating user started");

        if (userRepository.existsByEmail(userRequest.getEmail())) {
            log.error("UserService::Register()::Email already exists: {} ", userRequest.getEmail());
            throw new DuplicationException("Email already exists:"+ userRequest.getEmail());
        }
        User user = UserDtoMapper.mapToEntity(userRequest, passwordEncoder);
        user.setStatus(ActiveStatus.PENDING.name());
        user = userRepository.save(user);
        log.info("UserService::Register()::Creating user completed");
        return user;
    }

    public UserResponse getUserById(Long id) {
        log.info("UserService::getUserById()::Fetching user by id started");
        User user = userRepository.findById(id).orElseThrow(() -> new DataNotFoundException("User not found with id: " + id));
        log.info("UserService::getUserById()::Fetching user by id completed");
        return UserDtoMapper.mapToResponse(user);
    }

    public User getUserEntityById(Long id) {
        log.info("UserService::getUserEntityById()::Fetching user by id started");
        User user = userRepository.findById(id).orElseThrow(() -> new DataNotFoundException("User not found with id: " + id));
        log.info("UserService::getUserEntityById()::Fetching user by id completed");
        return user;
    }

    public UserResponse getUserByEmail(String email) {
        log.info("UserService::getUserByEmail()::Fetching user by email started");
        User user = userRepository.findByEmail(email).orElseThrow(() -> new DataNotFoundException("User not found with email: " + email));
        log.info("UserService::getUserByEmail()::Fetching user by email completed");
        return UserDtoMapper.mapToResponse(user);
    }

    public boolean existById(Long id) {
        log.info("UserService::existById()::Checking user existence by id started");
        return userRepository.existsById(id);

    }

    public UserResponse update(Long id, UserRequest userRequest) {
        log.info("UserService::update()::Updating user started");
        User user = userRepository.findById(id).orElseThrow(() -> new DataNotFoundException("User not found with id: " + id));
        if (!user.getEmail().equals(userRequest.getEmail()) && userRepository.existsByEmail(userRequest.getEmail())) {
            log.error("UserService::update()::Email already exists: {} ", userRequest.getEmail());
            throw new DuplicationException("Email already exists:"+ userRequest.getEmail());
        }
        User UpdatedUuser = UserDtoMapper.mapToEntity(userRequest, passwordEncoder);
        UpdatedUuser.setId(id);
        UpdatedUuser.setPassword(user.getPassword());
        user = userRepository.save(UpdatedUuser);
        log.info("UserService::update()::Updating user completed");
        return UserDtoMapper.mapToResponse(user);
    }

    public List<UserResponse> getAll() {
        log.info("UserService::getAll()::Fetching all users started");
        List<User> users = userRepository.findAll();
        log.info("UserService::getAll()::Fetching all users completed");
        return users.stream().map(UserDtoMapper::mapToResponse).collect(Collectors.toList());
    }

    public User findByEmail(String email) {
        log.info("UserService::findByEmail()::Fetching user by email started");
        User user = userRepository.findByEmail(email).orElseThrow(() -> new DataNotFoundException("User not found with email: " + email));
        log.info("UserService::findByEmail()::Fetching user by email completed");
        return user;
    }
}
