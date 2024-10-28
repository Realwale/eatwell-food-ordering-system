package com.eatwell.ordering.kafka.producer.service;

import com.eatwell.ordering.kafka.producer.exception.KafkaProducerException;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.concurrent.CompletableFuture;


@Slf4j
@Component
public class KafkaProducerImpl<K extends Serializable, V extends SpecificRecordBase> implements KafkaProducer<K, V> {

    private final KafkaTemplate<K, V> kafkaTemplate;

    public KafkaProducerImpl(KafkaTemplate<K, V> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public CompletableFuture<SendResult<K, V>> send(String topicName, K key, V message) {
        if (topicName == null || topicName.trim().isEmpty()) {
            throw new IllegalArgumentException("Topic name cannot be null or empty");
        }

        log.info("Attempting to send message to topic={}, key={}", topicName, key);
        log.debug("Message content: {}", message);

        try {
            ProducerRecord<K, V> producerRecord = new ProducerRecord<>(topicName, key, message);
            return kafkaTemplate.send(producerRecord)
                    .completable()
                    .whenComplete((result, error) -> {
                        if (error != null) {
                            logError(topicName, key, message, error);
                        } else {
                            logSuccess(topicName, key, result);
                        }
                    });

        } catch (KafkaException e) {
            handleKafkaException(topicName, key, message, e);
            return CompletableFuture.failedFuture(e);
        } catch (Exception e) {
            handleUnexpectedException(topicName, key, message, e);
            return CompletableFuture.failedFuture(e);
        }
    }

    private void logSuccess(String topicName, K key, SendResult<K, V> result) {
        RecordMetadata metadata = result.getRecordMetadata();
        log.info("Message successfully sent - Topic: {}, Key: {}, Partition: {}, Offset: {}, Timestamp: {}",
                topicName,
                key,
                metadata.partition(),
                metadata.offset(),
                metadata.timestamp());
    }

    private void logError(String topicName, K key, V message, Throwable ex) {
        log.error("Failed to send message - Topic: {}, Key: {}, Error: {}",
                topicName,
                key,
                ex.getMessage());
    }

    private void handleKafkaException(String topicName, K key, V message, KafkaException e) {
        String errorMessage = String.format("Kafka producer error - Topic: %s, Key: %s", topicName, key);
        log.error(errorMessage, e);

        Throwable cause = e.getCause();
        if (cause instanceof org.apache.kafka.common.errors.RetriableException) {
            log.warn("Retriable error occurred. The operation might succeed if retried");
        } else if (cause instanceof org.apache.kafka.common.errors.ProducerFencedException) {
            log.error("Producer is fenced. Need to recreate the producer");
        } else if (cause instanceof org.apache.kafka.common.errors.SerializationException) {
            log.error("Failed to serialize message");
        }
        throw new KafkaProducerException(errorMessage, e);
    }

    private void handleUnexpectedException(String topicName, K key, V message, Exception e) {
        String errorMessage = String.format("Unexpected error in Kafka producer - Topic: %s, Key: %s", topicName, key);
        log.error(errorMessage, e);
        throw new KafkaProducerException(errorMessage, e);
    }

    @PreDestroy
    public void close() {
        try {
            log.info("Shutting down Kafka producer");
            if (kafkaTemplate != null) {
                kafkaTemplate.flush();
                kafkaTemplate.destroy();
                log.info("Kafka producer shut down successfully");
            }
        } catch (Exception e) {
            log.error("Error during Kafka producer shutdown", e);
        }
    }
}

