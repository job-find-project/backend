package org.project.job.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private Integer salary;
    private String payrollPayment;
    private String workAddress;
    private Boolean isActive;
    private String position;
    private String description;
    private String diplomaRequire;
    private String workRequire;
    private String genderRequire;

    @Temporal(TemporalType.DATE)
    private Date createdDate;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL)
    private List<JobType> types;

    @ManyToOne
    @JoinColumn(name = "employer_id", referencedColumnName = "id")
    private Employer employer;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "job_candidate",
            joinColumns = @JoinColumn(
                    name = "job_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "candidate_id",
                    referencedColumnName = "id"
            )
    )
    private List<Candidate> candidates = new ArrayList<>();

    @PrePersist
    private void createdDate() {
        this.createdDate = new Date();
    }
}
