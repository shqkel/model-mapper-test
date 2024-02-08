package com.sh.app.property;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Destination {
    Long id;
    String name;
    String username;
    String firstName;
    String namePattern;
    String yourNameGreat;
}