package com.weatherapp;

import com.weatherapp.dtos.WeatherReq;
import com.weatherapp.dtos.WeatherRes;
import com.weatherapp.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class WeatherAppApplicationTests {
	@Autowired
	private WeatherService service;

	@Test
	public void testGetWeather() {
		WeatherReq req = new WeatherReq("411014", LocalDate.of(2020, 10, 15));
		WeatherRes res = service.getWeather(req);
		assertNotNull(res);
		assertEquals("411014", res.getPincode());
	}

}
