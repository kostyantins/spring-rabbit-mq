## To get the MQ service locally use the following steps:

- Start docker compose file (open the terminal and run the following script):

```shell
docker-compose up -d
```

- Open Rabbit MQ service locally:

Link: http://localhost:15672

Username: guest 
Password: guest

## Spring integration:

Add the following dependency:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-amqp</artifactId>
</dependency>
```

## Programing configuration:

Add configuration class that creates all necessary components for the Rabbit usage and use it into the controller level

```java
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
```

Controller:

```java
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
```
