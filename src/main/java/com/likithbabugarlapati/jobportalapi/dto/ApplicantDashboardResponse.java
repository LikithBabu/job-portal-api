package com.likithbabugarlapati.jobportalapi.dto;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class ApplicantDashboardResponse {
    private long totalApplications;
    private long pendingApplications;
    private long reviewedApplications;
    private long acceptedApplications;
    private long rejectedApplications;
    private long savedJobs;
}