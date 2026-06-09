package com.likithbabugarlapati.jobportalapi.Service;

import com.likithbabugarlapati.jobportalapi.Repository.*;
import com.likithbabugarlapati.jobportalapi.Model.User;
import com.likithbabugarlapati.jobportalapi.dto.*;
import com.likithbabugarlapati.jobportalapi.enums.ApplicationStatus;
import com.likithbabugarlapati.jobportalapi.enums.JobStatus;
import com.likithbabugarlapati.jobportalapi.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService
{

    private final UserRepository userRepository;
    private final JobRepository jobRepository;
    private final ApplicationRepository applicationRepository;
    private final CompanyRepository companyRepository;
    private final SavedJobRepository savedJobRepository;

    public AdminDashboardResponse getAdminDashboard() {
        long totalUsers = userRepository.count();
        long totalRecruiters = userRepository.countByRole(Role.RECRUITER);
        long totalApplicants = userRepository.countByRole(Role.APPLICANT);
        long totalJobs = jobRepository.count();
        long openJobs = jobRepository.countByStatus(JobStatus.OPEN);
        long closedJobs = jobRepository.countByStatus(JobStatus.CLOSED);
        long totalApplications = applicationRepository.count();
        long pending = applicationRepository.countByStatus(ApplicationStatus.PENDING);
        long accepted = applicationRepository.countByStatus(ApplicationStatus.ACCEPTED);
        long rejected = applicationRepository.countByStatus(ApplicationStatus.REJECTED);
        long totalCompanies = companyRepository.count();

        return AdminDashboardResponse.builder()
                .totalUsers(totalUsers)
                .totalRecruiters(totalRecruiters)
                .totalApplicants(totalApplicants)
                .totalJobs(totalJobs)
                .openJobs(openJobs)
                .closedJobs(closedJobs)
                .totalApplications(totalApplications)
                .pendingApplications(pending)
                .acceptedApplications(accepted)
                .rejectedApplications(rejected)
                .totalCompanies(totalCompanies)
                .build();
    }

    public RecruiterDashboardResponse getRecruiterDashboard() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        User recruiter = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        long totalJobsPosted = jobRepository.countByPostedById(recruiter.getId());
        long openJobs = jobRepository.countByPostedByIdAndStatus(recruiter.getId(), JobStatus.OPEN);
        long closedJobs = jobRepository.countByPostedByIdAndStatus(recruiter.getId(), JobStatus.CLOSED);

        var myJobs = jobRepository.findByPostedById(recruiter.getId());
        var jobIds = myJobs.stream().map(j -> j.getId()).collect(Collectors.toList());

        long totalApplications = applicationRepository.countByJobIdIn(jobIds);
        long pending = applicationRepository.countByJobIdInAndStatus(jobIds, ApplicationStatus.PENDING);
        long accepted = applicationRepository.countByJobIdInAndStatus(jobIds, ApplicationStatus.ACCEPTED);
        long rejected = applicationRepository.countByJobIdInAndStatus(jobIds, ApplicationStatus.REJECTED);

        Map<String, Long> applicationPerJob = myJobs.stream()
                .collect(Collectors.toMap(
                        job -> job.getTitle() + " (ID:" + job.getId() + ")",  // ✅ make key unique using ID
                        job -> applicationRepository.countByJobId(job.getId()),
                        (existing, replacement) -> existing + replacement      // ✅ merge duplicates
                ));

        return RecruiterDashboardResponse.builder()
                .totalJobsPosted(totalJobsPosted)
                .openJobs(openJobs)
                .closedJobs(closedJobs)
                .totalApplicationsReceived(totalApplications)
                .pendingApplications(pending)
                .acceptedApplications(accepted)
                .rejectedApplications(rejected)
                .applicationPerJob(applicationPerJob)
                .build();
    }

    public ApplicantDashboardResponse getApplicantDashboard() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        User applicant = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        long total = applicationRepository.countByApplicantId(applicant.getId());
        long pending = applicationRepository.countByApplicantIdAndStatus(applicant.getId(), ApplicationStatus.PENDING);
        long reviewed = applicationRepository.countByApplicantIdAndStatus(applicant.getId(), ApplicationStatus.REVIEWED);
        long accepted = applicationRepository.countByApplicantIdAndStatus(applicant.getId(), ApplicationStatus.ACCEPTED);
        long rejected = applicationRepository.countByApplicantIdAndStatus(applicant.getId(), ApplicationStatus.REJECTED);
        long savedJobs = savedJobRepository.countByApplicantId(applicant.getId());

        return ApplicantDashboardResponse.builder()
                .totalApplications(total)
                .pendingApplications(pending)
                .reviewedApplications(reviewed)
                .acceptedApplications(accepted)
                .rejectedApplications(rejected)
                .savedJobs(savedJobs)
                .build();
    }
}