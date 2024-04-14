package org.project.job.service;

import org.project.job.entity.User;
import org.project.job.entity.VerificationToken;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface VerificationTokenService {
    void saveVerificationTokenForUser(User user, String token);

    Optional<VerificationToken> findByToken(String token);

    String validateVerificationToken(String token);

}
