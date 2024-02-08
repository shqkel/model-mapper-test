package com.sh.app.game;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

class GameTest {
    ModelMapper modelMapper = new ModelMapper();

    /**
     * <pre>
     * Game -> GameDto
     * - Game#id -> GameDto#id
     * - Game#name -> GameDto#name
     * - Game#player#name -> GameDto#player:String
     *
     *
     * 별도의 type mapping이 없다면, Game#player:Player의 toString호출 결과가 GameDto#player:String에 매핑된다.
     *
     * TypeMap<S,D>#addMappings(mapper:ExpressionMap<S,D>)
     *
     * <strong>TypeMap#addMapping과 TypeMap#addMappings는 다르다.</strong>
     * </pre>
     *
     * @throws Exception
     */
    @DisplayName("계층구조가 있고, 별도의 타입매핑이 필요한 경우")
    @Test
    public void test() throws Exception {
        // given
        Game game = new Game(1L, "Game 1");
        game.setPlayer(new Player(1L, "John"));
        // when
        TypeMap<Game, GameDto> typeMap = modelMapper.createTypeMap(Game.class, GameDto.class);
        typeMap.addMappings(mapping -> mapping.map(src -> src.getPlayer().getName(), GameDto::setPlayer));
        GameDto gameDto = modelMapper.map(game, GameDto.class);
        // then
        System.out.println(gameDto); // GameDto(id=1, name=Game 1, player=John)
    }
}