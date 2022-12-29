package com.cokroktosmok.beersandmealsappfront.data.dto.meal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class IngredientAndMeasureDto {
    @JsonProperty("IngredientName")
    private String IngredientName;
    @JsonProperty("IngredientMeasure")
    private String IngredientMeasure;
}
