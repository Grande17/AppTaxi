package com.grande.taxiApp.foreignApi.fuelPrice;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FuelPriceDto {

    @JsonProperty("currency")
    private String currency;
    @JsonProperty("diesel")
    private String price;
    @JsonProperty("country")
    private String country;
}
