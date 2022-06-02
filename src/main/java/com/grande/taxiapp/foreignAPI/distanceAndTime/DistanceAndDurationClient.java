package com.grande.taxiapp.foreignAPI.distanceAndTime;

import com.grande.taxiapp.foreignAPI.addressToCoordinates.CoordinatesDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

@Slf4j
@Component
public class DistanceAndDurationClient {

    @Value("${distanceAndDuration.api.url}")
    private String url;
    private final String HOST= "X-RapidAPI-Host";
    private final String HOST_VALUE= "trueway-matrix.p.rapidapi.com";
    private final String KEY = "X-RapidAPI-Key";
    private final String KEY_VALUE = "0517e757fbmshb5906afd6b296fep16877ajsnc513eaa9de63";

    @Autowired
    private RestTemplate restTemplate;

    public DistanceAndDurationDto getData(CoordinatesDto pickUpLatAndLong, CoordinatesDto dropLatAndLong){
        log.info("Connecting to DistanceAndDuration API");
        String pickUp = pickUpLatAndLong.getLatitude()+","+pickUpLatAndLong.getLongitude();
        String drop = dropLatAndLong.getLatitude()+","+dropLatAndLong.getLongitude();
        try {
            RequestEntity<Void> request = RequestEntity.get(url+"origins="+pickUp+"&destinations="+drop)
                    .header("content-type","application/json; charset=utf-8")
                    .header(HOST,HOST_VALUE)
                    .header(KEY,KEY_VALUE)
                    .build();
            ResponseEntity<DistanceAndDurationDto> response = restTemplate.exchange(request,DistanceAndDurationDto.class);
            return response.getBody();
        }catch (RestClientException e){
            log.error("Error while connection DistanceAndDurationAPI: "+e.getMessage());
        }
        return null;
    }
}
