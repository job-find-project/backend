package org.project.job.event.listener;

import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;

import org.project.job.entity.User;
import org.project.job.event.RegistrationCompleteEvent;
import org.project.job.service.VerificationTokenService;
import org.project.job.utility.SendMail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

@Component
@Slf4j
public class RegistrationCompleteListener implements ApplicationListener<RegistrationCompleteEvent> {

    @Autowired
    private VerificationTokenService verificationTokenService;

    @Autowired
    private SendMail sendMail;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        verificationTokenService.saveVerificationTokenForUser(user, token);

        String verifyMailUrl = event.getConfirmationUrl() + "/registration/verifyEmail?token=" + token;

        try {
            sendMail.sendVerificationEmail(user, verifyMailUrl);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        log.info("Click the link to verify your account, {}", verifyMailUrl);
    }
}
