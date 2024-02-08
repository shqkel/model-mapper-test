# model-mapper-test
## Model Mapper란

Java 객체 간 매핑을 자동화하는 오픈 소스 라이브러리

JPA 엔티티와 DTO(Data Transfer Object) 간 매핑처럼 서로 다른 구조를 가진 객체들을 간편하게 변환할수 있다.

## ⚙️환경설정

### 의존 추가

```xml
<dependency>
    <groupId>org.modelmapper</groupId>
    <artifactId>modelmapper</artifactId>
    <version>3.2.0</version>
</dependency>
```

```groovy
implementation 'org.modelmapper:modelmapper:3.2.0'
```

### 스프링 빈등록

```java
@Configuration
public class AppConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
```
## 매핑 전략
ModelMapper객체에 전략을 설정할 수 있다.
- LOOSE 토큰(필드명을 쪼갠 문자열)이 계층구조를 포함해 어떤 순서로도 일치한다면 매핑. 
- STANDARD 기본매핑전략. 토큰을 지능적으로 매칭시켜 매핑.
- STRICT 계층구조를 고려하지 않고 속성명이 정확히 일치하면 매핑.

####  [@com.sh.app.property 예제](https://github.com/shqkel/model-mapper-test/blob/master/src/test/java/com/sh/app/property)
```java
// STANDARD 전략 (기본값)
modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
// LOOSE 전략
modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
// STRICT 전략
modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
```


## 다양한 매핑 케이스

### 1. 필드명이 다른 경우
TypeMap을 사용해 mapping정보를 추가 설정해야 한다.

#### [@com.sh.app.time 예제](https://github.com/shqkel/model-mapper-test/tree/master/src/test/java/com/sh/app/time)
```
Source#timestamp -> Destination#creationTimestamp
```
```java
TypeMap<Source, Destination> typeMap = modelMapper.createTypeMap(Source.class, Destination.class);
typeMap.addMapping(Source::getTimestamp, Destination::setCreationTimestamp);
```
### 2. 계층구조가 다른 경우

#### [@com.sh.app.order 예제](https://github.com/shqkel/model-mapper-test/tree/master/src/test/java/com/sh/app/order)
전략에 따라 매핑 결과가 다르다. 계층구조가 다를때는 LOOSE 전략이 제일 적합하다.
```
Order -> OrderDto
- Order#customer#name#firstName -> OrderDto#customerFirstName
- Order#customer#name#lastName -> OrderDto#customerLastName
- Order#address#street -> OrderDto#billingStreet
- Order#address#city -> OrderDto#billingCity
```
```
OrderDto -> Order
- OrderDto#customerFirstName -> Order#customer#name#firstName
- OrderDto#customerLastName -> Order#customer#name#lastName
- OrderDto#billingStreet -> Order#address#street
- OrderDto#billingCity -> Order#address#city
```

#### [@com.sh.app.game 예제](https://github.com/shqkel/model-mapper-test/tree/master/src/test/java/com/sh/app/game)
TypeMap을 사용해 mapping정보를 추가 설정할 수 있다.
```
Game -> GameDto
- Game#id -> GameDto#id
- Game#name -> GameDto#name
- Game#player#name -> GameDto#player:String
 ```
```java
TypeMap<Game, GameDto> typeMap = modelMapper.createTypeMap(Game.class, GameDto.class);
typeMap.addMappings(mapping -> mapping.map(src -> src.getPlayer().getName(), GameDto::setPlayer));
```
### 3. collection value을 single value로 변환하는 경우 
TypeMap을 사용해 converter를 사용하는 mapping정보를 추가 설정해야 한다.

#### [@com.sh.app.team 예제](https://github.com/shqkel/model-mapper-test/tree/master/src/test/java/com/sh/app/team)
```
Team -> TeamDto
- Team#id -> TeamDto
- Team#supporters:List<Supporter> -> TeamDto#supporterCount:int
```

### 4. collection value를 collection value로 변환하는 경우

#### [@com.sh.app.type.token 예제](https://github.com/shqkel/model-mapper-test/tree/master/src/test/java/com/sh/app/token)
TypeToken은 정확히 필드명이 일치하는 경우만 사용할 수 있다.
```
List<Integer> -> List<Character>
- Integer#value -> Character#value
```
```
List<Client> -> List<User>
- Client#id -> User#id
- Client#nickname -> User#name (실패)
```

#### [@com.sh.app.chatroom 예제](https://github.com/shqkel/model-mapper-test/tree/master/src/test/java/com/sh/app/chatroom)
TypeMap을 사용해 converter를 사용하는 mapping정보를 추가 설정해야 한다.
```
ChatRoomCreateDto -> ChatRoomEntity
- ChatRoomCreateDto#name -> ChatRoomEntity#name
- ChatRoomCreateDto#memberId:List<Long> -> ChatRoomEntity#members:List<Member>
```

dto의 List<Long>을 entity의 List<Member>로 변환하는 커스텀 converter를 사용
```java
class LongToSomeEntityConverter extends AbstractConverter<List<Long>, List<Member>> {
    @Override
    protected List<Member> convert(List<Long> ids) {
        return ids
                .stream()
                .map(Member::new)
                .collect(Collectors.toList());
    }
}
```
```java
TypeMap<ChatRoomCreateDto, ChatRoomEntity> typeMap = this.modelMapper.createTypeMap(ChatRoomCreateDto.class, ChatRoomEntity.class);
typeMap.addMappings(
  mapper -> mapper
                .using(new LongToSomeEntityConverter())
                .map(ChatRoomCreateDto::getMemberId, ChatRoomEntity::setMembers)
);
```

### 5. 특정필드를 skip하는 경우
#### [@com.sh.app.person 예제](https://github.com/shqkel/model-mapper-test/tree/master/src/test/java/com/sh/app/person)
```
PersonEntity -> PeronsDto
- PersonEntity#id -> PersonDto#id
- PersonEntity#secret -> PersonDto#secret (skip)
```
```java
TypeMap<PersonEntity, PersonDto> typeMap = modelMapper.createTypeMap(PersonEntity.class, PersonDto.class);
typeMap.addMappings(mapping -> mapping.skip(PersonDto::setSecret));
```

### 6. 기존 객체에 update mapping하는 경우
#### [@com.sh.app.note 예제](https://github.com/shqkel/model-mapper-test/tree/master/src/test/java/com/sh/app/note)
null인 컬럼은 건너뛰기 설정이 필요하다.
```java
modelMapper.getConfiguration().setSkipNullEnabled(true);
```

## SeeAlso
[https://www.baeldung.com/java-modelmapper](https://www.baeldung.com/java-modelmapper)
