package com.grande.taxiApp.scheduler;

import com.grande.taxiApp.domain.CurrencyRates;
import com.grande.taxiApp.domain.FuelPrice;
import com.grande.taxiApp.foreignApi.exchangeRates.CurrencyDto;
import com.grande.taxiApp.foreignApi.exchangeRates.CurrencyExchangeClient;
import com.grande.taxiApp.foreignApi.exchangeRates.CurrencyRepository;
import com.grande.taxiApp.foreignApi.exchangeRates.RatesDto;
import com.grande.taxiApp.foreignApi.fuelPrice.FuelPriceClient;
import com.grande.taxiApp.foreignApi.fuelPrice.FuelPriceDto;
import com.grande.taxiApp.foreignApi.fuelPrice.FuelPriceListDto;
import com.grande.taxiApp.foreignApi.fuelPrice.FuelPriceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

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