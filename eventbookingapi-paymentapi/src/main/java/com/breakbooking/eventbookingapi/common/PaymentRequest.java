package com.breakbooking.eventbookingapi.common;

import com.breakbooking.eventbookingapi.model.Booking;
import com.breakbooking.eventbookingapi.model.Payment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {

    private Booking booking;
    private Payment payment;
//    private String  eventTitle;
//    private BigDecimal eventPrice;
//    private String transactionId;
//    private String nonce;

}
