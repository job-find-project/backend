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

    @GetMapping("/register-form")
    public ModelAndView showRegistrationForm() {
        ModelAndView modelAndView = new ModelAndView("register");
        modelAndView.addObject("user", new UserDto());
        return modelAndView;
    }

    @PostMapping("/register")
    public RedirectView registerUser(@Valid @ModelAttribute UserDto userDto,
                                     BindingResult result, Model model, HttpServletRequest request) {
        if(!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            return new RedirectView("/registration/register-form?invalidc_f");
        }
        if(result.hasErrors()) {
            model.addAttribute("user", userDto);
        }
        System.out.println(result);
        User user = userService.registerUser(userDto);
        if(user == null)
            return new RedirectView("/registration/register-form?used");
        publisher.publishEvent(new RegistrationCompleteEvent(user, ApplicationUrl.getUrl(request)));
        return new RedirectView("/registration/register-form?success");
    }

    @GetMapping("/verifyEmail")
    public RedirectView verifyEmail(@RequestParam String token) {
        Optional<VerificationToken> verificationTokenOptional =
                verificationTokenService.findByToken(token);
        if(verificationTokenOptional.isPresent() && verificationTokenOptional.get().getUser().getIsEnabled()) {
            return new RedirectView("/login?verified");
        }
        String result = verificationTokenService.validateVerificationToken(token);
        switch (result.toLowerCase()) {
            case "valid":
                return new RedirectView("/login?valid");
            case "invalid":
                return new RedirectView("/error?invalid");
            default:
                return new RedirectView("/error?expired");
        }
    }

}
