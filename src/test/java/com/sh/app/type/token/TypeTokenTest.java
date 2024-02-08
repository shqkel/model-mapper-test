package com.sh.app.type.token;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TypeTokenTest {
    ModelMapper modelMapper = new ModelMapper();

    /**
     * Integer#value - Character#value
     * Integer-Character간 변환이 가능한 것은 value 속성을 가지고 있기 때문이다.
     *
     */
    @DisplayName("TypeToken을 사용해서 List<Integer>에서 List<Character>로 변환하기")
    @Test
    public void test() throws Exception {
        // given
        List<Integer> integers = new ArrayList<>();
        integers.add(1);
        integers.add(2);
        integers.add(3);
        // when
        Type destinationType = new TypeToken<List<Character>>() {}.getType();
        List<Character> characters = modelMapper.map(integers, destinationType);

        // then
        System.out.println(characters);
    }

    /**
     * 
     * 
     * 변환할 타입간의 필드명이 동일할때만 사용가능하다.
     * 필드명이 다르다면 별도의 converter를 사용해야 한다.
     */
    @DisplayName("TypeToken을 사용해서 List<Client>에서 List<User>로 변환하기")
    @Test
    public void test3() throws Exception {
        // given
        List<Client> clients = new ArrayList<>();
        clients.add(new Client(1L, "홍길동"));
        clients.add(new Client(2L, "신사임당"));
        clients.add(new Client(3L, "이순신"));

        // when
        List<User> users = modelMapper.map(clients, new TypeToken<List<User>>() {}.getType());

        // then
        System.out.println(users); // [User(id=1, username=null), User(id=2, username=null), User(id=3, username=null)]
    }
}
