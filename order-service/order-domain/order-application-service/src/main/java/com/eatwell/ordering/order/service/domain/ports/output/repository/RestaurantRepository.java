package com.eatwell.ordering.order.service.domain.ports.output.repository;

import com.eatwell.ordering.order.service.domain.entity.restaurant.Restaurant;

import java.util.Optional;

public interface RestaurantRepository {

    Optional<Restaurant> findRestaurantInformation(Restaurant restaurant);
}
