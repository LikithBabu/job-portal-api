package com.likithbabugarlapati.jobportalapi.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data @Builder
public class RecruiterDashboardResponse {
    private long totalJobsPosted;
    private long openJobs;
    private long closedJobs;
    private long totalApplicationsReceived;
    private long pendingApplications;
    private long acceptedApplications;
    private long rejectedApplications;
    private Map<String, Long> applicationPerJob;
}