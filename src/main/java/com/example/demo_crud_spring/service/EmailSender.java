package com.example.demo_crud_spring.service;

public interface EmailSender {

    void sendWelcomeEmail(String toEmail, String firstName);
}
