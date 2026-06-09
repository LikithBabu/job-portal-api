package com.likithbabugarlapati.jobportalapi.Service;

import com.likithbabugarlapati.jobportalapi.Model.Job;
import com.likithbabugarlapati.jobportalapi.Model.User;
import com.likithbabugarlapati.jobportalapi.Repository.JobRepository;
import com.likithbabugarlapati.jobportalapi.Repository.UserRepository;
import com.likithbabugarlapati.jobportalapi.dto.JobRequest;
import com.likithbabugarlapati.jobportalapi.dto.JobResponse;
import com.likithbabugarlapati.jobportalapi.enums.JobStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    public JobResponse createJob(JobRequest request)
    {
        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        User recruiter = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Job job = Job.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .location(request.getLocation())
                .jobType(request.getJobType())
                .salaryMin(request.getSalaryMin())
                .salaryMax(request.getSalaryMax())
                .status(request.getStatus())
                .expiryDate(request.getExpiryDate())
                .postedBy(recruiter)
                .build();

        Job saved = jobRepository.save(job);
        return mapToResponse(saved);
    }

    public List<JobResponse> getAllOpenJobs() {
        return jobRepository.findByStatus(JobStatus.OPEN)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<JobResponse> getAllJobs() {
        return jobRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public JobResponse getJobById(Long id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));
        return mapToResponse(job);
    }

    public JobResponse updateJob(Long id, JobRequest request)
    {
        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        if (!job.getPostedBy().getEmail().equals(email))
        {
            throw new RuntimeException("You can only update your own jobs");
        }

        job.setTitle(request.getTitle());
        job.setDescription(request.getDescription());
        job.setLocation(request.getLocation());
        job.setJobType(request.getJobType());
        job.setSalaryMin(request.getSalaryMin());
        job.setSalaryMax(request.getSalaryMax());
        job.setStatus(request.getStatus());
        job.setExpiryDate(request.getExpiryDate());

        return mapToResponse(jobRepository.save(job));
    }

    public void deleteJob(Long id) {
        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        if (!job.getPostedBy().getEmail().equals(email)) {
            throw new RuntimeException("You can only delete your own jobs");
        }

        jobRepository.delete(job);
    }

    public List<JobResponse> getMyJobs() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        User recruiter = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return jobRepository.findByPostedById(recruiter.getId())
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private JobResponse mapToResponse(Job job)
    {
        boolean isExpired = job.getExpiryDate() != null &&
                job.getExpiryDate().isBefore(LocalDate.now());

        return JobResponse.builder()
                .id(job.getId())
                .title(job.getTitle())
                .description(job.getDescription())
                .location(job.getLocation())
                .jobType(job.getJobType())
                .salaryMin(job.getSalaryMin())
                .salaryMax(job.getSalaryMax())
                .status(job.getStatus())
                .expiryDate(job.getExpiryDate())
                .isExpired(isExpired)
                .postedByName(job.getPostedBy().getName())
                .postedByEmail(job.getPostedBy().getEmail())
                .createdAt(job.getCreatedAt())
                .build();
    }

    public List<JobResponse> searchJobs(
            String title,
            String location,
            String jobType,
            Double minSalary,
            Double maxSalary) {

        return jobRepository.searchJobs(title, location, jobType, minSalary, maxSalary)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
}