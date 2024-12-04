package com.Hindol.User_Service.Event;

import lombok.Data;

@Data
public class UserCreatedEvent {
    private Long id;
    private String email;
}
