package com.likithbabugarlapati.jobportalapi.dto;

import com.likithbabugarlapati.jobportalapi.enums.ApplicationStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StatusUpdateRequest
{

    @NotNull
    private ApplicationStatus status;
}
