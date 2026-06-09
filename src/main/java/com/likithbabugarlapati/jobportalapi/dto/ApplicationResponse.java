package com.likithbabugarlapati.jobportalapi.dto;

import com.likithbabugarlapati.jobportalapi.enums.ApplicationStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ApplicationResponse
{
    private Long id;
    private Long jobId;
    private String jobTitle;
    private String applicantName;
    private String applicantEmail;
    private String resumePath;
    private ApplicationStatus status;
    private LocalDateTime appliedAt;
}
