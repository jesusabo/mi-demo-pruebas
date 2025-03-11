package com.casa.productos.app.services.impl;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumerService {


    @KafkaListener(topics = "my-kafka-topic", groupId = "$Default")
    public void listen(ConsumerRecord<String, String> record) {
        System.out.println("📥 Mensaje recibido:");
        System.out.println("  🔹 Offset: " + record.offset());
        System.out.println("  🔹 Partition: " + record.partition());
        System.out.println("  🔹 Key: " + record.key());
        System.out.println("  🔹 Value: " + record.value());
        System.out.println("  🔹 Topic: " + record.topic());
    }
}
