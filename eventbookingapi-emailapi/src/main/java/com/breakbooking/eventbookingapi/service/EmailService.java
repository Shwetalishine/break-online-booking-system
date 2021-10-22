package com.breakbooking.eventbookingapi.service;

public interface EmailService {

    void sendBookingNotificationEmail(String toEmail, String body, String subject);

    void sendBookingNotificationEmailWithAttachment(String toEmail, String body, String subject, String attachment);


}
