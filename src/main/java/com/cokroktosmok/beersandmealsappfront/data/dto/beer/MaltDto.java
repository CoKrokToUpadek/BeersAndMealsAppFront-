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
public class MaltDto {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty( "amount")
    private AmountDto amountDto;

    @Override
    public String toString() {
        return  "\n\t\t{"+
                "\n\t\t\"name\": "+name+
                "\n\t\t\"amount\": "+amountDto+
                "\n\t\t"+'}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MaltDto maltDto = (MaltDto) o;

        if (!Objects.equals(id, maltDto.id)) return false;
        if (!Objects.equals(name, maltDto.name)) return false;
        return Objects.equals(amountDto, maltDto.amountDto);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (amountDto != null ? amountDto.hashCode() : 0);
        return result;
    }
}
