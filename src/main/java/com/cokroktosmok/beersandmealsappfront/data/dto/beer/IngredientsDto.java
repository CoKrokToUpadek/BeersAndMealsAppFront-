package com.cokroktosmok.beersandmealsappfront.data.dto.beer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class IngredientsDto {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("malt")
    private List<MaltDto> maltDtoList;
    @JsonProperty( "hops")
    private List<HopsDto> hopsDtoList;
    @JsonProperty("yeast")
    private String yeast;

    @Override
    public String toString() {
        return "ingredients{"+
                "\n\t\"malt\": "+maltDtoList+
                "\n\t\"hops\": "+hopsDtoList+
                "\n\t\"yeast\": "+yeast+
                "\n"+'}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IngredientsDto that = (IngredientsDto) o;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(maltDtoList, that.maltDtoList)) return false;
        if (!Objects.equals(hopsDtoList, that.hopsDtoList)) return false;
        return Objects.equals(yeast, that.yeast);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (maltDtoList != null ? maltDtoList.hashCode() : 0);
        result = 31 * result + (hopsDtoList != null ? hopsDtoList.hashCode() : 0);
        result = 31 * result + (yeast != null ? yeast.hashCode() : 0);
        return result;
    }
}
