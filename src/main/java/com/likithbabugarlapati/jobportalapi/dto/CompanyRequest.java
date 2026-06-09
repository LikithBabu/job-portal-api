package com.likithbabugarlapati.jobportalapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CompanyRequest {

    @NotBlank
    private String name;

    private String description;
    private String website;
    private String location;
    private String industry;
}