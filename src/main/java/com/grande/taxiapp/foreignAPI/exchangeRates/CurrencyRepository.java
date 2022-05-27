package com.grande.taxiapp.foreignAPI.exchangeRates;

import com.grande.taxiapp.domain.CurrencyRates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurrencyRepository extends JpaRepository<CurrencyRates, Integer> {

    Optional<CurrencyRates> findByCurrency(String currency);
}
