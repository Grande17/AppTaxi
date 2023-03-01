package com.grande.taxiApp.foreignApi.addressToCoordinates;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CoordinatesClient {


    private final RestTemplate restTemplate;
    @Value("${addressToCoordinates.api}")
    private String api;
    @Value("${addressToCoordinates.host}")
    private String host;
    @Value("${addressToCoordinates.host_value}")
    private String hostValue;
    @Value("${addressToCoordinates.key}")
    private String key;
    @Value("${addressToCoordinates.key_value}")
    private String keyValue;

    public CoordinatesDto getCoordinates(String address) throws Exception {
        try {
            log.info("Connecting to CoordinatesAPI");
            RequestEntity<Void> request = RequestEntity.get(api + address)
                    .accept(MediaType.APPLICATION_JSON)
                    .header(host, hostValue)
                    .header(key, keyValue)
                    .build();
            ResponseEntity<CoordinatesListDto> response = restTemplate.exchange(request, CoordinatesListDto.class);
            if (Optional.of(response).isEmpty()){
                throw new Exception("Response is empty");
            }else {


                return response.getBody().getAllCoordinates().stream().findFirst().get();
            }
        }catch (RestClientException e){
            log.error("Error while connection to CoordinatesAPI: "+e.getMessage());
        }
        return null;
    }
}
