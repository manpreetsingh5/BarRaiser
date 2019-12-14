package com.aquamarine.barraiser.service.email.implementation;

import com.aquamarine.barraiser.dto.model.UserDTO;
import com.aquamarine.barraiser.model.PasswordResetToken;
import com.aquamarine.barraiser.model.User;
import com.aquamarine.barraiser.repository.PasswordTokenRepository;
import com.aquamarine.barraiser.service.email.interfaces.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    PasswordTokenRepository passwordTokenRepository;

    @Value("localhost:5000")
    private String contextPath;


    @Override
    public SimpleMailMessage constructResetTokenEmail(String token, User user) {
        String url = contextPath + "/api/user/resetPassword?id=" +
                user.getId() + "&token=" + token;
        String message = "Password Reset Request";
        return constructEmail("BarRaiser Reset Password", message + " \r\n" + url, user);
    }

    @Override
    public SimpleMailMessage constructEmail(String subject, String body, User user) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(user.getEmail());
        return email;
    }

    @Override
    public void sendEmail(SimpleMailMessage email) {
        javaMailSender.send(email);
    }

    @Override
    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordTokenRepository.save(myToken);
    }
}
