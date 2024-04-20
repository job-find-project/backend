package org.project.job.service;

import org.project.job.dto.UserDto;
import org.project.job.entity.User;
import org.project.job.response.UserDetailsResponse;
import org.springframework.http.ResponseEntity;

public interface UserService {
    User registerUser(UserDto userDto);

    User getUserById(Long id);

    void deleteUserById(Long id);

    ResponseEntity<?> login(String email, String password);
}
