package com.grande.taxiapp.facade;

import com.grande.taxiapp.domain.Customer;
import com.grande.taxiapp.domain.Driver;
import com.grande.taxiapp.domain.OrderTaxi;
import com.grande.taxiapp.domain.dto.MailDto;
import com.grande.taxiapp.domain.dto.OrderTaxiDto;
import com.grande.taxiapp.enums.DriverStatus;
import com.grande.taxiapp.enums.OrderTaxiStatus;
import com.grande.taxiapp.exceptions.NumberOfActiveOrdersException;
import com.grande.taxiapp.foreignAPI.addressToCoordinates.CoordinatesClient;
import com.grande.taxiapp.foreignAPI.addressToCoordinates.CoordinatesDto;
import com.grande.taxiapp.foreignAPI.distanceAndTime.DistanceAndDurationClient;
import com.grande.taxiapp.foreignAPI.distanceAndTime.DistanceAndDurationDto;
import com.grande.taxiapp.repository.CustomerRepository;
import com.grande.taxiapp.repository.DriverRepository;
import com.grande.taxiapp.service.EmailService;
import com.grande.taxiapp.service.OrderTaxiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
@Slf4j
@Service
public class OrderFacade {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CoordinatesClient coordinatesClient;
    @Autowired
    private DistanceAndDurationClient distanceAndDurationClient;
    @Autowired
    private OrderTaxiService orderTaxiService;
    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private EmailService emailService;


    public OrderTaxi createOrder(OrderTaxiDto orderTaxiDto) throws NumberOfActiveOrdersException {
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
                if (activeDrivers.size() > 0) {
                    Random random = new Random();
                    Driver driver = activeDrivers.get(random.nextInt(activeDrivers.size()));
                    driverRepository.save(new Driver(
                            driver.getId(),
                            driver.getName(),
                            driver.getSurname(),
                            driver.getPhoneNumber(),
                            driver.getEmail(),
                            DriverStatus.BUSY,
                            driver.getCar()
                    ));

                    OrderTaxi created = new OrderTaxi(orderTaxiDto.getId(), pickUpPlace.getAddress(), dropPlace.getAddress(),
                            tripCost, time, customer.get(), driver);
                    orderTaxiService.save(created);
                    emailService.send(new MailDto(created.getCustomer().getEmail(),
                            "New order for taxi",
                            "Hello "+created.getCustomer().getName()+", you just ordered a taxi. \n" +
                                    "PickUp place: "+created.getPickUpPlace() +"\n" +
                                    "Drop place: "+created.getDropPlace()+"\n" +
                                    "Cost: "+created.getEstimatedCost()+"\n" +
                                    "Duration: "+created.getEstimatedTime().toString()+"\n" +
                                    "Your driver will be: "+created.getDriver().getName()+"\n" +
                                    "WE WISH YOU A PLEASANT TRIP"));
                    customerRepository.save(new Customer(customer.get().getId(),
                            customer.get().getName(),customer.get().getSurname(),customer.get().getUsername(),
                            customer.get().getPhoneNumber(),customer.get().getEmail(),1.0));

                    return created;
                }
            }else{
                log.error("Too many ACTIVE orders");
                throw new NumberOfActiveOrdersException();
            }

        }
        return null;
    }
}
