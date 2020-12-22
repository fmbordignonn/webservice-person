package com.teste.southsystem.person.api;

import com.teste.southsystem.person.model.DTO.PersonDTO;
import com.teste.southsystem.person.service.PersonService;
import com.teste.southsystem.person.service.PersonServiceImpl;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping("/person")
public class PersonAPI {

    private static Logger LOG = getLogger(PersonAPI.class);

    private PersonService personService;

    @Autowired
    public PersonAPI(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody PersonDTO person) {
        try {
            LOG.info("Saving person information");
            return ResponseEntity.ok(personService.create(person));
        } catch (ResponseStatusException response) {
            LOG.error("An error occurred while trying to save person information");
            LOG.error("Error [{}]", response.getMessage());
            return ResponseEntity.status(response.getStatus()).body(response.getMessage());
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity findAll() {
        try {
            LOG.info("Getting all persons information");
            return ResponseEntity.ok(personService.findAll());
        } catch (ResponseStatusException response) {
            LOG.error("An error occurred while trying to get all persons information");
            LOG.error("Error [{}]", response.getMessage());
            return ResponseEntity.status(response.getStatus()).body(response.getMessage());
        }
    }


}
