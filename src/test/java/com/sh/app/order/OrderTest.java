package com.sh.app.order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;

class OrderTest {
    ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        this.modelMapper = new ModelMapper();
        MatchingStrategy matchingStrategy = MatchingStrategies.LOOSE;
        modelMapper.getConfiguration().setMatchingStrategy(matchingStrategy);
    }
    /**
     * <pre>
     * Order -> OrderDto
     * - Order#customer#name#firstName -> OrderDto#customerFirstName
     * - Order#customer#name#lastName -> OrderDto#customerLastName
     * - Order#address#street -> OrderDto#billingStreet
     * - Order#address#city -> OrderDto#billingCity
     * </pre>
     */
    @DisplayName("계층구조가 적용된 매핑처리(entity to dto)")
    @Test
    public void test1() throws Exception {
        // given
        Address address = new Address("서울", "충정로");
        Name name = new Name("길동", "홍");
        Customer customer = new Customer(name);
        Order order = new Order(customer, address);
        // when
        OrderDto orderDto = modelMapper.map(order, OrderDto.class);
        System.out.println(orderDto);
        // then
        // LOOSE : ModelMapperTest.OrderDto(customerFirstName=길동, customerLastName=홍, billingStreet=충정로, billingCity=서울)
        // STANDARD : ModelMapperTest.OrderDto(customerFirstName=길동, customerLastName=홍, billingStreet=null, billingCity=null)
        // STRICT : ModelMapperTest.OrderDto(customerFirstName=null, customerLastName=null, billingStreet=null, billingCity=null)
    }

    /**
     * <pre>
     * OrderDto -> Order
     * - OrderDto#customerFirstName -> Order#customer#name#firstName
     * - OrderDto#customerLastName -> Order#customer#name#lastName
     * - OrderDto#billingStreet -> Order#address#street
     * - OrderDto#billingCity -> Order#address#city
     * </pre>
     */
    @DisplayName("계층구조가 적용된 매핑처리(dto to entity)")
    @Test
    public void test2() throws Exception {
        // given
        OrderDto orderDto = new OrderDto("길동", "홍", "서울", "충정로");
        // when
        Order order = modelMapper.map(orderDto, Order.class);
        System.out.println(order);
        // then
        // LOOSE : ModelMapperTest.Order(customer=ModelMapperTest.Customer(name=ModelMapperTest.Name(firstName=길동, lastName=홍)), address=ModelMapperTest.Address(street=충정로, city=서울))
        // STANDARD : ModelMapperTest.Order(customer=ModelMapperTest.Customer(name=ModelMapperTest.Name(firstName=길동, lastName=홍)), address=null)
        // STRICT : ModelMapperTest.Order(customer=null, address=null)
    }

}