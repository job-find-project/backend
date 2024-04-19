package org.project.job.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.project.job.entity.User;
import org.project.job.entity.VerificationToken;
import org.project.job.repository.UserRepository;
import org.project.job.repository.VerificationTokenRepository;
import org.project.job.response.UserDetailsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired private UserDetailsService userDetailsService;
    @Autowired private VerificationTokenRepository verificationTokenRepository;
    @Autowired private UserRepository userRepository;

    private final static String[] paths = {
            "/**",
            "/registration/**"
    };

    private final static String[] authenticatedPath = {
    };

    private final static String[] employer = {
            "/employer/**"
    };

    private final static String[] admin = {
            "/admin/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        author -> author
                                .requestMatchers(authenticatedPath).authenticated()
                                .requestMatchers(employer).hasAnyAuthority("EMPLOYER")
                                .requestMatchers(admin).hasAnyAuthority("ADMIN")
                                .requestMatchers(paths).permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin(
                        form -> form
                                .loginProcessingUrl("/login")
                                .usernameParameter("email")
                                .successHandler((request, response, authentication) -> {
                                    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                                    User user = userRepository.findByEmail(userDetails.getUsername()).get();
                                    VerificationToken verificationToken = verificationTokenRepository.findByUser(user);

                                    UserDetailsResponse userDetailsResponse = UserDetailsResponse.builder()
                                            .id(user.getId())
                                            .userName(userDetails.getUsername())
                                            .authorities((List<GrantedAuthority>) userDetails.getAuthorities())
                                            .token(verificationToken.getToken())
                                            .build();

                                    response.setContentType("application/json");
                                    new ObjectMapper().writeValue(response.getWriter(), userDetailsResponse);
                                })
                                .permitAll()
                )
                .logout(
                        log -> log.invalidateHttpSession(true)
                                .clearAuthentication(true)
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .logoutSuccessUrl("/home")
                                .deleteCookies("JSESSIONID")
                                .permitAll()
                )
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
}

