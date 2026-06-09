package com.likithbabugarlapati.jobportalapi.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ApplicationRequest
{

    @NotNull
    private Long jobId;
}
