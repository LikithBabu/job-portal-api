package com.likithbabugarlapati.jobportalapi.Repository;

import com.likithbabugarlapati.jobportalapi.Model.SavedJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SavedJobRepository extends JpaRepository<SavedJob, Long> {
    List<SavedJob> findByApplicantId(Long applicantId);
    Optional<SavedJob> findByApplicantIdAndJobId(Long applicantId, Long jobId);
    boolean existsByApplicantIdAndJobId(Long applicantId, Long jobId);
    void deleteByApplicantIdAndJobId(Long applicantId, Long jobId);
    long countByApplicantId(Long applicantId);
}