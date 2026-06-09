package com.likithbabugarlapati.jobportalapi.Service;

import com.likithbabugarlapati.jobportalapi.Model.Application;
import com.likithbabugarlapati.jobportalapi.Model.Job;
import com.likithbabugarlapati.jobportalapi.Model.User;
import com.likithbabugarlapati.jobportalapi.Repository.ApplicationRepository;
import com.likithbabugarlapati.jobportalapi.Repository.JobRepository;
import com.likithbabugarlapati.jobportalapi.Repository.UserRepository;
import com.likithbabugarlapati.jobportalapi.dto.ApplicationResponse;
import com.likithbabugarlapati.jobportalapi.dto.StatusUpdateRequest;
import com.likithbabugarlapati.jobportalapi.enums.ApplicationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplicationService
{
    private final ApplicationRepository applicationRepository;
    private final JobRepository jobRepository;
    private final UserRepository userRepository;
    private final FileStorageService fileStorageService;
    private final EmailService emailService;

    public ApplicationResponse applyToJob(Long jobId, MultipartFile resume) {
        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        User applicant = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        if (applicationRepository.existsByJobIdAndApplicantId(jobId, applicant.getId())) {
            throw new RuntimeException("You have already applied to this job");
        }

        String resumePath = null;
        if (resume != null && !resume.isEmpty()) {
            resumePath = fileStorageService.storeFile(resume);
        }

        Application application = Application.builder()
                .job(job)
                .applicant(applicant)
                .resumePath(resumePath)
                .status(ApplicationStatus.PENDING)
                .build();

        Application saved = applicationRepository.save(application);
        return mapToResponse(saved);
    }

    public List<ApplicationResponse> getMyApplications() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        User applicant = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return applicationRepository.findByApplicantId(applicant.getId())
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<ApplicationResponse> getApplicationsForJob(Long jobId) {
        return applicationRepository.findByJobId(jobId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public ApplicationResponse updateStatus(Long applicationId, StatusUpdateRequest request) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        application.setStatus(request.getStatus());
        Application saved = applicationRepository.save(application);

        try {
            emailService.sendApplicationStatusEmail(
                    saved.getApplicant().getEmail(),
                    saved.getApplicant().getName(),
                    saved.getJob().getTitle(),
                    saved.getStatus().name()
            );
        } catch (Exception e) {
            System.err.println("Email failed to send: " + e.getMessage());
        }

        return mapToResponse(saved);
    }

    private ApplicationResponse mapToResponse(Application application) {
        return ApplicationResponse.builder()
                .id(application.getId())
                .jobId(application.getJob().getId())
                .jobTitle(application.getJob().getTitle())
                .applicantName(application.getApplicant().getName())
                .applicantEmail(application.getApplicant().getEmail())
                .resumePath(application.getResumePath())
                .status(application.getStatus())
                .appliedAt(application.getAppliedAt())
                .build();
    }
}
