package org.project.job.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.project.job.dto.UserDto;
import org.project.job.entity.User;
import org.project.job.entity.VerificationToken;
import org.project.job.event.RegistrationCompleteEvent;
import org.project.job.service.UserService;
import org.project.job.service.VerificationTokenService;
import org.project.job.utility.ApplicationUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;

@RestController
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired private UserService userService;
    @Autowired private VerificationTokenService verificationTokenService;
    @Autowired private ApplicationEventPublisher publisher;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @ModelAttribute UserDto userDto,
                                       BindingResult result, Model model, HttpServletRequest request) {
        if(!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            return ResponseEntity.badRequest().body("Vui lòng nhập đúng mật khẩu");
        }
        if(result.hasErrors()) {
            model.addAttribute("user", userDto);
        }
        System.out.println(result);
        User user = userService.registerUser(userDto);
        if(user == null)
            return ResponseEntity.badRequest().body("Email đã được sử dụng");
        publisher.publishEvent(new RegistrationCompleteEvent(user, ApplicationUrl.getUrl(request)));
        return ResponseEntity.ok("Đăng ký tài khoản thành công, vào email để xác thực tài khoản");
    }

    @GetMapping("/verifyEmail")
    public ResponseEntity<?> verifyEmail(@RequestParam String token) {
        Optional<VerificationToken> verificationTokenOptional =
                verificationTokenService.findByToken(token);
        if(verificationTokenOptional.isPresent() && verificationTokenOptional.get().getUser().getIsEnabled()) {
            return ResponseEntity.ok("Tài khoản đã được xác thực");
        }
        String result = verificationTokenService.validateVerificationToken(token);
        switch (result.toLowerCase()) {
            case "valid":
                return ResponseEntity.ok("Xác thực tài khoản thành công");
            case "invalid":
                return ResponseEntity.badRequest().body("Xác thực không hợp lệ");
            default:
                return ResponseEntity.badRequest().body("Token hết thời hạn");
        }
    }

}
