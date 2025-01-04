package com.eurekaclient.eurekaclientpkg.controller;

import com.eurekaclient.eurekaclientpkg.model.Car;
import com.eurekaclient.eurekaclientpkg.model.CarPart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class CarController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/parts")
    public Car getCarParts() {
        // Get the service instance from Eureka
        List<ServiceInstance> instances = discoveryClient.getInstances("PPM_TOOL_SERVICE");
        if (instances == null || instances.isEmpty()) {
            throw new RuntimeException("PPM_TOOL_SERVICE is not available");
        }
        String serviceUrl = instances.get(0).getUri().toString();

        // Call the external API to get the car parts
        CarPart[] carParts = restTemplate.getForObject(serviceUrl + "/eureka/carparts?model=toyota", CarPart[].class);

        // Create a car model
        Car car = new Car();
        car.setMake("Toyota");
        car.setModel("Camry");
        car.setYear(2022);

        // Set the parts in the car model
        car.setParts(List.of(carParts));

        return car;
    }
}