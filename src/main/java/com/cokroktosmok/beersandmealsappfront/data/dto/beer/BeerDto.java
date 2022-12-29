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
public class BeerDto {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty( "image_url")
    private String imageUrl;
    @JsonProperty("abv")
    private Double abv;
    @JsonProperty("ibu")
    private Double ibu;
    @JsonProperty("target_fg")
    private Integer targetFg;
    @JsonProperty("target_og")
    private Integer targetOg;
    @JsonProperty("ebc")
    protected Double ebc;
    @JsonProperty("srm")
    private Double srm;
    @JsonProperty("ph")
    private Double ph;
    @JsonProperty("attenuation_level")
    private Double attenuationLevel;
    @JsonProperty("volume")
    private VolumeDto volumeDto;
    @JsonProperty("boil_volume")
    private BoilVolumeDto boilVolumeDto;
    @JsonProperty("method")
    private MethodDto methodDto;
    @JsonProperty("ingredients")
    private IngredientsDto ingredientsDto;
    @JsonProperty("food_pairing")
    private List<String> foodPairing;
    @JsonProperty("brewers_tips")
    private String brewers_tips;
    @JsonProperty("contributed_by")
    private String contributed_by;

    @Override
    public String toString() {
        return "beer{" +
                "\"description\": " + description +
                "\n\"imageUrl\": " + imageUrl +
                "\n\"abv\": " + abv +
                "\n\"ibu\": " + ibu +
                "\n\"targetFg\": " + targetFg +
                "\n\"targetOg\": " + targetOg +
                "\n\" ebc\": " + ebc +
                "\n\"srm\": " + srm +
                "\n\"ph\": " + ph +
                "\n\"attenuationLevel\": " + attenuationLevel +
                "\n" + volumeDto +
                "\n" + boilVolumeDto +
                "\n" + methodDto +
                "\n" + ingredientsDto +
                "\n\"foodPairing\": " + foodPairing +
                "\n\"brewers_tips\": " + brewers_tips+
                "\n\"contributed_by\": " + contributed_by +
                "\n"+'}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BeerDto beerDto = (BeerDto) o;

        if (!Objects.equals(id, beerDto.id)) return false;
        if (!Objects.equals(description, beerDto.description)) return false;
        if (!Objects.equals(imageUrl, beerDto.imageUrl)) return false;
        if (!Objects.equals(abv, beerDto.abv)) return false;
        if (!Objects.equals(ibu, beerDto.ibu)) return false;
        if (!Objects.equals(targetFg, beerDto.targetFg)) return false;
        if (!Objects.equals(targetOg, beerDto.targetOg)) return false;
        if (!Objects.equals(ebc, beerDto.ebc)) return false;
        if (!Objects.equals(srm, beerDto.srm)) return false;
        if (!Objects.equals(ph, beerDto.ph)) return false;
        if (!Objects.equals(attenuationLevel, beerDto.attenuationLevel))
            return false;
        if (!Objects.equals(volumeDto, beerDto.volumeDto)) return false;
        if (!Objects.equals(boilVolumeDto, beerDto.boilVolumeDto))
            return false;
        if (!Objects.equals(methodDto, beerDto.methodDto)) return false;
        if (!Objects.equals(ingredientsDto, beerDto.ingredientsDto))
            return false;
        if (!Objects.equals(foodPairing, beerDto.foodPairing)) return false;
        if (!Objects.equals(brewers_tips, beerDto.brewers_tips))
            return false;
        return Objects.equals(contributed_by, beerDto.contributed_by);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (imageUrl != null ? imageUrl.hashCode() : 0);
        result = 31 * result + (abv != null ? abv.hashCode() : 0);
        result = 31 * result + (ibu != null ? ibu.hashCode() : 0);
        result = 31 * result + (targetFg != null ? targetFg.hashCode() : 0);
        result = 31 * result + (targetOg != null ? targetOg.hashCode() : 0);
        result = 31 * result + (ebc != null ? ebc.hashCode() : 0);
        result = 31 * result + (srm != null ? srm.hashCode() : 0);
        result = 31 * result + (ph != null ? ph.hashCode() : 0);
        result = 31 * result + (attenuationLevel != null ? attenuationLevel.hashCode() : 0);
        result = 31 * result + (volumeDto != null ? volumeDto.hashCode() : 0);
        result = 31 * result + (boilVolumeDto != null ? boilVolumeDto.hashCode() : 0);
        result = 31 * result + (methodDto != null ? methodDto.hashCode() : 0);
        result = 31 * result + (ingredientsDto != null ? ingredientsDto.hashCode() : 0);
        result = 31 * result + (foodPairing != null ? foodPairing.hashCode() : 0);
        result = 31 * result + (brewers_tips != null ? brewers_tips.hashCode() : 0);
        result = 31 * result + (contributed_by != null ? contributed_by.hashCode() : 0);
        return result;
    }
}
