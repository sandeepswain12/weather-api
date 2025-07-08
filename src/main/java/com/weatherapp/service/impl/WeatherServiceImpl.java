package com.weatherapp.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.weatherapp.dtos.WeatherReq;
import com.weatherapp.dtos.WeatherRes;
import com.weatherapp.entity.Pincode;
import com.weatherapp.entity.Weather;
import com.weatherapp.exception.GlobalExceptionHandler;
import com.weatherapp.exception.PincodeNotFoundException;
import com.weatherapp.repository.PincodeRepo;
import com.weatherapp.repository.WeatherRepo;
import com.weatherapp.service.WeatherService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class WeatherServiceImpl implements WeatherService {

    @Autowired
    private PincodeRepo pincodeRepo;
    @Autowired
    private WeatherRepo weatherRepo;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ModelMapper modelMapper;

    @Value("${openweather.api.key}")
    private String apiKey;

    Logger logger = LoggerFactory.getLogger(WeatherServiceImpl.class);

    @Override
    public WeatherRes getWeather(WeatherReq request) {
        logger.info("Received weather request for pincode: {} and date: {}", request.getPincode(), request.getForDate());

        Pincode pin = pincodeRepo.findByPincode(request.getPincode())
                .orElseGet(() -> {
                    logger.info("Pincode not found in DB, fetching from external API: {}", request.getPincode());
                    Pincode fetched = fetchAndSaveLatLong(request.getPincode());
                    if (fetched == null) {
                        logger.error("Failed to fetch location for pincode: {}", request.getPincode());
                        throw new PincodeNotFoundException("Unable to fetch location for pincode: " + request.getPincode());
                    }
                    return fetched;
                });

        Optional<Weather> cached = weatherRepo.findByPincodeAndDate(pin, request.getForDate());

        if (cached.isPresent()) {
            logger.info("Weather found in cache for pincode: {} and date: {}", request.getPincode(), request.getForDate());
            return mapToResponse(cached.get());
        }

        logger.info("Fetching fresh weather data from external API...");
        Weather info = fetchWeatherAndSave(pin, request.getForDate());
        return mapToResponse(info);
    }

    private Pincode fetchAndSaveLatLong(String pincode) {
        try {
            String url = "http://api.openweathermap.org/geo/1.0/zip?zip=" + pincode + ",IN&appid=" + apiKey;
            JsonNode res = restTemplate.getForObject(url, JsonNode.class);

            // Validate response
            if (res == null || res.path("lat").isMissingNode() || res.path("lon").isMissingNode()) {
                return null; // Will be caught and exception thrown in the caller
            }

            Pincode pin = new Pincode();
            pin.setPincode(pincode);
            pin.setLatitude(res.path("lat").asDouble());
            pin.setLongitude(res.path("lon").asDouble());

            return pincodeRepo.save(pin);
        } catch (Exception e) {
            return null; // Also causes exception in the caller
        }
    }


    private Weather fetchWeatherAndSave(Pincode pin, LocalDate date) {
        String url = "https://api.openweathermap.org/data/2.5/weather?lat=" + pin.getLatitude()
                + "&lon=" + pin.getLongitude()
                + "&appid=" + apiKey
                + "&units=metric";

        JsonNode res = restTemplate.getForObject(url, JsonNode.class);

        Weather info = new Weather();
        info.setPincode(pin);
        info.setDate(date);
        info.setTemperature(res.path("main").path("temp").asDouble());
        info.setDescription(res.path("weather").get(0).path("description").asText());
        info.setHumidity(res.path("main").path("humidity").asInt());
        info.setWindSpeed(res.path("wind").path("speed").asDouble());

        return weatherRepo.save(info);
    }

    private WeatherRes mapToResponse(Weather info) {
        return modelMapper.map(info, WeatherRes.class);
    }
}
