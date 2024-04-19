package org.project.job.controller;

import org.project.job.dto.EmployerDto;
import org.project.job.dto.JobDto;
import org.project.job.entity.Job;
import org.project.job.service.EmployerService;
import org.project.job.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employer")
public class EmployerController {

    @Autowired private EmployerService employerService;
    @Autowired private JobService jobService;

    @PostMapping("/register")
    public ResponseEntity<?> registerEmployer(@RequestParam String token, @ModelAttribute EmployerDto employerDto) {
        String message = employerService.registerEmployer(token, employerDto);
        return message.equals("valid") ?
                ResponseEntity.ok("Bạn đã là nhà tuyển dụng") :
                ResponseEntity.badRequest().body("Token không hợp lệ");
    }

    @PostMapping("/post_job")
    public ResponseEntity<?> postJob(@RequestParam String token, JobDto jobDto) {
        String message = employerService.postJob(token, jobDto);
        return message.equals("valid") ?
                ResponseEntity.ok("Đăng công việc thành công") :
                ResponseEntity.badRequest().body(message);
    }

    @GetMapping("/getJobList")
    public ResponseEntity<?> getJobList(@RequestParam String token) {
        List<Job> jobs = jobService.getJobList(token);
        return jobs == null ?
                ResponseEntity.badRequest().body("Token không hợp lệ") :
                ResponseEntity.ok(jobs);
    }

    @PatchMapping("/toggleJob")
    public ResponseEntity<?> toggleJob(@RequestParam String token, @RequestParam Long id) {
        jobService.toggleJob(token, id);
        return ResponseEntity.ok("Đã ẩn công việc");
    }

}
