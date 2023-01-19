package com.grande.taxiapp.foreignAPI.addressToCoordinates;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class CoordinatesClient {

    @Autowired
    private RestTemplate restTemplate;
    @Value("${addressToCoordinates.api}")
    private String APILink;
    private final String HOST = "X-RapidAPI-Host";
    private final String HOST_VALUE = "address-from-to-latitude-longitude.p.rapidapi.com";
    private final String KEY = "X-RapidAPI-Key";
    private final String KEY_VALUE = "6381154e39mshf50c1d240804828p124e5cjsn33065f9d1c4c";

    public CoordinatesDto getCoordinates(String address){
        try {
            log.info("Connecting to CoordinatesAPI");
            RequestEntity<Void> request = RequestEntity.get(APILink + address)
                    .accept(MediaType.APPLICATION_JSON)
                    .header(HOST, HOST_VALUE)
                    .header(KEY, KEY_VALUE)
                    .build();
            ResponseEntity<CoordinatesListDto> response = restTemplate.exchange(request, CoordinatesListDto.class);
            CoordinatesDto result = response.getBody().getAllCoordinates().stream().findFirst().get();

            return result;
        }catch (RestClientException e){
            log.error("Error while connection to CoordinatesAPI: "+e.getMessage());
        }
        return null;
    }
}
