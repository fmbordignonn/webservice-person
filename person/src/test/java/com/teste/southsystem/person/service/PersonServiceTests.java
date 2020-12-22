package com.teste.southsystem.person.service;

import com.teste.southsystem.person.model.DTO.PersonDTO;
import com.teste.southsystem.person.model.Entity.PersonEntity;
import com.teste.southsystem.person.repository.PersonRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PersonServiceTests {

    @Mock
    private RabbitMQService rabbitMQService;

    @Mock
    private PersonService personService;

    @Mock
    private PersonRepository personRepository;

    @Before
    public void setup(){
        MockitoAnnotations.openMocks(this);
        this.personService = new PersonServiceImpl(personRepository, rabbitMQService);
    }

    @Test
    @DisplayName("Validate successful person creation")
    public void successPersonCreation() {

        PersonDTO person = createValidPerson();

        when(personRepository.save(Mockito.any())).thenReturn(new PersonEntity());
        Mockito.doNothing().when(rabbitMQService).sendToAccountQueue(Mockito.any());

        assertEquals("Account created successfully",  personService.create(person));

    }

    @Test
    @DisplayName("Validate unsuccessful person creation")
    public void failPersonCreation(){
        PersonDTO person = createInvalidPerson();

        assertThrows(ResponseStatusException.class, () -> personService.create(person));
    }

    private PersonDTO createValidPerson(){
        return new PersonDTO().builder()
                .name("Test person")
                .type("PF")
                .document("01610461002")
                .build();
    }

    private PersonDTO createInvalidPerson(){
        return new PersonDTO().builder()
                .name("Test person")
                .type("PJ")
                .document("123")
                .build();
    }

}
