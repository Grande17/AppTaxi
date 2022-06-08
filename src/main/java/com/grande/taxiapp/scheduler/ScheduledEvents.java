package com.grande.taxiapp.scheduler;

import com.grande.taxiapp.domain.CurrencyRates;
import com.grande.taxiapp.domain.Customer;
import com.grande.taxiapp.domain.FuelPrice;
import com.grande.taxiapp.domain.OrderTaxi;
import com.grande.taxiapp.enums.OrderTaxiStatus;
import com.grande.taxiapp.foreignAPI.exchangeRates.CurrencyDto;
import com.grande.taxiapp.foreignAPI.fuelPrice.FuelPriceDto;
import com.grande.taxiapp.foreignAPI.fuelPrice.FuelPriceListDto;
import com.grande.taxiapp.foreignAPI.exchangeRates.CurrencyExchangeClient;
import com.grande.taxiapp.foreignAPI.exchangeRates.CurrencyRepository;
import com.grande.taxiapp.foreignAPI.fuelPrice.FuelPriceClient;
import com.grande.taxiapp.foreignAPI.fuelPrice.FuelPriceRepository;
import com.grande.taxiapp.repository.CustomerRepository;
import com.grande.taxiapp.repository.OrderTaxiRepository;
import com.grande.taxiapp.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Component
@Transactional
public class ScheduledEvents {
    public static final String POLAND = "Poland";
    public static final String EURO = "euro";

    @Autowired
    private CurrencyExchangeClient currencyExchangeClient;
    @Autowired
    private CurrencyRepository currencyRepository;
    @Autowired
    private FuelPriceClient fuelPriceClient;
    @Autowired
    private FuelPriceRepository fuelPriceRepository;
    @Autowired
    private OrderTaxiRepository orderTaxiRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerService customerService;
    private Random random = new Random();

    @Scheduled(cron = "1 * * * * * ")
    public void updateCurrencyRate() {
        CurrencyDto currencyDto = currencyExchangeClient.getCurrentCurrencyRate();
        BigDecimal value = currencyDto.getRates().get(0).getValue();
        Optional<CurrencyRates> euro = currencyRepository.findByCurrency(EURO);
        if (euro.isEmpty()) {
            CurrencyRates currencyRates = new CurrencyRates(currencyDto.getCurrency(), currencyDto.getRates().get(0).getValue());
            currencyRepository.save(currencyRates);
        } else {
            currencyRepository.save(new CurrencyRates(
                    euro.get().getId(),
                    euro.get().getCurrency(),
                    value,
                    LocalDateTime.now()));
        }
    }

    @Scheduled(cron = "15 * * * * *")
    public void updateFuelPrice() {
        FuelPriceListDto fuelPriceListDto = fuelPriceClient.getFuelPrice();
        FuelPriceDto fuelPriceDto = fuelPriceListDto.getList().stream()
                .filter(x -> x.getCountry().equalsIgnoreCase(POLAND))
                .findFirst().get();
        Optional<FuelPrice> price = fuelPriceRepository.findFuelPriceByCountry(POLAND);
        BigDecimal value = new BigDecimal(fuelPriceDto.getPrice().replace(",", "."));
        if (price.isEmpty()) {
            FuelPrice fuelPrice = new FuelPrice(
                    fuelPriceDto.getCurrency(),
                    fuelPriceDto.getCountry(),
                    value,
                    LocalDateTime.now());
            fuelPriceRepository.save(fuelPrice);
        } else {
            fuelPriceRepository.save(new FuelPrice(
                    price.get().getId(),
                    price.get().getCurrency(),
                    price.get().getCountry(),
                    value,
                    LocalDateTime.now()));
        }
    }

   // @Scheduled(cron = "* 1 * * * * ")
    public void updateBonus(){
        Map<Customer, Long> collect = orderTaxiRepository.findAll().stream().filter(x -> x.getStatus().equals(OrderTaxiStatus.FINISHED))
                .collect(Collectors.groupingBy(OrderTaxi::getCustomer, Collectors.counting()));
        for (Map.Entry<Customer, Long> x: collect.entrySet()){
            if (randomizer()) {
                customerRepository.save(new Customer(x.getKey().getId(),
                        x.getKey().getName(), x.getKey().getSurname(), x.getKey().getSurname(),
                        x.getKey().getPassword(), x.getKey().getPhoneNumber(), x.getKey().getEmail(), discount()));
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
}
