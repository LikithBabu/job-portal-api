package com.likithbabugarlapati.jobportalapi.Repository;

import com.likithbabugarlapati.jobportalapi.Model.Application;
import com.likithbabugarlapati.jobportalapi.enums.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application,Long>
{
    List<Application> findByApplicantId(Long applicantId);
    List<Application> findByJobId(Long jobId);
    Optional<Application> findByJobIdAndApplicantId(Long jobId, Long applicantId);
    boolean existsByJobIdAndApplicantId(Long jobId, Long applicantId);
    long countByStatus(ApplicationStatus status);
    long countByJobIdIn(List<Long> jobIds);
    long countByJobIdInAndStatus(List<Long> jobIds, ApplicationStatus status);
    long countByJobId(Long jobId);
    long countByApplicantId(Long applicantId);
    long countByApplicantIdAndStatus(Long applicantId, ApplicationStatus status);
}
