package org.project.job.service;

import org.project.job.dto.EmployerDto;
import org.project.job.dto.JobDto;
import org.project.job.entity.*;
import org.project.job.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EmployerServiceImpl implements EmployerService{

    @Autowired private EmployerRepository employerRepository;
    @Autowired private VerificationTokenRepository verificationTokenRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private RoleRepository roleRepository;
    @Autowired private JobRepository jobRepository;
    @Autowired private JobTypeRepository jobTypeRepository;

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

    @Override
    public String postJob(String token, JobDto jobDto) {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        if(verificationToken.isEmpty()) {
            return "Token không hợp lệ";
        }
        User user = verificationToken.get().getUser();
        Role role = roleRepository.findByName("EMPLOYER");
        if(!user.getRoles().contains(role)) {
            return "Bạn không có quyền";
        }
        Employer employer = employerRepository.findByUser(user);

        Job job = Job.builder()
                .title(jobDto.getTitle())
                .salary(jobDto.getSalary())
                .payrollPayment(jobDto.getPayrollPayment())
                .workAddress(jobDto.getWorkAddress())
                .isActive(true)
                .position(jobDto.getPosition())
                .description(jobDto.getDescription())
                .diplomaRequire(jobDto.getDiplomaRequire())
                .workRequire(jobDto.getWorkRequire())
                .genderRequire(jobDto.getGenderRequire())
                .employer(employer)
                .build();
        JobType jobType = jobTypeRepository.findByType(jobDto.getJobType());

        jobRepository.save(job);

        if(Objects.isNull(jobType)) {
            jobType = JobType.builder()
                    .type(jobDto.getJobType())
                    .job(job)
                    .build();
            jobTypeRepository.save(jobType);
        }
        return "valid";
    }
}
