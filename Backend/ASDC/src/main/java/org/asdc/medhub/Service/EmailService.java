package org.asdc.medhub.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Service class for sending emails.
 */
@Service
public class EmailService {

    /**
     * Java's email sender instance
     */
    private final JavaMailSender javaMailSender;

    /**
     * Constructor for EmailService.
     * @param javaMailSender The JavaMailSender bean injected by Spring.
     */
    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    /**
     * Sends an email.
     * @param to The recipient's email address.
     * @param subject The subject of the email.
     * @param body The body of the email.
     */
    @Async
    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        this.javaMailSender.send(message);
    }
}
