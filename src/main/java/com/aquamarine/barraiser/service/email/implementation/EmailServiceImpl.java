package com.aquamarine.barraiser.service.email.implementation;

import com.aquamarine.barraiser.model.PasswordResetToken;
import com.aquamarine.barraiser.model.User;
import com.aquamarine.barraiser.repository.PasswordTokenRepository;
import com.aquamarine.barraiser.service.email.interfaces.EmailService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.Date;


@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    PasswordTokenRepository passwordTokenRepository;

    @Value("http://localhost:5000")
    private String contextPath;


    @Override
    public MimeMessage constructResetTokenEmail(String token, User user) throws MessagingException {
        String url = contextPath + "/api/auth/validateToken?id=" +
                user.getId() + "&token=" + token;
        String message = "Password Reset Request";
//        url = "google.com";
        return constructEmail("BarRaiser Reset Password", message + " \r\n <a href=" + url + ">Reset Password</a>", user);
    }

    @Override
    public MimeMessage constructEmail(String subject, String body, User user) throws MessagingException {
        MimeMessage email = javaMailSender.createMimeMessage();
        email.setSubject(subject);
        email.setText(body, "UTF-8", "html");
        email.setRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
        return email;
    }

    @Override
    public void sendEmail(MimeMessage email) {
        javaMailSender.send(email);
    }

    @Override
    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken myToken = new PasswordResetToken(token, user);
        System.out.println(PasswordResetToken.EXPIRATION);
        System.out.println(new Date(System.currentTimeMillis() + PasswordResetToken.EXPIRATION * 2));
        myToken.setExpiryDate(new Date(System.currentTimeMillis() + PasswordResetToken.EXPIRATION * 2));
        passwordTokenRepository.save(myToken);
    }
}
