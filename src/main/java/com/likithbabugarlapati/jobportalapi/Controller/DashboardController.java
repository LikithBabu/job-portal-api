package com.likithbabugarlapati.jobportalapi.Controller;

import com.likithbabugarlapati.jobportalapi.Service.DashboardService;
import com.likithbabugarlapati.jobportalapi.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AdminDashboardResponse> getAdminDashboard() {
        return ResponseEntity.ok(dashboardService.getAdminDashboard());
    }

    @GetMapping("/recruiter")
    @PreAuthorize("hasRole('RECRUITER')")
    public ResponseEntity<RecruiterDashboardResponse> getRecruiterDashboard() {
        return ResponseEntity.ok(dashboardService.getRecruiterDashboard());
    }

    @GetMapping("/applicant")
    @PreAuthorize("hasRole('APPLICANT')")
    public ResponseEntity<ApplicantDashboardResponse> getApplicantDashboard() {
        return ResponseEntity.ok(dashboardService.getApplicantDashboard());
    }
}