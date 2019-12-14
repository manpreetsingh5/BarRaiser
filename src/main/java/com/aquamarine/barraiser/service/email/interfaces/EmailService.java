package com.aquamarine.barraiser.service.email.interfaces;

import com.aquamarine.barraiser.model.User;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public interface EmailService {
    MimeMessage constructResetTokenEmail(String token, User user) throws MessagingException;
    MimeMessage constructEmail(String subject, String body, User user) throws MessagingException;
    void sendEmail(MimeMessage email);
    void createPasswordResetTokenForUser(User user, String token);
}
