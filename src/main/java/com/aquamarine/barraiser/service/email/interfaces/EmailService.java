package com.aquamarine.barraiser.service.email.interfaces;

import com.aquamarine.barraiser.model.User;
import org.springframework.mail.SimpleMailMessage;

import java.util.Locale;

public interface EmailService {
    SimpleMailMessage constructResetTokenEmail(String contextPath, Locale locale, String token, User user);
    SimpleMailMessage constructEmail(String subject, String body, User user);
    void sendEmail();
    void createPasswordResetTokenForUser(User user, String token);
}
