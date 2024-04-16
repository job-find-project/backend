package org.project.job.service;

import org.project.job.dto.EmployerDto;
import org.project.job.entity.*;
import org.project.job.repository.EmployerRepository;
import org.project.job.repository.RoleRepository;
import org.project.job.repository.UserRepository;
import org.project.job.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployerServiceImpl implements EmployerService{

    @Autowired private EmployerRepository employerRepository;
    @Autowired private VerificationTokenRepository verificationTokenRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private RoleRepository roleRepository;

    @Override
    public String registerEmployer(String token, EmployerDto employerDto) {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        if(verificationToken.isEmpty()) {
            return "invalid";
        }
        VerificationToken tmp = verificationToken.get();
        Role role = roleRepository.findByName("EMPLOYER");

        // lấy user ra và set các thuộc tính của employer nhập sau đó lưu lại
        User user = tmp.getUser();
        user.setFullName(employerDto.getFullName().isEmpty() ? user.getFullName() : employerDto.getFullName());
        user.setMobilePhone(employerDto.getMobilePhone());
        if(role == null) {
            role = new Role("EMPLOYER");
            roleRepository.save(role);
            user.getRoles().add(role);
        } else {
            user.getRoles().add(role);
        }

        // 1 employer thuộc về 1 company nên ta tạo mới 1 company và set các thuộc tính
        Company company = Company.builder()
                .name(employerDto.getCompanyName())
                .sector(employerDto.getCompanySector())
                .description(employerDto.getCompanyDescription())
                .address(employerDto.getCompanyAddress())
                .build();

        Employer employer = Employer.builder()
                .company(company)
                .user(user)
                .build();

        userRepository.save(user);
        employerRepository.save(employer);
        return "valid";
    }
}
