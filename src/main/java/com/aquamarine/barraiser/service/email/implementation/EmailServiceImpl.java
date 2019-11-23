package com.aquamarine.barraiser.service.email.implementation;

import com.aquamarine.barraiser.model.User;
import com.aquamarine.barraiser.service.email.interfaces.EmailService;
import org.springframework.mail.SimpleMailMessage;

import java.util.Locale;

public class EmailServiceImpl implements EmailService {


    @Override
    public SimpleMailMessage constructResetTokenEmail(String contextPath, Locale locale, String token, User user) {
        return null;
    }

    @Override
    public SimpleMailMessage constructEmail(String subject, String body, User user) {
        return null;
    }

    @Override
    public void sendEmail() {

    }

    @Override
    public void createPasswordResetTokenForUser(User user, String token) {

    }
}
