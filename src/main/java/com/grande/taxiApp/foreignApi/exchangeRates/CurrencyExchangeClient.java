package com.grande.taxiApp.foreignApi.exchangeRates;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@Component
@RequiredArgsConstructor
public class CurrencyExchangeClient {

    @Value("${nbp.currency.rates.api}")
    private String nbpApiEndpoint;

    private final RestTemplate restTemplate;

    private URI createURI(){
        return UriComponentsBuilder.fromHttpUrl(nbpApiEndpoint).build().encode().toUri();
    }
    public CurrencyDto getCurrentCurrencyRate(){
        try{
            return restTemplate.getForObject(createURI(),CurrencyDto.class);
        }catch (RestClientException e){
            log.error("Error during connecting to NBP API! "+ e.getMessage() );
        }
        return null;
    }
}
