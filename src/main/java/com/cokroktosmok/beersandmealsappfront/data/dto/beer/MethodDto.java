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
public class MethodDto {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("mash_temp")
    private List<MashTempDto> mashTempDtoList;
    @JsonProperty("fermentation")
    private FermentationDto fermentationDto;
    @JsonProperty("twist")
    private String twist;

    @Override
    public String toString() {
        return "\"method\":{"+
                "\n\t\"mash_temp\": "+mashTempDtoList+
                "\n\t"+fermentationDto+
                "\n\t\"twist\": "+twist+
                "\n"+'}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MethodDto methodDto = (MethodDto) o;

        if (!Objects.equals(id, methodDto.id)) return false;
        if (!Objects.equals(mashTempDtoList, methodDto.mashTempDtoList))
            return false;
        if (!Objects.equals(fermentationDto, methodDto.fermentationDto))
            return false;
        return Objects.equals(twist, methodDto.twist);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (mashTempDtoList != null ? mashTempDtoList.hashCode() : 0);
        result = 31 * result + (fermentationDto != null ? fermentationDto.hashCode() : 0);
        result = 31 * result + (twist != null ? twist.hashCode() : 0);
        return result;
    }
}
