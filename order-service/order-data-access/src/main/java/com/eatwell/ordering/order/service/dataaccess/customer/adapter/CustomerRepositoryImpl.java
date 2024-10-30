package com.eatwell.ordering.order.service.dataaccess.customer.adapter;

import com.eatwell.ordering.order.service.dataaccess.customer.mapper.CustomerDataAccessMapper;
import com.eatwell.ordering.order.service.dataaccess.customer.repository.CustomerJpaRepository;
import com.eatwell.ordering.order.service.domain.entity.customer.Customer;
import com.eatwell.ordering.order.service.domain.ports.output.repository.CustomerRepository;

import java.util.Optional;
import java.util.UUID;

public class CustomerRepositoryImpl implements CustomerRepository {

    private final CustomerJpaRepository customerJpaRepository;
    private final CustomerDataAccessMapper customerDataAccessMapper;

    public CustomerRepositoryImpl(CustomerJpaRepository customerJpaRepository,
                                  CustomerDataAccessMapper customerDataAccessMapper) {
        this.customerJpaRepository = customerJpaRepository;
        this.customerDataAccessMapper = customerDataAccessMapper;
    }

    @Override
    public Optional<Customer> findCustomer(UUID customerId) {
        return customerJpaRepository.findById(customerId)
                .map(customerDataAccessMapper::customerEntityToCustomer);
    }
}
