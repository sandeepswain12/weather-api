package com.weatherapp.controller;

import com.weatherapp.dtos.WeatherReq;
import com.weatherapp.dtos.WeatherRes;
import com.weatherapp.service.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {
    @Autowired
    private WeatherService service;

    Logger logger = LoggerFactory.getLogger(WeatherController.class);

    @PostMapping
    public ResponseEntity<WeatherRes> getWeather(@RequestBody WeatherReq request) {
        logger.info("API /api/weather called with request: {}", request);
        WeatherRes res = service.getWeather(request);
        return ResponseEntity.ok(res);
    }
}
