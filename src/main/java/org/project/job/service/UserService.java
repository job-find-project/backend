package org.project.job.service;

import org.project.job.dto.UserDto;
import org.project.job.entity.User;

public interface UserService {
    User registerUser(UserDto userDto);
}
