package com.teste.southsystem.person.service;

import com.google.gson.Gson;
import com.teste.southsystem.person.model.DTO.PersonDTO;
import com.teste.southsystem.person.rabbitmq.config.RabbitMQConfig;
import org.slf4j.Logger;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class RabbitMQServiceImpl implements RabbitMQService {
    private static final Logger LOG = getLogger(RabbitMQServiceImpl.class);

    private final RabbitTemplate rabbitTemplate;
    private final AmqpAdmin amqpAdmin;
    private RabbitMQConfig rabbitConfig;

    @Autowired
    public RabbitMQServiceImpl(RabbitTemplate rabbitTemplate, AmqpAdmin amqpAdmin, RabbitMQConfig rabbitConfig) {
        this.rabbitTemplate = rabbitTemplate;
        this.amqpAdmin = amqpAdmin;
        this.rabbitConfig = rabbitConfig;

        this.rabbitTemplate.setRoutingKey(this.rabbitConfig.accountQueue);
    }

    @PostConstruct
    public void queueSetup() {
        amqpAdmin.declareQueue(rabbitConfig.pushQueue());
        amqpAdmin.declareExchange(rabbitConfig.exchange());
        amqpAdmin.declareBinding(rabbitConfig.binding());
    }

    @Override
    public void sendToAccountQueue(PersonDTO personDto) {
        try {
            LOG.info("Sending person information through RabbitMQ");
            rabbitTemplate.convertAndSend(new Gson().toJson(personDto));
            LOG.info("Message sent");
        } catch (AmqpException ex) {
            LOG.error("An error occurred while sending message to RMQ: [{}]", ex.getMessage());
        }
    }
}
