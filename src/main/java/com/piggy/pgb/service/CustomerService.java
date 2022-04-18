package com.piggy.pgb.service;

import com.piggy.pgb.entity.CustomerDetails;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    Optional<List<CustomerDetails>> getCustomerDetails();

    Optional<CustomerDetails> getCustomerDetailsById(Integer id);

    CustomerDetails addCustomerDetails(CustomerDetails details);

    CustomerDetails updateCustomerDetails(CustomerDetails details);

    void deleteCustomerDetails(Integer id);
}
