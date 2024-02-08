package com.sh.app.property;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

class PropertyMatchingTest {
    ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        this.modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }
    @DisplayName("ModelMapper전략에 따른 매핑 차이")
    @Test
    public void test1() throws Exception {
        // given
        Source source = Source.builder().memberId(1L).name("홍길동").build();
        // when
        Destination destination = modelMapper.map(source, Destination.class);
        // then
        System.out.println(destination);
        // LOOSE : Destination(id=1, name=홍길동, username=null, firstName=홍길동, namePattern=홍길동, yourNameGreat=홍길동)
        // STANDARD : Destination(id=1, name=홍길동, username=null, firstName=null, namePattern=null, yourNameGreat=null)
        // STRICT : Destination(id=null, name=홍길동, username=null, firstName=null, namePattern=null, yourNameGreat=null)
    }

}