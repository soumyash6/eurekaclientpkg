package com.eurekaclient.eurekaclientpkg.model;

import lombok.Data;
import java.util.List;

@Data
public class Car {
    private String make;
    private String model;
    private int year;
    private List<CarPart> parts;
}