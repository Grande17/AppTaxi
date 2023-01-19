package com.grande.taxiApp.foreignApi.fuelPrice;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FuelPriceListDto {

    @JsonProperty("results")
    private List<FuelPriceDto> list;
}
