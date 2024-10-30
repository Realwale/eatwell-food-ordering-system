package com.eatwell.ordering.order.service.dataaccess.customer.mapper;

import com.eatwell.ordering.domain.valueobject.CustomerId;
import com.eatwell.ordering.order.service.dataaccess.customer.entity.CustomerEntity;
import com.eatwell.ordering.order.service.domain.entity.customer.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerDataAccessMapper {

    public Customer customerEntityToCustomer(CustomerEntity customerEntity){
        return new Customer(new CustomerId(customerEntity.getId()));
    }
}
