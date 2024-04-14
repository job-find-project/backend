package org.project.job.service.impl;

import org.project.job.dto.UserDto;
import org.project.job.entity.Role;
import org.project.job.entity.User;
import org.project.job.repository.RoleRepository;
import org.project.job.repository.UserRepository;
import org.project.job.service.UserService;
import org.project.job.utility.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private UserRepository userRepository;
    @Autowired private ImageUtils imageUtils;
    @Autowired private RoleRepository roleRepository;

    @Override
    public User registerUser(UserDto userDto) {
        Role role = roleRepository.findByName("CANDIDATE");
        if(role == null) {
            role = new Role("CANDIDATE");
        }
        Optional<User> check = userRepository.findByEmail(userDto.getEmail());

        if(check.isPresent()) {
            return null;
        }

        User user = User.builder()
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .roles(Arrays.asList(role))
                .isEnabled(false)
                .build();
        return userRepository.save(user);
    }
}
