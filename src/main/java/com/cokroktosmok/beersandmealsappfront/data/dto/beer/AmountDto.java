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
public class AmountDto {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("value")
    private Double value;
    @JsonProperty( "unit")
    private String unit;

    @Override
    public String toString() {
        return  "\n\t\t\t\"value\": "+value+
                "\n\t\t\t\"unit\": "+unit+
                "\n\t\t\t"+'}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AmountDto amountDto = (AmountDto) o;

        if (!Objects.equals(id, amountDto.id)) return false;
        if (!Objects.equals(value, amountDto.value)) return false;
        return Objects.equals(unit, amountDto.unit);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (unit != null ? unit.hashCode() : 0);
        return result;
    }
}
