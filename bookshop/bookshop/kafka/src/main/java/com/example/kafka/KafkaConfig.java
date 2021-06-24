package com.example.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfig {
    @Value("${kafka.broker-list}")
    private String brokers;
    @Value("${kafka.producer.retries}")
    private Integer producer_retries;
    @Value("${kafka.producer.batch-size}")
    private Integer producer_batch_size;
    @Value("${kafka.producer.linger-ms}")
    private Integer producer_linger_ms;
    @Value("${kafka.producer.buffer-memory}")
    private Integer producer_buffer_memory;
    @Value("${kafka.producer.key-serializer}")
    private String producer_key_serializer;
    @Value("${kafka.producer.value-serializer}")
    private String producer_value_serializer;

    @Value("${kafka.consumer.topic}")
    private String consumer_topic;
    @Value("${kafka.consumer.gourp-id}")
    private String consumer_gourp_id;
    @Value("${kafka.consumer.enable-auto-commit}")
    private boolean consumer_enable_auto_commit;
    @Value("${kafka.consumer.auto-commit-ms}")
    private String consumer_auto_commit_ms;
    @Value("${kafka.consumer.session-timeout-ms}")
    private String consumer_session_timeout_ms;
    @Value("${kafka.consumer.key-deserializer}")
    private String consumer_key_deserializer;
    @Value("${kafka.consumer.value-deserializer}")
    private String consumer_value_deserializer;

    @Bean
    ConcurrentKafkaListenerContainerFactory<Integer, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<Integer, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<Integer, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    /**
     * 消费者参数配置
     *
     * @return
     */
    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, consumer_gourp_id);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, consumer_enable_auto_commit);
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, consumer_auto_commit_ms);
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, consumer_session_timeout_ms);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, consumer_key_deserializer);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, consumer_value_deserializer);
        return props;
    }

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    /**
     * 生产者参数配置
     *
     * @return
     */
    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers);
        props.put(ProducerConfig.RETRIES_CONFIG, producer_retries);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, producer_batch_size);
        props.put(ProducerConfig.LINGER_MS_CONFIG, producer_linger_ms);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, producer_buffer_memory);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, producer_key_serializer);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, producer_value_serializer);
        return props;
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        System.out.println("init");
        return new KafkaTemplate<String, String>(producerFactory());
    }
}