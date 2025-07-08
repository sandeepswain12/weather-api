package com.weatherapp.dtos;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeatherRes {
    private String pincode;
    private LocalDate date;
    private double temperature;
    private String description;
    private int humidity;
    private double windSpeed;
}
