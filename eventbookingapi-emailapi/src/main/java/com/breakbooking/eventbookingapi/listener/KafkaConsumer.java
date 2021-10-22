package com.breakbooking.eventbookingapi.listener;


import com.breakbooking.eventbookingapi.common.BookingRequest;
import com.breakbooking.eventbookingapi.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaConsumer {

    @Autowired
    EmailService emailService;

    @KafkaListener(topics = "email", groupId = "email-group")
//    containerFactory = "bookingKafkaListenerFactory")
    public void cosumeJson(BookingRequest newBookingRequest) {
        log.info("Consumed Json message " + newBookingRequest);
        if (newBookingRequest.getBookingId() != null) {
            try {
                emailService.sendBookingNotificationEmail(newBookingRequest.getBookerEmail(),
                        "<body><div>Dear <strong>" + newBookingRequest.getBookerName() + ",</strong><br>" + "Thank you for choosing Break Booking!</div><br>" +
                                "<div style='background-color:#F2F2F2;'>" +
                                "<div><strong><span style='font-size:18p;color:#236fa1;border-bottom: 3px #e6a501 solid;'>EVENT DETAILS<span><strong></div>" +
                                "<p><strong>Attendee Name: </strong><span>" + newBookingRequest.getBookerName() + "</span></p>" +
                                "<p><strong>Attendee ID: </strong><span>" + newBookingRequest.getUserId() + "</span><p>" +
                                "<p><strong>Booking ID:  </strong><span>" + newBookingRequest.getBookingId() + "</span><p>" +
                                "<p><strong>Event Name: </strong><span>" + newBookingRequest.getEventTitle() + "</span><p>" +
                                "<p><strong>Amount Paid: </strong><span>" + newBookingRequest.getEventPrice() + "</span><p>" +
                                "<p><strong>Transaction ID: </strong><span>" + newBookingRequest.getTransactionId() + "</span><p>" +
                                "</div><br>" +
                                "<div> <p>Please, consider this as an acceptance email. If you have any questions contact us with this link eventsname@yourdomain.com</p>" +
                                "<p>We look forward to seeing you at the event.</p></div></body>" +
                                "<br><br><br>" + "Regards,<br>" +
                                "Break booking Team</body>",
                        "Break booking");
            } catch (MailException e) {
                log.error("Could not send mail", e);
            }
        }


    }

    @KafkaListener(topics = "reminderEmail", groupId = "reminder-email-group")
    public void cosumeReminder(BookingRequest newBookingRequest) {
        log.info("Consumed Json message " + newBookingRequest);
        if (newBookingRequest.getBookingId() != null) {
            try {
                emailService.sendBookingNotificationEmail(newBookingRequest.getBookerEmail(),
                        "<body><div>Dear <strong>" + newBookingRequest.getBookerName() + ",</strong><br>" + "This is a friendly reminder from Break booking about your appointment.</div><br>" +
                                "<p>We look forward to seeing you at the event.</p></div></body>" +
                                "<br><br><br>" + "Regards,<br>" +
                                "Break booking Team</body>",
                        "Break booking");
            } catch (MailException e) {
                log.error("Could not send mail", e);
            }
        }
    }
}
