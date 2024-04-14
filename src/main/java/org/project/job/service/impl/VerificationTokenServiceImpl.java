package org.project.job.service.impl;

import org.project.job.entity.User;
import org.project.job.entity.VerificationToken;
import org.project.job.repository.UserRepository;
import org.project.job.repository.VerificationTokenRepository;
import org.project.job.service.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Optional;

@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void saveVerificationTokenForUser(User user, String token) {
        VerificationToken verificationToken = new VerificationToken(user, token);
        verificationTokenRepository.save(verificationToken);
    }

    @Override
    public Optional<VerificationToken> findByToken(String token) {
        return verificationTokenRepository.findByToken(token);
    }

    @Override
    public String validateVerificationToken(String token) {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        if(verificationToken.isEmpty())
            return "invalid";
        User user = verificationToken.get().getUser();
        Calendar calendar = Calendar.getInstance();
        if(verificationToken.get().getExpirationTime().getTime() - calendar.getTime().getTime() <= 0) {
            verificationTokenRepository.delete(verificationToken.get());
            return "expired";
        }
        user.setIsEnabled(true);
        userRepository.save(user);
        return "valid";
    }
}
