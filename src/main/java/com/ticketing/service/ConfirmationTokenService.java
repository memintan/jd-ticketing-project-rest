package com.ticketing.service;

import com.ticketing.entity.ConfirmationToken;
import com.ticketing.exception.TicketingProjectException;
import org.springframework.mail.SimpleMailMessage;

public interface ConfirmationTokenService {

    ConfirmationToken save(ConfirmationToken confirmationToken);

    void sendEmail(SimpleMailMessage email);

    ConfirmationToken readByToken(String token) throws TicketingProjectException;

    void delete(ConfirmationToken confirmationToken);
}