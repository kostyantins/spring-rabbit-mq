package com.example.springrabbitmq.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitConfiguration {

    @Bean
    public Queue localSpringQueue() {
        return new Queue("spring-queue");
    }

    @Bean
    public Exchange messageExchangeFanout() {
        return new FanoutExchange("message-fanout");
    }

    //To be able to link queue with exchange we need to create binding
    @Bean
    public Binding springQueueBinding(){
        return BindingBuilder
                .bind(localSpringQueue())
                .to(messageExchangeFanout())
                //RoutingKey is empty due to the type fanout that will send a message for all
                //In the case of a different type we should use the right key for the exchange to provide a message to the right queue
                .with("")
                .noargs();
    }
}
