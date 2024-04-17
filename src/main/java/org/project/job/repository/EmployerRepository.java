package org.project.job.repository;

import org.project.job.entity.Employer;
import org.project.job.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployerRepository extends JpaRepository<Employer, Long> {
    Employer findByUser(User user);
}
