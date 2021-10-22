package com.breakbooking.eventbookingapi.service;

import com.braintreegateway.*;
import com.breakbooking.eventbookingapi.model.Payment;
import com.breakbooking.eventbookingapi.common.PaymentRequest;
import com.breakbooking.eventbookingapi.model.PaymentStatus;
import com.breakbooking.eventbookingapi.repository.PaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService {

    private PaymentRepository paymentRepository;


    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    //@Autowired
    // private KafkaTemplate<String,Payment> kafkaTemplatePayment;
    //private static String TEST="Status";

    /* Braintree Payment Transaction Credentials  */


    @Value("${braintree.environment}")
    private String BT_ENVIRONMENT;

    @Value("${braintree.merchant.id}")
    private String BT_MERCHANT_ID;

    @Value("${braintree.public.key}")
    private String BT_PUBLIC_KEY;

    @Value("${braintree.private.key}")
    private String BT_PRIVATE_KEY;

    private static BraintreeGateway gateway = new BraintreeGateway(
            Environment.SANDBOX,
            "bmznvgn95hzt489m",
            "hhvygtzhtqjfcn8b",
            "fdd8cab61b3a4760cc3e3523ec356ad2"
    );

    @Override
    public String getToken() {
        ClientTokenRequest clientTokenRequest = new ClientTokenRequest();

        if(clientTokenRequest.getCustomerId()!=null){
            clientTokenRequest.customerId(clientTokenRequest.getCustomerId());
        }

        // pass clientToken to your front-end
        String clientToken = gateway.clientToken().generate(clientTokenRequest);
        log.info("Token is " + clientToken);
        return clientToken;
    }

    @Override
    public Payment makePayment(String nonce, String bookingId, String amount) {


        BigDecimal chargedAmount = BigDecimal.ZERO;
        log.info("In make payment");

        try {
            chargedAmount = new BigDecimal(amount);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        log.info("Charged Amount is " + chargedAmount);

        log.info("nonce " + nonce);

        String transationId = processPayment(chargedAmount, nonce);

        Payment payment = insertPayment(bookingId, transationId, chargedAmount, nonce);
        if (transationId != null) {
            payment.setPaymentStatus(PaymentStatus.SUCCESS);
            paymentRepository.save(payment);
            //   kafkaTemplatePayment.send(TEST,payment);
            log.info("Payment payload is " + payment);
//            return "success";
            return payment;
        } else {
            payment.setPaymentStatus(PaymentStatus.FAILED);
            paymentRepository.save(payment);
            // kafkaTemplatePayment.send(TEST,payment);
            log.info("Payment payload is " + payment);
//            return "fail";
            return payment;
        }

    }

    @Override
    public Transaction getTransaction(String transactionId) {
        Transaction transaction=null;
        CreditCard creditCard;
        Customer customer;

        try {
            transaction = gateway.transaction().find(transactionId);
            creditCard = transaction.getCreditCard();
            customer = transaction.getCustomer();
        } catch (Exception e) {
            System.out.println("Exception: " + e);
//            return "redirect:/checkouts";
        }return  transaction;

    }

    @Override
    public List<Payment> getAllTransactions() {
        return paymentRepository.findAll();
    }

    private Payment insertPayment(String bookingId, String transationId, BigDecimal amount, String nonce) {

        Payment payment = new Payment();

        payment.setBookingId(bookingId);
        payment.setTransactionId(transationId);
        payment.setPrice(amount);
        payment.setNonce(nonce);

        paymentRepository.save(payment);
        log.info("Payment is " + payment);
        return payment;
    }


    public String processPayment(BigDecimal amount, String nonce) {
        log.info("In process Payment");
        log.info("Nonce is " + nonce);

        TransactionRequest request = new TransactionRequest()
                .amount(amount).paymentMethodNonce(nonce).options().submitForSettlement(true).done();

        log.info("request is " + request);

        //get the response

        Result<Transaction> result = gateway.transaction().sale(request);

        log.info("result is " + result);

        //If its a successful transation go to the transaction page

        if (result.isSuccess()) {
            Transaction transaction = result.getTarget();
            log.info("Payment has been successfully processed");
            log.info("target " + result.getTarget());
            log.info("Success! transaction id is " + transaction.getId());
            log.info("Success! Amount paid is " + amount);
            return transaction.getId();
        } else if (result.getTransaction() != null) {
            Transaction transaction = result.getTransaction();
            log.info("Payment failure!, Please try again later...");
            log.info("Failed! transaction id is " + transaction.getId());
            return null;
        } else {
            //if transaction failed return to the payment page and display all errors

            log.info("Something went wrong");
            return null;
        }
    }

    @KafkaListener(topics = "${kafka.request.topic}", groupId = "${kafka.group.id}")
    @SendTo
    public Payment handle(PaymentRequest paymentRequest) {
        System.out.println("Calculating Result...");
        System.out.println("In listener");
        Payment payment=makePayment("fake-valid-nonce", paymentRequest.getBooking().getId(),paymentRequest.getPayment().getPrice().toString());
        System.out.println("Done");
      return payment;
    }

}
