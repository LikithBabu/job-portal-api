package com.likithbabugarlapati.jobportalapi.dto;

import com.likithbabugarlapati.jobportalapi.enums.JobStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data @Builder
public class SavedJobResponse {
    private Long savedJobId;
    private Long jobId;
    private String jobTitle;
    private String jobLocation;
    private String jobType;
    private Double salaryMin;
    private Double salaryMax;
    private JobStatus jobStatus;
    private String postedByName;
    private LocalDateTime savedAt;
}

