package com.piggy.pgb.service;

import com.piggy.pgb.entity.CustomerDetails;
import com.piggy.pgb.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Autowired
    private CustomerRepository custRepo;

    @Override
    public Optional<List<CustomerDetails>> getCustomerDetails() {
        LOGGER.info("Fetching All Customer Details from DB Start");
        return Optional.of(custRepo.findAll());
    }

    @Override
    @Cacheable(cacheNames = "custDetails", key = "#id")
    public Optional<CustomerDetails> getCustomerDetailsById(Integer id) {
        LOGGER.info("Fetching Customer Details by Id {} :", id);
        if (custRepo.findById(id).isEmpty()){
            return Optional.empty();
        }else{
            return custRepo.findById(id);
        }
    }

    @Override
    public CustomerDetails addCustomerDetails(CustomerDetails details) {
        LOGGER.info("Adding New Customer Details in Database {} {} {}:", details.getId(), details.getName(), details.getBalance());
        return custRepo.save(details);
    }

    @Override
    @CachePut(cacheNames = "custDetails", key = "#details.id")
    public CustomerDetails updateCustomerDetails(CustomerDetails details) {
        LOGGER.info("Updating Customer Details in Database {} {} {}:", details.getId(), details.getName(), details.getBalance());
        custRepo.updateCustomerDetails(details.getId(), details.getBalance());
        return details;
    }

    @Override
    public void deleteCustomerDetails(Integer id) {
        LOGGER.info("Deleting Customer Information from Database {}", id);
        CustomerDetails customerDetails = custRepo.getById(id);
        custRepo.deleteMobileDetails(customerDetails.getId());
    }
}
