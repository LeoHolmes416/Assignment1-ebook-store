package com.example.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 实现命令行接口，从命令行读取参数发送
 *
 * @author Administrator
 */
@SpringBootApplication
@Slf4j
public class KafkaAppTwo implements CommandLineRunner {
    // kafka模板
    @Autowired
    private KafkaTemplate<String, String> template;
    // 计数器-等待3个消息接收完成
    private final CountDownLatch latch = new CountDownLatch(3);

    @Override
    public void run(String... args) throws Exception {
        System.out.println("发送信息...");
        this.template.send("test-topic", "foo1");
        this.template.send("test-topic", "foo2");
        this.template.send("test-topic", "foo3");

        // 等待60秒接收完成退出
        latch.await(60, TimeUnit.SECONDS);
        log.info("接收完成");
    }

    /**
     * kafka消费
     *
     * @param cr
     * @throws Exception
     */
    @KafkaListener(topics = "test-topic")
    public void listen(ConsumerRecord<String, String> cr) throws Exception {
        log.info("我是消费者:{}:{}", cr.key(), cr.value());
        //latch.countDown();
    }

    public static void main(String[] args) {
        SpringApplication.run(KafkaAppTwo.class, args).close();
    }
}