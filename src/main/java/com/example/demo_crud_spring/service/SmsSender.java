package com.example.demo_crud_spring.service;

public interface SmsSender {

    void sendWelcomeSms(String phoneNumber, String firstName);
}
