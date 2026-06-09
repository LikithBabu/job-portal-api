package com.likithbabugarlapati.jobportalapi.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendApplicationStatusEmail(
            String toEmail,
            String applicantName,
            String jobTitle,
            String status) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Application Update: " + jobTitle);
        message.setText(buildEmailBody(applicantName, jobTitle, status));

        mailSender.send(message);
    }

    private String buildEmailBody(String name, String jobTitle, String status) {
        String statusMessage;

        switch (status) {
            case "ACCEPTED" -> statusMessage =
                    "Congratulations! Your application for '" + jobTitle +
                            "' has been ACCEPTED. The recruiter will contact you shortly.";
            case "REJECTED" -> statusMessage =
                    "Thank you for applying. Unfortunately, your application for '" +
                            jobTitle + "' has not moved forward at this time.";
            case "REVIEWED" -> statusMessage =
                    "Good news! Your application for '" + jobTitle +
                            "' is currently being REVIEWED by the recruiter.";
            default -> statusMessage =
                    "Your application status for '" + jobTitle +
                            "' has been updated to: " + status;
        }

        return "Dear " + name + ",\n\n" +
                statusMessage + "\n\n" +
                "You can log in to the Job Portal to view more details.\n\n" +
                "Best regards,\n" +
                "Job Portal Team";
    }
}