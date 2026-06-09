package com.likithbabugarlapati.jobportalapi.Controller;

import com.likithbabugarlapati.jobportalapi.Service.SavedJobService;
import com.likithbabugarlapati.jobportalapi.dto.SavedJobResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/saved-jobs")
@RequiredArgsConstructor
public class SavedJobController {

    private final SavedJobService savedJobService;

    @PostMapping("/{jobId}")
    @PreAuthorize("hasRole('APPLICANT')")
    public ResponseEntity<String> toggleSaveJob(@PathVariable Long jobId) {
        return ResponseEntity.ok(savedJobService.toggleSaveJob(jobId));
    }

    @GetMapping
    @PreAuthorize("hasRole('APPLICANT')")
    public ResponseEntity<List<SavedJobResponse>> getMySavedJobs() {
        return ResponseEntity.ok(savedJobService.getMySavedJobs());
    }

    @GetMapping("/{jobId}/is-saved")
    @PreAuthorize("hasRole('APPLICANT')")
    public ResponseEntity<Boolean> isJobSaved(@PathVariable Long jobId) {
        return ResponseEntity.ok(savedJobService.isJobSaved(jobId));
    }
}