package com.likithbabugarlapati.jobportalapi.Controller;

import com.likithbabugarlapati.jobportalapi.Service.ApplicationService;
import com.likithbabugarlapati.jobportalapi.dto.ApplicationResponse;
import com.likithbabugarlapati.jobportalapi.dto.StatusUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping("/apply/{jobId}")
    @PreAuthorize("hasRole('APPLICANT')")
    public ResponseEntity<ApplicationResponse> applyToJob(
            @PathVariable Long jobId,
            @RequestParam(value = "resume", required = false) MultipartFile resume) {
        return ResponseEntity.ok(applicationService.applyToJob(jobId, resume));
    }

    @GetMapping("/my-applications")
    @PreAuthorize("hasRole('APPLICANT')")
    public ResponseEntity<List<ApplicationResponse>> getMyApplications() {
        return ResponseEntity.ok(applicationService.getMyApplications());
    }

    @GetMapping("/job/{jobId}")
    @PreAuthorize("hasRole('RECRUITER') or hasRole('ADMIN')")
    public ResponseEntity<List<ApplicationResponse>> getApplicationsForJob(
            @PathVariable Long jobId) {
        return ResponseEntity.ok(applicationService.getApplicationsForJob(jobId));
    }

    @PatchMapping("/{applicationId}/status")
    @PreAuthorize("hasRole('RECRUITER') or hasRole('ADMIN')")
    public ResponseEntity<ApplicationResponse> updateStatus(
            @PathVariable Long applicationId,
            @Valid @RequestBody StatusUpdateRequest request) {
        return ResponseEntity.ok(applicationService.updateStatus(applicationId, request));
    }
}