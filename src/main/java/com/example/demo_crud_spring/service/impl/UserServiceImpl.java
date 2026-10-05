package com.example.demo_crud_spring.service.impl;

import com.example.demo_crud_spring.exception.ResourceNotFoundException;
import com.example.demo_crud_spring.model.dto.UserDto;
import com.example.demo_crud_spring.model.entity.User;
import com.example.demo_crud_spring.model.request.UserRequest;
import com.example.demo_crud_spring.repository.UserRepository;
import com.example.demo_crud_spring.service.FileStorageService;
import com.example.demo_crud_spring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final FileStorageService fileStorageService;

    @Override
    @Transactional
    public UserDto createUser(UserRequest request) {
        User user = toEntity(request, new User());
        User saved = userRepository.save(user);
        return toDto(saved);
    }

    @Override
    @Transactional
    public UserDto updateUser(Long id, UserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        toEntity(request, user);
        User updated = userRepository.save(user);
        return toDto(updated);
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return toDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public UserDto uploadUserImage(Long id, MultipartFile image) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        String storedFilename = fileStorageService.store(image);
        String previousImage = user.getUserImg();

        user.setUserImg(storedFilename);
        User updated = userRepository.save(user);

        if (previousImage != null) {
            fileStorageService.delete(previousImage);
        }

        return toDto(updated);
    }

    private User toEntity(UserRequest request, User user) {
        user.setUserImg(request.getUserImg());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setDateOfBirth(request.getDateOfBirth());
        return user;
    }

    private UserDto toDto(User user) {
        return new UserDto(
                user.getId(),
                user.getUserImg(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getDateOfBirth()
        );
    }
}
