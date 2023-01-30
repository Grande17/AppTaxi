package com.grande.taxiApp;

import com.grande.taxiApp.domain.Car;
import com.grande.taxiApp.domain.Customer;
import com.grande.taxiApp.domain.Driver;
import com.grande.taxiApp.domain.dto.CarDto;
import com.grande.taxiApp.domain.dto.CustomerDto;
import com.grande.taxiApp.domain.dto.DriverDto;
import com.grande.taxiApp.enums.DriverStatus;

import java.util.Arrays;
import java.util.List;

public class Exes {

    public static CarDto carDto = new CarDto(1,"test","test","test","test");
    public static Car car = new Car("test","test","test","test");
    public static List<CarDto> listCarDto = List.of(
            new CarDto(1,"test1","test1","test1","test1"),
            new CarDto(2,"test2","test2","test2","test2")
    );
    public static List<Car> carList = List.of(
            new Car("test1","test1","test1","test1"),
            new Car("test2","test2","test2","test2")
    );
    public static CustomerDto customerDto = new CustomerDto(1,"test","test","test","test","test");
    public static Customer customer = new Customer("test","test","test","test","test");
    public static List<CustomerDto> customerDtoList = List.of(
            new CustomerDto(1,"test","test","test","test","test"),
            new CustomerDto(2,"test","test","test","test","test")
    );
    public static List<Customer> customerList = List.of(
            new Customer("test","test","test","test","test"),
            new Customer("test","test","test","test","test")
    );
    public static DriverDto driverDto = new DriverDto(1,"test","test","test","test", DriverStatus.ACTIVE,
            new Car(1,"test","test","test","test"));
    public static Driver driver = new Driver(1,"test","test","test","test", DriverStatus.ACTIVE,
            new Car(1,"test","test","test","test"));
    public static List<DriverDto> driversDtoList = List.of(
            new DriverDto(1,"test","test","test","test", DriverStatus.ACTIVE,
                    new Car(1,"test","test","test","test")),
            new DriverDto(2,"test","test","test","test", DriverStatus.ACTIVE,
                    new Car(2,"test","test","test","test")));
    public static List<Driver> driversList = Arrays.asList(
            new Driver(1,"test","test","test","test", DriverStatus.ACTIVE,
                    new Car(1,"test","test","test","test")),
            new Driver(2,"test","test","test","test", DriverStatus.ACTIVE,
                    new Car(2,"test","test","test","test")));
}
