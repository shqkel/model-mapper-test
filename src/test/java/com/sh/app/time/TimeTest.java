package com.sh.app.time;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class TimeTest {
    ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        this.modelMapper = new ModelMapper();
    }

    /**
     * <pre>
     * Source#timestamp -> Destination#creationTimestamp 매핑을 시도한다.
     *
     * ModelMapper - TypeMap - mapping 정보 추가 (sourceGetter, destinationSetter)
     *
     */
    @DisplayName("필드명이 다른 경우, 별도의 TypeMap(mapping정보)를 작성한다.")
    @Test
    public void test() throws Exception {
        // given
        Source source = Source.builder().timestamp(LocalDateTime.now()).build();

        // when
        TypeMap<Source, Destination> typeMap = modelMapper.createTypeMap(Source.class, Destination.class);
        typeMap.addMapping(Source::getTimestamp, Destination::setCreationTimestamp);
        Destination destination = modelMapper.map(source, Destination.class);

        // then
        System.out.println(destination);
        assertThat(destination.getCreationTimestamp()).isNotNull();
    }
}