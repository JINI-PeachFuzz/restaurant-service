package org.jiniit.restaurant.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor @AllArgsConstructor
public class Restaurant {
    @Id
    private Long seq;

    @Column(length=40)
    private String category;
    private String address;
    private String name; // 식당명
    private double latitude; // 위도  // 34.8264515
    private double longitude; // 경도  // 128.400468
}