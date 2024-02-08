package com.sh.app.person;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

class PersonTest {
    ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        this.modelMapper = new ModelMapper();
    }

    /**
     * PersonEntity -> PeronsDto
     * - PersonEntity#id -> PersonDto#id
     * - PersonEntity#secret -> PersonDto#secret (skip)
     *
     */
    @DisplayName("매핑시 특정컬럼을 skip처리할 수 있다.")
    @Test
    public void test() throws Exception {
        // given
        PersonEntity personEntity = new PersonEntity(1L, "1234");
        // when
        TypeMap<PersonEntity, PersonDto> typeMap = modelMapper.createTypeMap(PersonEntity.class, PersonDto.class);
        typeMap.addMappings(mapping -> mapping.skip(PersonDto::setSecret));
        PersonDto personDto = modelMapper.map(personEntity, PersonDto.class);
        // then
        System.out.println(personDto);
    }

}