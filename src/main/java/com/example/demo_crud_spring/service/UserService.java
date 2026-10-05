package com.example.demo_crud_spring.service;

import com.example.demo_crud_spring.model.dto.UserDto;
import com.example.demo_crud_spring.model.request.UserRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    UserDto createUser(UserRequest request);

    UserDto updateUser(Long id, UserRequest request);

    UserDto getUserById(Long id);

    List<UserDto> getAllUsers();

    void deleteUser(Long id);

    UserDto uploadUserImage(Long id, MultipartFile image);
}
