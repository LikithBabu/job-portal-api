package com.likithbabugarlapati.jobportalapi.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data @Builder
public class CompanyResponse {
    private Long id;
    private String name;
    private String description;
    private String website;
    private String location;
    private String industry;
    private String recruiterName;
    private String recruiterEmail;
    private LocalDateTime createdAt;
}