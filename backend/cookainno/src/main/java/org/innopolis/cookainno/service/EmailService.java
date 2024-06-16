package org.innopolis.cookainno.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    public void sendConfirmationEmail(String to, String confirmationCode) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject("Email Confirmation");

        String htmlContent = "<div style='font-family: Arial, sans-serif; color: #333;'>"
                + "<h2 style='text-align: center; color: #4CAF50;'>Email Confirmation</h2>"
                + "<p style='text-align: center;'>Thank you for registering. Please use the following confirmation code to complete your registration:</p>"
                + "<div style='font-size: 24px; font-weight: bold; margin: 20px auto; padding: 10px; width: 150px; text-align: center; background-color: #f9f9f9; border: 2px solid #4CAF50; border-radius: 5px;'>"
                + confirmationCode
                + "</div>"
                + "<p style='text-align: center;'>If you did not register for this service, please ignore this email.</p>"
                + "<p style='text-align: center;'>Best regards,<br/>CookAInno Team</p>"
                + "</div>";
        helper.setText(htmlContent, true);

        mailSender.send(message);
        log.debug("Mail sent successfully to {}", to);
    }
}

