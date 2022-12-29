package com.cokroktosmok.beersandmealsappfront.data.dto.beer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FermentationDto {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("temp")
    private TempDto tempDto;

    @Override
    public String toString() {
        return "\"fermentation\":{"+
                "\n"+tempDto+
                "\n"+'}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FermentationDto that = (FermentationDto) o;

        if (!Objects.equals(id, that.id)) return false;
        return Objects.equals(tempDto, that.tempDto);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (tempDto != null ? tempDto.hashCode() : 0);
        return result;
    }
}
