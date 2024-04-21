package org.project.job.repository;

import org.project.job.entity.Employer;
import org.project.job.entity.Job;
import org.project.job.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    @Query(value = "SELECT id, description, diploma_require, gender_require, is_active, payroll_payment, position, salary,\n" +
            "title,work_address,work_require,created_date from job", nativeQuery = true)
    List<Job> findAllExcludeEmployer();


    List<Job> findByEmployer(Employer employer);
}
