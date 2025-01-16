package com.gingerx.focusservice.dtoMapper;

import com.gingerx.focusservice.dto.UserRequest;
import com.gingerx.focusservice.dto.UserResponse;
import com.gingerx.focusservice.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserDtoMapper {

    public static User mapToEntity(UserRequest userRequest, PasswordEncoder passwordEncoder){
        return User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .email(userRequest.getEmail())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .vCode(userRequest.getVCode())
                .vCodeTime(userRequest.getVCodeTime())
                .status(userRequest.getStatus())
                .build();
    }

    public static UserResponse mapToResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .status(user.getStatus())
                .build();
    }

    public static UserRequest mapToRequest(User user) {
        return UserRequest.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(user.getPassword())
                .vCode(user.getVCode())
                .vCodeTime(user.getVCodeTime())
                .status(user.getStatus())
                .build();
    }
}
