package com.sh.app.team;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

class TeamTest {
    ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        this.modelMapper = new ModelMapper();
    }

    /**
     * Team -> TeamDto
     * - Team#id -> TeamDto
     * - Team#supporters:List<Supporter> -> TeamDto#supporterCount:int
     * @throws Exception
     */
    @DisplayName("컬렉션타입을 단일값 타입으로 변환한다.")
    @Test
    public void test() throws Exception {
        // given
        List<Supporter> supporters = Arrays.asList(new Supporter(1L), new Supporter(2L));
        Team team = new Team(1L, supporters);
        // when
        TypeMap<Team, TeamDto> typeMap = modelMapper.createTypeMap(Team.class, TeamDto.class);
        Converter<Collection, Integer> collectionToSize = c -> c.getSource().size();
        typeMap.addMappings(mapper ->
                mapper.using(collectionToSize)
                        .map(Team::getSupporters, TeamDto::setSupporerCount)
        );
        TeamDto teamDto = modelMapper.map(team, TeamDto.class);
        // then
        System.out.println(teamDto);
    }
}