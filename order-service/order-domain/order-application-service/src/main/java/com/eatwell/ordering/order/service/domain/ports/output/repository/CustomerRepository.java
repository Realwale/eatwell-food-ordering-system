package com.eatwell.ordering.order.service.domain.ports.output.repository;

import com.eatwell.ordering.order.service.domain.entity.customer.Customer;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {

    Optional<Customer> findCustomer(UUID customerId);
}
