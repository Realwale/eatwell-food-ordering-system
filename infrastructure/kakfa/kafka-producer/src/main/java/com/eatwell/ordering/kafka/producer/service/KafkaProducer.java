package com.eatwell.ordering.kafka.producer.service;

import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.kafka.support.SendResult;

import java.io.Serializable;
import java.util.concurrent.CompletableFuture;

public interface KafkaProducer <K extends Serializable, V extends SpecificRecordBase>{

    /**
     * Sends a message to a specified Kafka topic.
     *
     * @param topicName The name of the topic to send the message to
     * @param key The key of the message
     * @param message The message to send
     * @return CompletableFuture<SendResult<K, V>> A future that completes when the send operation is done
     * @throws IllegalArgumentException if topicName is null or empty
     */
    CompletableFuture<SendResult<K, V>> send(String topicName, K key, V message);
}
