package com.weatherapp.repository;

import com.weatherapp.entity.Pincode;
import com.weatherapp.entity.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface WeatherRepo extends JpaRepository<Weather,Long> {
    Optional<Weather> findByPincodeAndDate(Pincode pincode, LocalDate date);
}
