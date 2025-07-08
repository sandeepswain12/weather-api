package com.weatherapp.service;

import com.weatherapp.dtos.WeatherReq;
import com.weatherapp.dtos.WeatherRes;

public interface WeatherService {
    WeatherRes getWeather(WeatherReq request);
}
