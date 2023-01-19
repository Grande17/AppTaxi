package com.grande.taxiApp.foreignApi.fuelPrice;

import com.grande.taxiApp.domain.FuelPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FuelPriceRepository extends JpaRepository<FuelPrice, Integer> {

    Optional<FuelPrice> findFuelPriceByCountry(String country);
}
