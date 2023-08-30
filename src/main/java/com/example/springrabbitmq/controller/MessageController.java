package com.example.springrabbitmq.controller;

import com.example.springrabbitmq.dto.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {

    private final RabbitTemplate rabbitTemplate;

    @PostMapping
    public void postMessage(@RequestBody Message message) {
        log.info("Received HTTP request message '{}'", message.body());
        rabbitTemplate.convertAndSend("message-fanout", "", message.body());
    }
}
