package com.grande.taxiApp.foreignApi.fuelPrice;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class FuelPriceClient {

    @Value("${fuel.price.api}")
    private String fuelAPI;
    @Value("${fuel.price.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public static final String AUTH = "authorization";


    public FuelPriceListDto getFuelPrice(){
        log.info("Connecting to FuelPriceAPI");
        RequestEntity<Void> request = RequestEntity.get(fuelAPI)
                .accept(MediaType.APPLICATION_JSON)
                .header(AUTH,apiKey)
                .build();
        ResponseEntity<FuelPriceListDto> response = restTemplate.exchange(request,FuelPriceListDto.class);
        return response.getBody();
    }

}
