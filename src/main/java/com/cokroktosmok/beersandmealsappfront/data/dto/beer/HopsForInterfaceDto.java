package com.cokroktosmok.beersandmealsappfront.data.dto.beer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HopsForInterfaceDto {
    private String name;
    private Double value;
    private String unit;
    private String add;
    private String attribute;

}
