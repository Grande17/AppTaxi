package com.grande.taxiapp.foreignAPI.exchangeRates;

import com.grande.taxiapp.domain.dto.CurrencyDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@Component
public class CurrencyExchangeClient {

    @Value("${nbp.currency.rates.api}")
    private String nbpApiEndpoint;


    @Autowired
    private RestTemplate restTemplate;

    private URI createURI(){
        URI url = UriComponentsBuilder.fromHttpUrl(nbpApiEndpoint)
                .build().encode().toUri();
        return url;
    }
    public CurrencyDto getCurrentCurrencyRate(){
        try{
            CurrencyDto currencyDto = restTemplate.getForObject(createURI(),CurrencyDto.class);
            return currencyDto;
        }catch (RestClientException e){
            log.error("Error during connecting to NBP API! "+ e.getMessage() );
        }
        return null;
    }
}
