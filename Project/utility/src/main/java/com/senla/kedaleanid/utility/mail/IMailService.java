package com.senla.kedaleanid.utility.mail;

public interface IMailService {
    void sendEmail(final String senderEmailId, final String receiverEmailId,
                   final String subject, final String message);
}
