package com.weatherapp.dtos;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeatherReq {
    private String pincode;
    private LocalDate forDate;
}
