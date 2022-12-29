package com.cokroktosmok.beersandmealsappfront.data.dto.meal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MealDto {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("category")
    private String category;
    @JsonProperty("area")
    private String area;
    @JsonProperty("instruction")
    private String instruction;
    @JsonProperty("thumbnail")
    private String thumbnail;
    @JsonProperty("tags")
    private String tags;
    @JsonProperty("youtubeLink")
    private String youtubeLink;
    @JsonProperty("ingredientsAndMeasure")
    List<IngredientAndMeasureDto> ingredientsAndMeasureDtoList;
    @JsonProperty("source")
    private String source;
}
