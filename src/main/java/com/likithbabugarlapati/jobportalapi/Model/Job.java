package com.likithbabugarlapati.jobportalapi.Model;

import com.likithbabugarlapati.jobportalapi.enums.JobStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Job
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    private String location;
    private String jobType;
    private Double salaryMin;
    private Double salaryMax;
    private LocalDate expiryDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JobStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "posted_by",nullable = false)
    private User postedBy;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}
