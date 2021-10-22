package com.breakbooking.eventbookingapi.service;

import com.braintreegateway.Transaction;
import com.breakbooking.eventbookingapi.model.Payment;

import java.util.List;

public interface PaymentService {

    String getToken();
    Payment makePayment(String nonce, String bookingId, String amount);
    Transaction getTransaction(String transactionId);


    List<Payment> getAllTransactions();
}
