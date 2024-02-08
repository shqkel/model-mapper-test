package com.sh.app.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    String customerFirstName;
    String customerLastName;
    String billingStreet;
    String billingCity;
}