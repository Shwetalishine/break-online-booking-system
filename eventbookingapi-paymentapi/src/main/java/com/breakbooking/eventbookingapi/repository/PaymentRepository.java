package com.breakbooking.eventbookingapi.repository;


import com.breakbooking.eventbookingapi.model.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends MongoRepository<Payment,String> {
    Payment findByBookingId(String bookingId);
}
