package com.example.springrabbitmq.listener;

import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class MessageListener {

    @RabbitListener(queues = "spring-queue")
    public void printMessage (String message){
        log.info("Read message from RabbitMQ: '{}'", message);
    }
}
