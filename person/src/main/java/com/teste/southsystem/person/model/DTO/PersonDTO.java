package com.teste.southsystem.person.model.DTO;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class PersonDTO {
    private Long personId;
    private String name;
    private String type;
    private String document;
    private int score;
}
