package com.Hindol.Scheduling.Controller;

import com.Hindol.Scheduling.Entity.User;
import com.Hindol.Scheduling.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping("/info")
    public ResponseEntity<User> getUserReport() {
        User user = userService.getUserReport();
        return ResponseEntity.ok(user);
    }
    @GetMapping("/email")
    public ResponseEntity<String> scheduleEmail() {
        userService.sendEmail();
        return ResponseEntity.ok("OK");
    }
}
