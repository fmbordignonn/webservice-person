package com.teste.southsystem.person.rabbitmq.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Value("${config.rabbit.account.queue}")
    public String accountQueue;

    @Value("${config.rabbit.account.exchange}")
    public String accountExchange;

    @Bean
    public Queue pushQueue() { return QueueBuilder.durable(accountQueue).build(); }

    @Bean
    public DirectExchange exchange() { return ExchangeBuilder.directExchange(accountExchange).build(); }

    @Bean
    public Binding binding(){
        return BindingBuilder.bind(this.pushQueue()).to(this.exchange()).with(this.accountQueue);
    }
}
