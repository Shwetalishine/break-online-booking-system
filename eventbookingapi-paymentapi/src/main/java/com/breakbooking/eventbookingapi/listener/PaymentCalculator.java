package com.breakbooking.eventbookingapi.listener;

import com.breakbooking.eventbookingapi.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentCalculator {

    @Autowired
    private PaymentService paymentService;




}
