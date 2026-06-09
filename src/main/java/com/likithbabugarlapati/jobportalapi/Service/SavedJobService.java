package com.likithbabugarlapati.jobportalapi.Service;

import com.likithbabugarlapati.jobportalapi.Model.Job;
import com.likithbabugarlapati.jobportalapi.Model.SavedJob;
import com.likithbabugarlapati.jobportalapi.Model.User;
import com.likithbabugarlapati.jobportalapi.Repository.JobRepository;
import com.likithbabugarlapati.jobportalapi.Repository.SavedJobRepository;
import com.likithbabugarlapati.jobportalapi.Repository.UserRepository;
import com.likithbabugarlapati.jobportalapi.dto.SavedJobResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SavedJobService {

    private final SavedJobRepository savedJobRepository;
    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    public String toggleSaveJob(Long jobId) {
        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        User applicant = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        if (savedJobRepository.existsByApplicantIdAndJobId(applicant.getId(), jobId)) {
            savedJobRepository.deleteByApplicantIdAndJobId(applicant.getId(), jobId);
            return "Job removed from saved list";
        } else {
            SavedJob savedJob = SavedJob.builder()
                    .applicant(applicant)
                    .job(job)
                    .build();
            savedJobRepository.save(savedJob);
            return "Job saved successfully";
        }
    }

    public List<SavedJobResponse> getMySavedJobs() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        User applicant = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return savedJobRepository.findByApplicantId(applicant.getId())
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public boolean isJobSaved(Long jobId) {
        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        User applicant = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return savedJobRepository.existsByApplicantIdAndJobId(applicant.getId(), jobId);
    }

    private SavedJobResponse mapToResponse(SavedJob savedJob) {
        return SavedJobResponse.builder()
                .savedJobId(savedJob.getId())
                .jobId(savedJob.getJob().getId())
                .jobTitle(savedJob.getJob().getTitle())
                .jobLocation(savedJob.getJob().getLocation())
                .jobType(savedJob.getJob().getJobType())
                .salaryMin(savedJob.getJob().getSalaryMin())
                .salaryMax(savedJob.getJob().getSalaryMax())
                .jobStatus(savedJob.getJob().getStatus())
                .postedByName(savedJob.getJob().getPostedBy().getName())
                .savedAt(savedJob.getSavedAt())
                .build();
    }
}