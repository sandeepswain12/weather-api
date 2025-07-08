package com.weatherapp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Weather {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // or AUTO, depending on DB
    private Long id;
    private LocalDate date;
    private double temperature;
    private String description;
    private int humidity;
    private double windSpeed;

    @ManyToOne
    @JoinColumn(name = "pincode_id")
    private Pincode pincode;
}

