package com.example.demo_crud_spring.service;

import com.example.demo_crud_spring.model.dto.UserDto;

public interface NotificationService {

    void notifyUserRegistered(UserDto user);
}
