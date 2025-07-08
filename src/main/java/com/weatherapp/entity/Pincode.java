package com.weatherapp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Pincode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // or AUTO, depending on DB
    private Long id;
    private String pincode;
    private double latitude;
    private double longitude;
}
