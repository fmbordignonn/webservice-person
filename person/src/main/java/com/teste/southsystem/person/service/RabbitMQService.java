package com.teste.southsystem.person.service;

import com.teste.southsystem.person.model.DTO.PersonDTO;

public interface RabbitMQService {
    void sendToAccountQueue(PersonDTO personDto);
}
