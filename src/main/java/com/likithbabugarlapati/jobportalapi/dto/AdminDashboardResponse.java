package com.likithbabugarlapati.jobportalapi.dto;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class AdminDashboardResponse {
    private long totalUsers;
    private long totalRecruiters;
    private long totalApplicants;
    private long totalJobs;
    private long openJobs;
    private long closedJobs;
    private long totalApplications;
    private long pendingApplications;
    private long acceptedApplications;
    private long rejectedApplications;
    private long totalCompanies;
}