package com.grande.taxiapp.scheduler;

import com.grande.taxiapp.domain.CurrencyRates;
import com.grande.taxiapp.domain.FuelPrice;
import com.grande.taxiapp.foreignAPI.exchangeRates.CurrencyDto;
import com.grande.taxiapp.foreignAPI.fuelPrice.FuelPriceDto;
import com.grande.taxiapp.foreignAPI.fuelPrice.FuelPriceListDto;
import com.grande.taxiapp.foreignAPI.exchangeRates.CurrencyExchangeClient;
import com.grande.taxiapp.foreignAPI.exchangeRates.CurrencyRepository;
import com.grande.taxiapp.foreignAPI.fuelPrice.FuelPriceClient;
import com.grande.taxiapp.foreignAPI.fuelPrice.FuelPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
@Transactional
public class ScheduledEvents {
    public static final String POLAND ="Poland";
    public static final String EURO = "euro";

    @Autowired
    private CurrencyExchangeClient currencyExchangeClient;
    @Autowired
    private CurrencyRepository currencyRepository;
    @Autowired
    private FuelPriceClient fuelPriceClient;
    @Autowired
    private FuelPriceRepository fuelPriceRepository;

    @Scheduled(cron = "1 * * * * * ")
    public void updateCurrencyRate(){
        CurrencyDto currencyDto = currencyExchangeClient.getCurrentCurrencyRate();
        BigDecimal value = currencyDto.getRates().get(0).getValue();
        Optional<CurrencyRates> euro = currencyRepository.findByCurrency(EURO);
        if (euro.isEmpty()) {
            CurrencyRates currencyRates = new CurrencyRates(currencyDto.getCurrency(), currencyDto.getRates().get(0).getValue());
            currencyRepository.save(currencyRates);
        }else{
            currencyRepository.save(new CurrencyRates(
                    euro.get().getId(),
                    euro.get().getCurrency(),
                    value,
                    LocalDateTime.now()));
        }
    }

    @Scheduled(cron = "15 * * * * *")
    public void updateFuelPrice(){
        FuelPriceListDto fuelPriceListDto = fuelPriceClient.getFuelPrice();
        FuelPriceDto fuelPriceDto = fuelPriceListDto.getList().stream()
                .filter(x->x.getCountry().equalsIgnoreCase(POLAND))
                .findFirst().get();
        Optional<FuelPrice> price = fuelPriceRepository.findFuelPriceByCountry(POLAND);
        BigDecimal value = new BigDecimal(fuelPriceDto.getPrice().replace(",","."));
        if (price.isEmpty()){
            FuelPrice fuelPrice = new FuelPrice(
                    fuelPriceDto.getCurrency(),
                    fuelPriceDto.getCountry(),
                    value,
                    LocalDateTime.now());
            fuelPriceRepository.save(fuelPrice);
        }else{
            fuelPriceRepository.save(new FuelPrice(
                    price.get().getId(),
                    price.get().getCurrency(),
                    price.get().getCountry(),
                    value,
                    LocalDateTime.now()));
        }
    }
}
