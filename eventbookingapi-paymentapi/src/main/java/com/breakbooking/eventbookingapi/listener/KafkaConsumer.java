package com.breakbooking.eventbookingapi.listener;


import com.breakbooking.eventbookingapi.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class KafkaConsumer {

    @Autowired
    PaymentService paymentService;
/*
    @KafkaListener(topics = "P2", groupId= "group_payment")
//            ,containerFactory = "paymentKafkaListenerFactory")
    public void cosumeJson(Payment payment){
        log.info("Consumed Json message "+payment);
        String token =  paymentService.getToken();
        log.info("Token is "+ token);
//        payment.setNonce("fake-valid-visa-nonce");
//        payment.setNonce("fake-valid-mastercard-nonce");
        String restult=paymentService.makePayment("fake-valid-nonce",payment.getBookingId(),payment.getAmount().toString());
      if (restult.equals("fail")){
          log.info("Payment failure");
      }else if(restult.equals("success")){
          log.info("Payment successful");
      }else {
          log.info("Something went wrong!");
      }
    }*/
/*
    @KafkaListener(topics = "${kafka.reuest.topic}", groupId = "${kafka.group.id}")
    @SendTo
    public Result handle(Payment payment) {
        log.info("Consumed Json message " + payment);
        String token = paymentService.getToken();
        log.info("Token is " + token);
//        payment.setNonce("fake-valid-visa-nonce");
//        payment.setNonce("fake-valid-mastercard-nonce");
        String restult = paymentService.makePayment("fake-valid-nonce", payment.getBookingId(), payment.getAmount().toString());
        if (restult.equals("fail")) {
            log.info("Payment failure");
        } else if (restult.equals("success")) {
            log.info("Payment successful");
        } else {
            log.info("Something went wrong!");
        }


        Result result = new Result();
        return result;
    }

*/



}
