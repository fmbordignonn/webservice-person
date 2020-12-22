package com.teste.southsystem.person.util;

import com.teste.southsystem.person.model.DTO.PersonDTO;
import com.teste.southsystem.person.model.Entity.PersonEntity;

public class Mapper {

    public static PersonDTO toDTO(PersonEntity entity){
        return PersonDTO.builder().name(entity.getName())
                .type(entity.getType())
                .document(entity.getDocument())
                .score(entity.getScore())
                .personId(entity.getPersonId())
                .build();
    }

    public static PersonEntity toEntity(PersonDTO dto){
        return new PersonEntity(
                dto.getPersonId(),
                dto.getName(),
                dto.getType(),
                dto.getDocument(),
                dto.getScore()
        );
    }
}
