package com.Hindol.Notification_Service.Event;

import lombok.Data;

@Data
public class UserCreatedEvent {
    private Long id;
    private String email;
}
