package com.grande.taxiApp.foreignApi.addressToCoordinates;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CoordinatesDto {

    @JsonProperty("latitude")
    private BigDecimal latitude;

    @JsonProperty("longitude")
    private BigDecimal longitude;

    @JsonProperty("address")
    private String address;
}
