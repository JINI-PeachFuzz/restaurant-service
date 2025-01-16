package org.jiniit.restaurant.repositories;

import org.jiniit.restaurant.entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long>, QuerydslPredicateExecutor<Restaurant> {
}