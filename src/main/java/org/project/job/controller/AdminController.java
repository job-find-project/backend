package org.project.job.controller;

import org.project.job.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired private UserService userService;

    @DeleteMapping("/delete")
    private ResponseEntity<?> deleteUserById(@RequestParam Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok("delete success");
    }
}
