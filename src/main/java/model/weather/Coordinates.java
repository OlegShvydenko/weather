package model.weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Coordinates {
    @JsonProperty("lon")
    private double longitude;
    @JsonProperty("lat")
    private double latitude;
}
