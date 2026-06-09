package com.likithbabugarlapati.jobportalapi.dto;

import com.likithbabugarlapati.jobportalapi.enums.JobStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class JobResponse
{
    private Long id;
    private String title;
    private String description;
    private String location;
    private String jobType;
    private Double salaryMin;
    private Double salaryMax;
    private JobStatus status;
    private String postedByName;
    private String postedByEmail;
    private LocalDateTime createdAt;
    private LocalDate expiryDate;
    private boolean isExpired;
}
