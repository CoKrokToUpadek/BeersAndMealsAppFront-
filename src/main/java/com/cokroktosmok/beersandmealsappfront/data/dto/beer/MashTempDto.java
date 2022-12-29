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
public class MashTempDto {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("temp")
    private  TempDto tempDto;
    @JsonProperty("duration")
    private Integer duration;


    @Override
    public String toString() {
        return  "\n\t\t{"+
                "\n"+tempDto+
                "\n\t\t\"duration\": "+duration+
                "\n\t\t"+'}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MashTempDto that = (MashTempDto) o;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(tempDto, that.tempDto)) return false;
        return Objects.equals(duration, that.duration);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (tempDto != null ? tempDto.hashCode() : 0);
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        return result;
    }
}
