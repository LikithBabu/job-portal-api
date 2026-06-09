package com.likithbabugarlapati.jobportalapi.Service;

import com.likithbabugarlapati.jobportalapi.Model.Company;
import com.likithbabugarlapati.jobportalapi.Model.User;
import com.likithbabugarlapati.jobportalapi.Repository.CompanyRepository;
import com.likithbabugarlapati.jobportalapi.Repository.UserRepository;
import com.likithbabugarlapati.jobportalapi.dto.CompanyRequest;
import com.likithbabugarlapati.jobportalapi.dto.CompanyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

    public CompanyResponse createCompany(CompanyRequest request) {
        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        User recruiter = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (companyRepository.existsByRecruiterId(recruiter.getId())) {
            throw new RuntimeException("You already have a company profile");
        }

        if (companyRepository.existsByName(request.getName())) {
            throw new RuntimeException("Company name already exists");
        }

        Company company = Company.builder()
                .name(request.getName())
                .description(request.getDescription())
                .website(request.getWebsite())
                .location(request.getLocation())
                .industry(request.getIndustry())
                .recruiter(recruiter)
                .build();

        return mapToResponse(companyRepository.save(company));
    }

    public CompanyResponse getMyCompany() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        User recruiter = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Company company = companyRepository.findByRecruiterId(recruiter.getId())
                .orElseThrow(() -> new RuntimeException("Company profile not found"));

        return mapToResponse(company);
    }

    public CompanyResponse updateCompany(CompanyRequest request) {
        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        User recruiter = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Company company = companyRepository.findByRecruiterId(recruiter.getId())
                .orElseThrow(() -> new RuntimeException("Company profile not found"));

        company.setName(request.getName());
        company.setDescription(request.getDescription());
        company.setWebsite(request.getWebsite());
        company.setLocation(request.getLocation());
        company.setIndustry(request.getIndustry());

        return mapToResponse(companyRepository.save(company));
    }

    public List<CompanyResponse> getAllCompanies() {
        return companyRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public CompanyResponse getCompanyById(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found"));
        return mapToResponse(company);
    }

    private CompanyResponse mapToResponse(Company company) {
        return CompanyResponse.builder()
                .id(company.getId())
                .name(company.getName())
                .description(company.getDescription())
                .website(company.getWebsite())
                .location(company.getLocation())
                .industry(company.getIndustry())
                .recruiterName(company.getRecruiter().getName())
                .recruiterEmail(company.getRecruiter().getEmail())
                .createdAt(company.getCreatedAt())
                .build();
    }
}