package com.sh.app.chatroom;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChatRoomTest {
    ModelMapper modelMapper = new ModelMapper();

    static class LongToSomeEntityConverter extends AbstractConverter<List<Long>, List<Member>> {
        @Override
        protected List<Member> convert(List<Long> ids) {
            return ids
                    .stream()
                    .map(Member::new)
                    .collect(Collectors.toList());
        }
    }

    /**
     * ChatRoomCreateDto -> ChatRoomEntity
     * - ChatRoomCreateDto#name -> ChatRoomEntity#name
     * - ChatRoomCreateDto#memberId:List<Long> -> ChatRoomEntity#members:List<Member>
     * @throws Exception
     */
    @DisplayName("복잡한 매핑 - List<Long> -> List<Member>")
    @Test
    public void test() throws Exception {
        // STANDARD전략에서는 ChatRoomCreateDto#memberId -> ChatRoomEntity#id로 매핑된다.
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        // given
        List<Long> memberId = new ArrayList<>();
        memberId.add(1L);
        memberId.add(2L);
        ChatRoomCreateDto sourceThatHasCollection =
                ChatRoomCreateDto.builder()
                    .name("홍길동")
                    .memberId(memberId)
                    .build();
        // when
        TypeMap<ChatRoomCreateDto, ChatRoomEntity> typeMap = this.modelMapper.createTypeMap(ChatRoomCreateDto.class, ChatRoomEntity.class);
        typeMap.addMappings(
          mapper -> mapper.using(new LongToSomeEntityConverter()).map(ChatRoomCreateDto::getMemberId, ChatRoomEntity::setMembers)
        );

        ChatRoomEntity destinationThatHasCollection =
                modelMapper.map(sourceThatHasCollection, ChatRoomEntity.class);
        // then
        System.out.println(destinationThatHasCollection);
    }

}
