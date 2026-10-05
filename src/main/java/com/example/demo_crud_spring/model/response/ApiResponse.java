package com.example.demo_crud_spring.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApiResponse <T>{
    private String message;
    private T payload;
    private LocalDateTime localDateTime;
}
