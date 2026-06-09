package com.likithbabugarlapati.jobportalapi.dto;

import com.likithbabugarlapati.jobportalapi.enums.JobStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class JobRequest
{
    @NotBlank
    private String title;

    @NotBlank
    private String description;

    private String location;
    private String jobType;
    private Double salaryMin;
    private Double salaryMax;

    @NotNull
    private JobStatus status;
    private LocalDate expiryDate;
}
