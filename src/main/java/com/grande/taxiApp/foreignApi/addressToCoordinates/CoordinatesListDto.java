package com.grande.taxiApp.foreignApi.addressToCoordinates;

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
public class CoordinatesListDto {

    @JsonProperty("Results")
    private List<CoordinatesDto> allCoordinates;
}
