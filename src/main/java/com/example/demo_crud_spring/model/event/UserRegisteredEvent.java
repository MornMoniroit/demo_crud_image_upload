package com.example.demo_crud_spring.model.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRegisteredEvent implements Serializable {

    private Long userId;
    private String email;
    private String phoneNumber;
    private String firstName;
    private String lastName;
}
