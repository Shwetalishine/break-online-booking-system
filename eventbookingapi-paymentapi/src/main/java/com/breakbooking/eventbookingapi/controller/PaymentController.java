package com.breakbooking.eventbookingapi.controller;


import com.braintreegateway.Transaction;
import com.breakbooking.eventbookingapi.model.Payment;
import com.breakbooking.eventbookingapi.service.PaymentService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@EnableKafka
//@RestController
@Controller
@RequestMapping("/api/v1/payment")
@Slf4j
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost", "http://localhost:8080",
        "http://192.168.99.101", "http://ec2-3-104-154-174.ap-southeast-2.compute.amazonaws.com",
        "http://break-booking.online", "https://break-booking.online",
        "http://www.break-booking.online", "https://www.break-booking.online"})
public class PaymentController {

    PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    private final Transaction.Status[] TRANSACTION_SUCCESS_STATUSES = new Transaction.Status[]{
            Transaction.Status.AUTHORIZED,
            Transaction.Status.AUTHORIZING,
            Transaction.Status.SETTLED,
            Transaction.Status.SETTLEMENT_CONFIRMED,
            Transaction.Status.SETTLEMENT_PENDING,
            Transaction.Status.SETTLING,
            Transaction.Status.SUBMITTED_FOR_SETTLEMENT
    };



    @GetMapping("/")
    public String root() {
        return "redirect:token";
    }
    @GetMapping("/token")
    @ApiOperation(value = "Generate token")
    public String getToken(Model model) {
        log.info("Calling getToken()");
        String clientToken = paymentService.getToken();
        log.info(clientToken);
        model.addAttribute("clientToken", clientToken);
        return "new";

    }


    @KafkaListener(topics = "Payment", groupId = "group_payment")
//            containerFactory = "paymentKafkaListenerFactory")
    @PostMapping("/checkout")
    @ApiOperation(value = "for payment")
    public String processPayment(@RequestParam("payment_method_nonce") String nonce,
                                 @RequestParam(value = "amount", defaultValue = "0") String amount,
                                 @RequestParam(value = "bookingId", defaultValue = "testId") String bookingId) {
//        log.info("Consumed Json message "+payment);

        Payment payment = paymentService.makePayment(nonce, bookingId, amount);
        System.out.println("result is " + payment);
        if (payment.getPaymentId() == null)
            return "failure";
        return "success";
    }

//    @KafkaListener(topics = "${kafka.request.topic}", groupId = "${kafka.group.id}")
//    @SendTo
////    @PostMapping("/checkouts")
//    @ApiOperation(value = "for payment")
//    public Payment handle( PaymentRequest paymentRequest) {
//        System.out.println("Calculating Result...");
//        System.out.println("I am here");
//        Payment payment=paymentService.makePayment(paymentRequest.getNonce(),paymentRequest.getBookingId(),paymentRequest.getEventPrice().toString());
//        System.out.println("Done");
//        return payment;
//    }

/*
    @Value("${kafka.request.topic}")
    private String requestTopic;
    @Autowired
    private ReplyingKafkaTemplate<String, Payment, Result> replyingKafkaTemplate;

    @PostMapping("/get-result")
    public ResponseEntity<Result> getObject(@RequestParam("payment_method_nonce") String nonce,
                                            @RequestParam(value = "bookingId", defaultValue = "testId") String bookingId,
                                            @RequestParam(value = "amount", defaultValue = "3") String amount)
            throws InterruptedException, ExecutionException {
        Payment payment = new Payment();
        ProducerRecord<String, Payment> record = new ProducerRecord<String, Payment>(requestTopic, null, payment.getTransactionId(), payment);
        RequestReplyFuture<String, Payment, Result> future = replyingKafkaTemplate.sendAndReceive(record);
        ConsumerRecord<String, Result> response = future.get();
        return new ResponseEntity<>(response.value(), HttpStatus.OK);
    }

*/

    //Return Transaction object to front-end
    //Code for Rest Controller
    /*
    @GetMapping(value = "/checkouts/{transactionId}")
    public Transaction getTransaction(@PathVariable String transactionId) {
        Transaction transaction = paymentService.getTransaction(transactionId);
        return transaction;
    }
    */


    @GetMapping(value = "/checkout/{transactionId}")
    public String getTransaction(@PathVariable String transactionId, Model model) {
        Transaction transaction = paymentService.getTransaction(transactionId);
        model.addAttribute("isSuccess", Arrays.asList(TRANSACTION_SUCCESS_STATUSES).contains(transaction.getStatus()));
        model.addAttribute("transaction", transaction);
        model.addAttribute("creditCard", transaction.getCreditCard());
        model.addAttribute("customer", transaction.getCustomer());
        return "show";

    }
    @GetMapping(value = "/transactions/list")
    public ResponseEntity<List<Payment>> getAllTransactions() {
        return new ResponseEntity(paymentService.getAllTransactions(), HttpStatus.OK);
    }
}
