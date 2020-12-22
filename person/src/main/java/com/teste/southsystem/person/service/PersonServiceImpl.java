package com.teste.southsystem.person.service;

import com.teste.southsystem.person.model.DTO.PersonDTO;
import com.teste.southsystem.person.model.Entity.PersonEntity;
import com.teste.southsystem.person.repository.PersonRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.slf4j.LoggerFactory.getLogger;
import static com.teste.southsystem.person.util.Mapper.*;

@Service
public class PersonServiceImpl implements PersonService {

    private static Logger LOG = getLogger(PersonServiceImpl.class);

    private PersonRepository personRepository;
    private RabbitMQService rabbitMQService;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository, RabbitMQService rabbitMQService) {
        this.personRepository = personRepository;
        this.rabbitMQService = rabbitMQService;
    }

    @Override
    public String create(PersonDTO person) {
        try {
            LOG.info("Persisting person information to database");
            person.setScore(ThreadLocalRandom.current().nextInt(0, 10));
            validatePerson(person);
            PersonEntity entity = personRepository.save(toEntity(person));
            LOG.info("Person created successfully");

            person.setPersonId(entity.getPersonId());
            rabbitMQService.sendToAccountQueue(person);
        } catch (Exception ex) {
            LOG.error("An error occurred while trying to save entity");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }

        return "Account created successfully";
    }

    @Override
    public List<PersonEntity> findAll() {
        try {
            LOG.info("Getting all persons from dataase");
            List<PersonEntity> persons = personRepository.findAll();
            return persons;
        } catch (Exception ex) {
            LOG.error("An error occurred while fetching persons from database");
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }


    public void validatePerson(PersonDTO person) {
        LOG.info("Started validating person info for person name={}", person.getName());

        String reason = "";

        if (person.getDocument().length() != 11 && person.getType().equals("PF")) {
            reason = "PF document length must be 11";
            LOG.error(reason);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, reason);

        }

        if (person.getDocument().length() != 14 && person.getType().equals("PJ")) {
            reason = "PJ document length must be 14";
            LOG.error(reason);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, reason);

        }

        LOG.info("Finished validating person info");

    }
}