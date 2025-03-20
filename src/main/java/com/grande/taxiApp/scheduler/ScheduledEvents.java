package com.grande.taxiApp.scheduler;

import com.grande.taxiApp.domain.CurrencyRates;
import com.grande.taxiApp.domain.Customer;
import com.grande.taxiApp.domain.FuelPrice;
import com.grande.taxiApp.domain.OrderTaxi;
import com.grande.taxiApp.enums.OrderTaxiStatus;
import com.grande.taxiApp.foreignApi.exchangeRates.CurrencyDto;
import com.grande.taxiApp.foreignApi.fuelPrice.FuelPriceDto;
import com.grande.taxiApp.foreignApi.fuelPrice.FuelPriceListDto;
import com.grande.taxiApp.foreignApi.exchangeRates.CurrencyExchangeClient;
import com.grande.taxiApp.foreignApi.exchangeRates.CurrencyRepository;
import com.grande.taxiApp.foreignApi.fuelPrice.FuelPriceClient;
import com.grande.taxiApp.foreignApi.fuelPrice.FuelPriceRepository;
import com.grande.taxiApp.repository.CustomerRepository;
import com.grande.taxiApp.repository.OrderTaxiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Component
@Transactional
@RequiredArgsConstructor
public class ScheduledEvents implements CommandLineRunner {
    public static final String POLAND = "Poland";
    public static final String EURO = "euro";

    private final CurrencyExchangeClient currencyExchangeClient;
    private final CurrencyRepository currencyRepository;
    private final FuelPriceClient fuelPriceClient;
    private final FuelPriceRepository fuelPriceRepository;
    private final OrderTaxiRepository orderTaxiRepository;
    private final CustomerRepository customerRepository;
    private Random random = new Random();

    @Scheduled(cron = "* 1 * * * * ")
    public void updateCurrencyRate() {
        CurrencyDto currencyDto = currencyExchangeClient.getCurrentCurrencyRate();
        BigDecimal value = currencyDto.getRates().get(0).getValue();
        Optional<CurrencyRates> euro = currencyRepository.findByCurrency(EURO);
        if (euro.isEmpty()) {
            CurrencyRates currencyRates = new CurrencyRates(currencyDto.getCurrency(), new BigDecimal("4.5"));
            currencyRepository.save(currencyRates);
        } else {
            euro.get().setPrice(new BigDecimal("4.5"));
        }
    }

    @Scheduled(cron = "* 1 * * * *")
    public void updateFuelPrice() throws Exception {
        FuelPriceListDto fuelPriceListDto = fuelPriceClient.getFuelPrice();
        FuelPriceDto fuelPriceDto = fuelPriceListDto.getList().stream()
                .filter(x -> x.getCountry().equalsIgnoreCase(POLAND))
                .findFirst().get();
        if (Optional.of(fuelPriceDto).isEmpty()){
            throw new Exception("FuelPriceDto is empty");
        }
        Optional<FuelPrice> price = fuelPriceRepository.findFuelPriceByCountry(POLAND);
        BigDecimal value = new BigDecimal(fuelPriceDto.getPrice().replace(",", "."));
        if (price.isEmpty()) {
            FuelPrice fuelPrice = new FuelPrice(
                    fuelPriceDto.getCurrency(),
                    fuelPriceDto.getCountry(),
                    new BigDecimal("4.5"),
                    LocalDateTime.now());
            fuelPriceRepository.save(fuelPrice);
        } else {
            price.get().setPrice(new BigDecimal("4.5"));
        }
    }

    @Scheduled(cron = "* 1 * * * * ")
    public void updateBonus(){
        Map<Customer, Long> collect = orderTaxiRepository.findAll().stream().filter(x -> x.getStatus().equals(OrderTaxiStatus.FINISHED))
                .collect(Collectors.groupingBy(OrderTaxi::getCustomer, Collectors.counting()));
        for (Map.Entry<Customer, Long> x: collect.entrySet()){
            if (randomizer()) {
                x.getKey().setDiscount(discount());
            }
        }
    }
    private boolean randomizer(){
        int ran = random.nextInt(10);
        return (ran == 2 || ran == 4 || ran ==6);
    }
    private double discount(){
        int i = random.nextInt(65,95);
        return (double) i/100;

    }

    @Override
    public void run(String... args) throws Exception {
        if (currencyRepository.findAll().isEmpty()){
            currencyRepository.save(
                    new CurrencyRates(1,"euro",BigDecimal.valueOf(4.6),LocalDateTime.now())
            );
        }
        if (fuelPriceRepository.findFuelPriceByCountry(POLAND).isEmpty()){
            fuelPriceRepository.save(
                    new FuelPrice(1,"euro",POLAND,BigDecimal.valueOf(1.65),
                            LocalDateTime.now())
            );
        }
    }
}
