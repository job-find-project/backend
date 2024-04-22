package org.project.job.repository;

import org.project.job.entity.Candidate;
import org.project.job.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    Candidate findByUser(User user);
}
