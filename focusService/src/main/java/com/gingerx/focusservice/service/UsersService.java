package com.gingerx.focusservice.service;


import com.gingerx.focusservice.dto.UserRequest;
import com.gingerx.focusservice.dto.UserResponse;
import com.gingerx.focusservice.dtoMapper.UserDtoMapper;
import com.gingerx.focusservice.entity.User;
import com.gingerx.focusservice.exception.DataNotFoundException;
import com.gingerx.focusservice.exception.DuplicationException;
import com.gingerx.focusservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class UsersService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User Register(UserRequest userRequest) {
        log.info("UsersService::Register()::Creating user started");

        if (userRepository.existsByEmail(userRequest.getEmail())) {
            log.error("UsersService::Register()::Email already exists: {} ", userRequest.getEmail());
            throw new DuplicationException("Email already exists:"+ userRequest.getEmail());
        }
        User user = UserDtoMapper.mapToEntity(userRequest, passwordEncoder);
        user = userRepository.save(user);
        log.info("UsersService::Register()::Creating user completed");
        return user;
    }

    public UserResponse getUserById(Long id) {
        log.info("UsersService::getUserById()::Fetching user by id started");
        User user = userRepository.findById(id).orElseThrow(() -> new DataNotFoundException("User not found with id: " + id));
        log.info("UsersService::getUserById()::Fetching user by id completed");
        return UserDtoMapper.mapToResponse(user);
    }

    public User getUserEntityById(Long id) {
        log.info("UsersService::getUserEntityById()::Fetching user by id started");
        User user = userRepository.findById(id).orElseThrow(() -> new DataNotFoundException("User not found with id: " + id));
        log.info("UsersService::getUserEntityById()::Fetching user by id completed");
        return user;
    }

    public UserResponse getUserByEmail(String email) {
        log.info("UsersService::getUserByEmail()::Fetching user by email started");
        User user = userRepository.findByEmail(email).orElseThrow(() -> new DataNotFoundException("User not found with email: " + email));
        log.info("UsersService::getUserByEmail()::Fetching user by email completed");
        return UserDtoMapper.mapToResponse(user);
    }




    public boolean existById(Long id) {
        log.info("UsersService::existById()::Checking user existence by id started");
        return userRepository.existsById(id);

    }

    public UserResponse update(Long id, UserRequest userRequest) {
        log.info("UsersService::update()::Updating user started");
        User user = userRepository.findById(id).orElseThrow(() -> new DataNotFoundException("User not found with id: " + id));
        User UpdatedUuser = UserDtoMapper.mapToEntity(userRequest, passwordEncoder);
        UpdatedUuser.setId(id);
        UpdatedUuser.setPassword(user.getPassword());
        log.info("UsersService::update()::Updating user completed");
        return UserDtoMapper.mapToResponse(user);
    }
}
