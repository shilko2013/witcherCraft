package com.shilko.ru.witcher.serviceimpl;

import com.shilko.ru.witcher.service.NotificationMailService;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class NotificationMailServiceImpl implements NotificationMailService {

    private static final String USERNAME = "project.witcher.craft@gmail.com";
    private static final String PASSWORD = "iaq15000";

    @Override
    public boolean sendMessage(String messages, String email) {

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "25");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(USERNAME, PASSWORD);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(USERNAME));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email));
            message.setSubject("Wither craft project");
            message.setText(messages);

            Transport.send(message);

            return true;

        } catch (MessagingException e) {
            return false;
        }
    }
}
