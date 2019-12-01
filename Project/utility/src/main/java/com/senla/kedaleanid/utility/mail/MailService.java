package com.senla.kedaleanid.utility.mail;

import com.senla.kedaleanid.utility.exception.MailFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;

@Component("mailService")
public class MailService implements IMailService {

    private final JavaMailSender mailSender;

    @Autowired
    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(final String senderEmailId, final String receiverEmailId,
                          final String subject, final String message) {
        MimeMessagePreparator preparator = mimeMessage -> {
            mimeMessage.setFrom(senderEmailId);
            mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(receiverEmailId));
            mimeMessage.setSubject(subject);
            mimeMessage.setText(message);
        };

        try {
            mailSender.send(preparator);
            System.out.println("Message sent...");
        } catch (Exception e) {
            throw new MailFailedException("An attempt to send message to ["
                    + receiverEmailId + "] from [" + senderEmailId + "] failed!");
        }
    }
}
