package com.grande.taxiApp.foreignApi.distanceAndTime;

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
public class DistanceAndDurationDto {

    @JsonProperty("distances")
    private List<List<Long>> distance;

    @JsonProperty("durations")
    private List<List<Long>> durationInSeconds;
}
