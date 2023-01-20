package com.grande.taxiApp.facade;

import com.grande.taxiApp.domain.Customer;
import com.grande.taxiApp.domain.Driver;
import com.grande.taxiApp.domain.OrderTaxi;
import com.grande.taxiApp.domain.dto.OrderTaxiDto;
import com.grande.taxiApp.domain.dto.OrderTaxiFullDto;
import com.grande.taxiApp.enums.DriverStatus;
import com.grande.taxiApp.enums.OrderTaxiStatus;
import com.grande.taxiApp.exceptions.NumberOfActiveOrdersException;
import com.grande.taxiApp.foreignApi.addressToCoordinates.CoordinatesClient;
import com.grande.taxiApp.foreignApi.addressToCoordinates.CoordinatesDto;
import com.grande.taxiApp.foreignApi.distanceAndTime.DistanceAndDurationClient;
import com.grande.taxiApp.foreignApi.distanceAndTime.DistanceAndDurationDto;
import com.grande.taxiApp.mappers.OrderTaxiMapper;
import com.grande.taxiApp.repository.CustomerRepository;
import com.grande.taxiApp.repository.DriverRepository;
import com.grande.taxiApp.service.EmailService;
import com.grande.taxiApp.service.OrderTaxiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderFacade {
    private final CustomerRepository customerRepository;
    private final CoordinatesClient coordinatesClient;
    private final DistanceAndDurationClient distanceAndDurationClient;
    private final OrderTaxiService orderTaxiService;
    private final DriverRepository driverRepository;
    private final EmailService emailService;
    private final OrderTaxiMapper mapper;
    private Random random = new Random();


    public OrderTaxiFullDto createOrder(OrderTaxiDto orderTaxiDto) throws NumberOfActiveOrdersException {
        log.info("Process of creating new order started");
        Optional<Customer> customer = customerRepository.findById(orderTaxiDto.getCustomer().getId());
        if (customer.isPresent()){
            long count = orderTaxiService.findByCustomerId(customer.get().getId()).stream()
                    .filter(x->x.getStatus().equals(OrderTaxiStatus.ACTIVE))
                    .count();
            if (count == 0) {

                CoordinatesDto pickUpPlace = coordinatesClient.getCoordinates(orderTaxiDto.getPickUpPlace());
                CoordinatesDto dropPlace = coordinatesClient.getCoordinates(orderTaxiDto.getDropPlace());
                DistanceAndDurationDto data = distanceAndDurationClient.getData(pickUpPlace, dropPlace);

                List<Long> distance = data.getDistance().stream().findFirst().get();

                List<Long> duration = data.getDurationInSeconds().stream().findFirst().get();
                BigDecimal tripCost = (orderTaxiService.countTripPrice(distance.get(0) / 1000)).multiply(BigDecimal.valueOf(customer.get().getDiscount()))
                        .setScale(2, RoundingMode.CEILING);
                LocalTime time = LocalTime.ofSecondOfDay(duration.get(0));

                List<Driver> activeDrivers = driverRepository.findByStatus(DriverStatus.ACTIVE);
                if (!activeDrivers.isEmpty()) {
                    Driver driver = activeDrivers.get(random.nextInt(activeDrivers.size()));
                    driver.setStatus(DriverStatus.BUSY);

                    OrderTaxi created = new OrderTaxi(orderTaxiDto.getId(), pickUpPlace.getAddress(), dropPlace.getAddress(),
                            tripCost, time, customer.get(), driver);
                    orderTaxiService.save(created);
                    emailService.send(emailService.emailPatternAfterOrdering(created));


                    return mapper.mapToOrderTaxiFullDto(created);
                }
            }else{
                log.error("Too many ACTIVE orders");
                throw new NumberOfActiveOrdersException();
            }

        }
        return null;
    }
}
