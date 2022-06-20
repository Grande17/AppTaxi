package com.grande.taxiapp.scheduler;

import com.grande.taxiapp.domain.CurrencyRates;
import com.grande.taxiapp.domain.FuelPrice;
import com.grande.taxiapp.foreignAPI.exchangeRates.CurrencyDto;
import com.grande.taxiapp.foreignAPI.exchangeRates.CurrencyExchangeClient;
import com.grande.taxiapp.foreignAPI.exchangeRates.CurrencyRepository;
import com.grande.taxiapp.foreignAPI.exchangeRates.RatesDto;
import com.grande.taxiapp.foreignAPI.fuelPrice.FuelPriceClient;
import com.grande.taxiapp.foreignAPI.fuelPrice.FuelPriceDto;
import com.grande.taxiapp.foreignAPI.fuelPrice.FuelPriceListDto;
import com.grande.taxiapp.foreignAPI.fuelPrice.FuelPriceRepository;
import com.grande.taxiapp.repository.CustomerRepository;
import com.grande.taxiapp.repository.OrderTaxiRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class ScheduledEventsTest {
    @Mock
    private CurrencyExchangeClient currencyExchangeClient;
    @Mock
    private CurrencyRepository currencyRepository;
    @Mock
    private FuelPriceClient fuelPriceClient;
    @Mock
    private FuelPriceRepository fuelPriceRepository;
    @InjectMocks
    private ScheduledEvents scheduledEvents;

    @Test
    void updateCurrencyRate() {
        RatesDto ratesDto = new RatesDto(BigDecimal.valueOf(4.6));
        CurrencyDto currencyDto = new CurrencyDto("euro","EUR", List.of(ratesDto));
        when(currencyExchangeClient.getCurrentCurrencyRate()).thenReturn(currencyDto);
        when(currencyRepository.findByCurrency(any())).thenReturn(Optional.empty());
        when(currencyRepository.save(any())).thenReturn(null);

        scheduledEvents.updateCurrencyRate();
        verify(currencyRepository,times(1)).save(any(CurrencyRates.class));
    }

    @Test
    void updateFuelPrice() {
        FuelPriceDto fuelPriceDto = new FuelPriceDto("test","1.65","Poland");
        FuelPriceListDto fuelPriceListDto = new FuelPriceListDto(List.of(fuelPriceDto));

        when(fuelPriceClient.getFuelPrice()).thenReturn(fuelPriceListDto);
        when(fuelPriceRepository.findFuelPriceByCountry(any())).thenReturn(Optional.empty());
        when(fuelPriceRepository.save(any())).thenReturn(null);

        scheduledEvents.updateFuelPrice();
        verify(fuelPriceRepository,times(1)).save(any(FuelPrice.class));

    }
}