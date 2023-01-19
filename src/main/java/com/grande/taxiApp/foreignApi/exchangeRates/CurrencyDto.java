package com.grande.taxiApp.foreignApi.exchangeRates;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyDto {

    @JsonProperty("currency")
    private String currency;
    @JsonProperty("code")
    private String code;
    @JsonProperty("rates")
    private List<RatesDto> rates;


}
