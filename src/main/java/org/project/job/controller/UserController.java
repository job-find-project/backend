package org.project.job.controller;

import org.project.job.entity.User;
import org.project.job.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired private UserService userService;

    @GetMapping("/getById")
    public User getUserById(@RequestParam Long id) {
        return userService.getUserById(id);
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
        return userService.login(email, password);
    }

}
