package com.breakbooking.eventbookingapi.configuration;


import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@Configuration
public class KafkaConfigurationConsumer {

  /*  @Bean
    public ConsumerFactory<String, BookingRequest> bookingConsumerFactory() {
        Map<String, Object> config = new HashMap<>();

        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "group");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        config.put(JsonDeserializer.TRUSTED_PACKAGES, "com.breakbooking.eventbookingapi.model.*");
        config.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, "false");



        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(),
                new ErrorHandlingDeserializer(new JsonDeserializer<>(BookingRequest.class)));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, BookingRequest> bookingKafkaListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, BookingRequest> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(bookingConsumerFactory());
        return factory;
    }
*/
}
