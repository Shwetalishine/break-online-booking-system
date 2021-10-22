package com.breakbooking.eventbookingapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Booking {


    private String id;

    private String userId;

    private String bookerName;

    private String bookerEmail;

    private String bookerPhone;

    private String eventEid;

    private PaymentStatus paymentStatus;




}
