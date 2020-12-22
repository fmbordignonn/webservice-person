package com.teste.southsystem.person.service;

import com.teste.southsystem.person.model.DTO.PersonDTO;
import com.teste.southsystem.person.model.Entity.PersonEntity;

import java.util.List;

public interface PersonService {
    String create(PersonDTO person);
    List<PersonEntity> findAll();
}
