package com.likithbabugarlapati.jobportalapi.Controller;

import com.likithbabugarlapati.jobportalapi.Service.JobService;
import com.likithbabugarlapati.jobportalapi.dto.JobRequest;
import com.likithbabugarlapati.jobportalapi.dto.JobResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    @PostMapping
    @PreAuthorize("hasRole('RECRUITER') or hasRole('ADMIN')")
    public ResponseEntity<JobResponse> createJob(@Valid @RequestBody JobRequest request) {
        return ResponseEntity.ok(jobService.createJob(request));
    }

    @GetMapping
    @PreAuthorize("hasRole('APPLICANT') or hasRole('RECRUITER') or hasRole('ADMIN')")
    public ResponseEntity<List<JobResponse>> getAllOpenJobs() {
        return ResponseEntity.ok(jobService.getAllOpenJobs());
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<JobResponse>> getAllJobs() {
        return ResponseEntity.ok(jobService.getAllJobs());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('APPLICANT') or hasRole('RECRUITER') or hasRole('ADMIN')")
    public ResponseEntity<JobResponse> getJobById(@PathVariable Long id) {
        return ResponseEntity.ok(jobService.getJobById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('RECRUITER') or hasRole('ADMIN')")
    public ResponseEntity<JobResponse> updateJob(@PathVariable Long id,
                                                 @Valid @RequestBody JobRequest request) {
        return ResponseEntity.ok(jobService.updateJob(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('RECRUITER') or hasRole('ADMIN')")
    public ResponseEntity<String> deleteJob(@PathVariable Long id) {
        jobService.deleteJob(id);
        return ResponseEntity.ok("Job deleted successfully");
    }

    @GetMapping("/my-jobs")
    @PreAuthorize("hasRole('RECRUITER')")
    public ResponseEntity<List<JobResponse>> getMyJobs() {
        return ResponseEntity.ok(jobService.getMyJobs());
    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('APPLICANT') or hasRole('RECRUITER') or hasRole('ADMIN')")
    public ResponseEntity<List<JobResponse>> searchJobs(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String jobType,
            @RequestParam(required = false) Double minSalary,
            @RequestParam(required = false) Double maxSalary) {

        return ResponseEntity.ok(jobService.searchJobs(title, location, jobType, minSalary, maxSalary));
    }

}