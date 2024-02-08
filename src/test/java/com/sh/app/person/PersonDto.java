package com.sh.app.person;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonDto {
    private Long id;
    private String secret; // 값을 담지 않을거면 아예 안만드는게 낫겠지만...
}
