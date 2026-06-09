package com.likithbabugarlapati.jobportalapi.Controller;

import com.likithbabugarlapati.jobportalapi.Service.CompanyService;
import com.likithbabugarlapati.jobportalapi.dto.CompanyRequest;
import com.likithbabugarlapati.jobportalapi.dto.CompanyResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping
    @PreAuthorize("hasRole('RECRUITER')")
    public ResponseEntity<CompanyResponse> createCompany(
            @Valid @RequestBody CompanyRequest request) {
        return ResponseEntity.ok(companyService.createCompany(request));
    }

    @GetMapping("/my-company")
    @PreAuthorize("hasRole('RECRUITER')")
    public ResponseEntity<CompanyResponse> getMyCompany() {
        return ResponseEntity.ok(companyService.getMyCompany());
    }

    @PutMapping("/my-company")
    @PreAuthorize("hasRole('RECRUITER')")
    public ResponseEntity<CompanyResponse> updateCompany(
            @Valid @RequestBody CompanyRequest request) {
        return ResponseEntity.ok(companyService.updateCompany(request));
    }

    @GetMapping
    @PreAuthorize("hasRole('APPLICANT') or hasRole('RECRUITER') or hasRole('ADMIN')")
    public ResponseEntity<List<CompanyResponse>> getAllCompanies() {
        return ResponseEntity.ok(companyService.getAllCompanies());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('APPLICANT') or hasRole('RECRUITER') or hasRole('ADMIN')")
    public ResponseEntity<CompanyResponse> getCompanyById(@PathVariable Long id) {
        return ResponseEntity.ok(companyService.getCompanyById(id));
    }
}