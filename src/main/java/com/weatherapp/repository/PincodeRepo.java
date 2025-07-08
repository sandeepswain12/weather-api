package com.weatherapp.repository;

import com.weatherapp.entity.Pincode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PincodeRepo extends CrudRepository<Pincode,Long> {
    Optional<Pincode> findByPincode(String pincode);
}
