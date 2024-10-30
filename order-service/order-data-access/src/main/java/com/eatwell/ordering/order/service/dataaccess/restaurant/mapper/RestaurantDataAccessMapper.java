package com.eatwell.ordering.order.service.dataaccess.restaurant.mapper;

import com.eatwell.ordering.domain.valueobject.Money;
import com.eatwell.ordering.domain.valueobject.ProductId;
import com.eatwell.ordering.domain.valueobject.RestaurantId;
import com.eatwell.ordering.order.service.dataaccess.restaurant.entity.RestaurantEntity;
import com.eatwell.ordering.order.service.dataaccess.restaurant.exception.RestaurantDataAccessException;
import com.eatwell.ordering.order.service.domain.entity.Product;
import com.eatwell.ordering.order.service.domain.entity.restaurant.Restaurant;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class RestaurantDataAccessMapper {


    public List<UUID> restaurantToRestaurantProducts(Restaurant restaurant){
        return restaurant.getProducts().stream()
                .map(product -> product.getId().getValue())
                .collect(Collectors.toList());
    }

    public Restaurant restaurantEntityToRestaurant(List<RestaurantEntity> restaurantEntity){
        RestaurantEntity restaurant = restaurantEntity.stream()
                .findFirst().orElseThrow(() -> new RestaurantDataAccessException("Restaurant could not be found!"));

        List<Product> products = restaurantEntity.stream()
                .map(entity -> new Product(new ProductId(entity.getProductId()),
                        entity.getProductName(),
                        new Money(entity.getProductPrice())))
                .toList();

        return Restaurant.builder()
                .restaurantId(new RestaurantId(restaurant.getRestaurantId()))
                .products(products)
                .active(restaurant.getRestaurantActive())
                .build();
    }

}
