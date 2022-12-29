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
public class HopsDto {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty( "amount")
    private AmountDto amountDto;
    @JsonProperty("add")
    private String add;
    @JsonProperty("attribute")
    private String attribute;

    @Override
    public String toString() {
        return  "\n\t\t{"+
                "\n\t\t\"name\": "+name+
                "\n\t\t\"amount\": "+amountDto+
                "\n\t\t\"add\": "+add+
                "\n\t\t\"attribute\": "+attribute+
                "\n"+'}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HopsDto hopsDto = (HopsDto) o;

        if (!Objects.equals(id, hopsDto.id)) return false;
        if (!Objects.equals(name, hopsDto.name)) return false;
        if (!Objects.equals(amountDto, hopsDto.amountDto)) return false;
        if (!Objects.equals(add, hopsDto.add)) return false;
        return Objects.equals(attribute, hopsDto.attribute);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (amountDto != null ? amountDto.hashCode() : 0);
        result = 31 * result + (add != null ? add.hashCode() : 0);
        result = 31 * result + (attribute != null ? attribute.hashCode() : 0);
        return result;
    }
}
