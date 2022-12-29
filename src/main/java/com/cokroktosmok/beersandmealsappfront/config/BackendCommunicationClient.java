package com.cokroktosmok.beersandmealsappfront.config;

import com.cokroktosmok.beersandmealsappfront.data.dto.beer.BeerDto;
import com.cokroktosmok.beersandmealsappfront.data.dto.meal.MealDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class BackendCommunicationClient {

    private final Config beerConfig;
    private final RestTemplate restTemplate;

    public List<MealDto> getMealDtoList() {
        URI url = buildUriForAllMeals();
        MealDto[] mealDtoList = restTemplate.getForObject(url, MealDto[].class);
        return Optional.of(mealDtoList).map(Arrays::asList)
                .orElse(Collections.emptyList());
    }

    private URI buildUriForAllMeals() {
        return UriComponentsBuilder.fromHttpUrl(beerConfig.getBeerAppBasicEndpoint())
                .pathSegment(beerConfig.getUserFunctionalities())
                .pathSegment(beerConfig.getGetMeals())
                .build()
                .encode()
                .toUri();
    }


    public List<BeerDto> getBeerDtoList() {
        URI url = buildUriForAllBeers();
        BeerDto[] beerDtoList = restTemplate.getForObject(url, BeerDto[].class);
        return Optional.of(beerDtoList).map(Arrays::asList)
                .orElse(Collections.emptyList());
    }

    private URI buildUriForAllBeers() {
        return UriComponentsBuilder.fromHttpUrl(beerConfig.getBeerAppBasicEndpoint())
                .pathSegment(beerConfig.getUserFunctionalities())
                .pathSegment(beerConfig.getGetBeers())
                .build()
                .encode()
                .toUri();
    }
}
