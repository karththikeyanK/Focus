package com.gingerx.focusservice.controller;

import com.gingerx.focusservice.dto.UserRequest;
import com.gingerx.focusservice.dto.UserResponse;
import com.gingerx.focusservice.response.ApiResponse;
import com.gingerx.focusservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> update(@PathVariable Long id,@RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(new ApiResponse<>(ApiResponse.SUCCESS, "User updated successfully", userService.update(id,userRequest)));
    }

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAll() {
        return ResponseEntity.ok(new ApiResponse<>(ApiResponse.SUCCESS, "Users retrieved successfully", userService.getAll()));
    }
}
