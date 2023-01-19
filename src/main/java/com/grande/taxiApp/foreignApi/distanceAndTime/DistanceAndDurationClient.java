package com.grande.taxiApp.foreignApi.distanceAndTime;

import com.grande.taxiApp.foreignApi.addressToCoordinates.CoordinatesDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class DistanceAndDurationClient {

    @Value("${distanceAndDuration.api.url}")
    private String url;
    @Value("${distanceAndDuration.api.host}")
    private String host;
    @Value("${distanceAndDuration.api.host_value}")
    private String hostValue;
    @Value("${distanceAndDuration.api.key}")
    private String key;
    @Value("${distanceAndDuration.api.key_value}")
    private String keyValue;

    private final RestTemplate restTemplate;

    public DistanceAndDurationDto getData(CoordinatesDto pickUpLatAndLong, CoordinatesDto dropLatAndLong){
        log.info("Connecting to DistanceAndDuration API");
        String pickUp = pickUpLatAndLong.getLatitude()+","+pickUpLatAndLong.getLongitude();
        String drop = dropLatAndLong.getLatitude()+","+dropLatAndLong.getLongitude();
        try {
            RequestEntity<Void> request = RequestEntity.get(url+"origins="+pickUp+"&destinations="+drop)
                    .header("content-type","application/json; charset=utf-8")
                    .header(host, hostValue)
                    .header(key, keyValue)
                    .build();
            ResponseEntity<DistanceAndDurationDto> response = restTemplate.exchange(request,DistanceAndDurationDto.class);
            return response.getBody();
        }catch (RestClientException e){
            log.error("Error while connection DistanceAndDurationAPI: "+e.getMessage());
        }
        return null;
    }
}
