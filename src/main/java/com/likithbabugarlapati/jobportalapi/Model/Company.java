package com.likithbabugarlapati.jobportalapi.Model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "companies")
@Getter @Setter
@Builder
@NoArgsConstructor @AllArgsConstructor
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String website;
    private String location;
    private String industry;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruiter_id", nullable = false)
    private User recruiter;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}