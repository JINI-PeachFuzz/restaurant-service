package org.jiniit.restaurant.controllers;

import lombok.Data;

import java.util.List;

@Data
public class RestaurantSearch {
    private List<Long> seq;
    private String sido;
    private String sigugun;
    private List<String> category;
    private String sort; // name_DESC, address_DESC
}