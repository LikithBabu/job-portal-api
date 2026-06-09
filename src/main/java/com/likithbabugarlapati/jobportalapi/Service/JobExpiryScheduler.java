package com.likithbabugarlapati.jobportalapi.Service;

import com.likithbabugarlapati.jobportalapi.Model.Job;
import com.likithbabugarlapati.jobportalapi.Repository.JobRepository;
import com.likithbabugarlapati.jobportalapi.enums.JobStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JobExpiryScheduler {

    private final JobRepository jobRepository;

    // runs every day at midnight
    @Scheduled(cron = "0 0 0 * * *")
    public void closeExpiredJobs() {
        List<Job> openJobs = jobRepository.findByStatus(JobStatus.OPEN);

        int count = 0;
        for (Job job : openJobs) {
            if (job.getExpiryDate() != null &&
                    job.getExpiryDate().isBefore(LocalDate.now())) {
                job.setStatus(JobStatus.CLOSED);
                jobRepository.save(job);
                count++;
            }
        }

        if (count > 0) {
            System.out.println("Auto-closed " + count + " expired jobs");
        }
    }
}