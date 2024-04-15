package org.project.job.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String sector;
    private String description;
    private String address;

    @ManyToMany
    @JoinTable(
            name = "company_review",
            joinColumns = @JoinColumn(
                    name = "company_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "review_id",
                    referencedColumnName = "id"
            )
    )
    private List<Review> reviews = new ArrayList<>();
}
