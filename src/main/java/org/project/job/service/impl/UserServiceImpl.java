package org.project.job.service.impl;

import jakarta.transaction.Transactional;
import org.project.job.dto.UserDto;
import org.project.job.entity.Review;
import org.project.job.entity.Role;
import org.project.job.entity.User;
import org.project.job.entity.VerificationToken;
import org.project.job.repository.ReviewRepository;
import org.project.job.repository.RoleRepository;
import org.project.job.repository.UserRepository;
import org.project.job.repository.VerificationTokenRepository;
import org.project.job.response.UserDetailsResponse;
import org.project.job.service.UserService;
import org.project.job.utility.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private UserRepository userRepository;
    @Autowired private ImageUtils imageUtils;
    @Autowired private RoleRepository roleRepository;
    @Autowired private VerificationTokenRepository verificationTokenRepository;
    @Autowired private ReviewRepository reviewRepository;

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
                .password(String.valueOf(userDto.getPassword().hashCode()))
                .roles(Arrays.asList(role))
                .isEnabled(false)
                .build();
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    @Transactional
    public void deleteUserById(Long id) {
        User user = userRepository.findById(id).get();
        verificationTokenRepository.deleteByUser(user);
        reviewRepository.deleteAll(user.getReviews());
        userRepository.deleteById(id);
    }

    @Override
    public ResponseEntity<?> login(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isEmpty())
            return ResponseEntity.badRequest().body("not_register");
        User user = userOptional.get();
        if(!user.getIsEnabled())
            return ResponseEntity.badRequest().body("not_verified");
        if(!(password.hashCode() + "").equals(user.getPassword()))
            return ResponseEntity.badRequest().body("password_incorrect");
        VerificationToken verificationToken = verificationTokenRepository.findByUser(user);
        UserDetailsResponse userDetailsResponse = UserDetailsResponse.builder()
                .id(user.getId())
                .userName(user.getEmail())
                .roles(user.getRoles())
                .token(verificationToken.getToken())
                .build();
        return ResponseEntity.ok(userDetailsResponse);
    }
}
