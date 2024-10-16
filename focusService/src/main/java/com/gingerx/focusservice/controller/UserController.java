package com.gingerx.focusservice.controller;

import com.gingerx.focusservice.dto.UserRequest;
import com.gingerx.focusservice.dto.UserResponse;
import com.gingerx.focusservice.response.ApiResponse;
import com.gingerx.focusservice.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UsersService userService;

    @PutMapping("/update")
    public ApiResponse<UserResponse> update(Long id, UserRequest userRequest) {
        return new ApiResponse<>(ApiResponse.SUCCESS, "User updated successfully", userService.update(id, userRequest));
    }

}
