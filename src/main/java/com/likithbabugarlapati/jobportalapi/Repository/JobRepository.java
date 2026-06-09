package com.likithbabugarlapati.jobportalapi.Repository;

import com.likithbabugarlapati.jobportalapi.Model.Job;
import com.likithbabugarlapati.jobportalapi.enums.JobStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.beans.JavaBean;
import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long>
{
    List<Job> findByStatus(JobStatus status);
    List<Job> findByPostedById(Long recruiterId);
    long countByStatus(JobStatus status);
    long countByPostedById(Long recruiterId);
    long countByPostedByIdAndStatus(Long recruiterId, JobStatus status);

    @Query("""
        SELECT j FROM Job j
        WHERE j.status = 'OPEN'
        AND (:title IS NULL OR LOWER(j.title) LIKE LOWER(CONCAT('%', :title, '%')))
        AND (:location IS NULL OR LOWER(j.location) LIKE LOWER(CONCAT('%', :location, '%')))
        AND (:jobType IS NULL OR LOWER(j.jobType) LIKE LOWER(CONCAT('%', :jobType, '%')))
        AND (:minSalary IS NULL OR j.salaryMin >= :minSalary)
        AND (:maxSalary IS NULL OR j.salaryMax <= :maxSalary)
        ORDER BY j.createdAt DESC
    """)
    List<Job> searchJobs(
            @Param("title") String title,
            @Param("location") String location,
            @Param("jobType") String jobType,
            @Param("minSalary") Double minSalary,
            @Param("maxSalary") Double maxSalary
    );

}
