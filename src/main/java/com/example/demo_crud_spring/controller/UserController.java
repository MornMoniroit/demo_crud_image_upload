package com.example.demo_crud_spring.controller;

import com.example.demo_crud_spring.model.dto.UserDto;
import com.example.demo_crud_spring.model.request.UserRequest;
import com.example.demo_crud_spring.model.response.ApiResponse;
import com.example.demo_crud_spring.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<UserDto>> createUser(@Valid @RequestBody UserRequest request) {
        UserDto created = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("User created successfully", created, LocalDateTime.now()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDto>> updateUser(@PathVariable Long id,
                                                            @Valid @RequestBody UserRequest request) {
        UserDto updated = userService.updateUser(id, request);
        return ResponseEntity.ok(new ApiResponse<>("User updated successfully", updated, LocalDateTime.now()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDto>> getUserById(@PathVariable Long id) {
        UserDto user = userService.getUserById(id);
        return ResponseEntity.ok(new ApiResponse<>("User retrieved successfully", user, LocalDateTime.now()));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserDto>>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(new ApiResponse<>("Users retrieved successfully", users, LocalDateTime.now()));
    }

    @PostMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<UserDto>> uploadUserImage(@PathVariable Long id,
                                                                 @RequestParam("image") MultipartFile image) {
        UserDto updated = userService.uploadUserImage(id, image);
        return ResponseEntity.ok(new ApiResponse<>("User image uploaded successfully", updated, LocalDateTime.now()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(new ApiResponse<>("User deleted successfully", null, LocalDateTime.now()));
    }
}
