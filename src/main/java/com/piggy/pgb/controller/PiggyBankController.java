package com.piggy.pgb.controller;

import com.google.gson.Gson;
import com.piggy.pgb.entity.CustomerDetails;
import com.piggy.pgb.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jettison.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pgb")
@CrossOrigin
@Slf4j
public class PiggyBankController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PiggyBankController.class);
    private Gson gson;
    private CustomerService customerService;

    @Autowired
    public PiggyBankController(Gson gson, CustomerService customerService) {
        this.gson = gson;
        this.customerService = customerService;
    }

    /**
     * Endpoint for Getting All the Customer Details
     * @return
     * @throws IOException
     * @throws JSONException
     */
    @GetMapping(value = "/customerDetails", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> getCustomerDetails()
            throws IOException, JSONException {
        LOGGER.info("Calling ServiceImpl for fetching all CustomerDetails");
        Optional<List<CustomerDetails>> customerDetails = customerService.getCustomerDetails();
        return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(customerDetails));
    }

    /**
     * EndPoint for getting Customer Details based upon Id
     * @param id
     * @return
     * @throws IOException
     * @throws JSONException
     */
    @GetMapping(value = "/customerDetails/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> getCustomerDetailsById(@PathVariable("id") String id)
            throws IOException, JSONException {
        LOGGER.info("Calling ServiceImpl for fetching data for a particular customer");
        if (customerService.getCustomerDetailsById(Integer.parseInt(id)).isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Please Provide Correct Customer ID");
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(gson.toJson(customerService.getCustomerDetailsById(Integer.parseInt(id))));
        }
    }

    /**
     * Endpoint for adding new Customer Details
     * @param details
     * @return
     * @throws IOException
     * @throws JSONException
     */
    @PostMapping(value = "/addCustomer", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> addCustomerDetails(@RequestBody CustomerDetails details)
            throws IOException, JSONException{
        LOGGER.info("Calling ServiceImpl for adding new Customer Data in the database");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(gson.toJson(customerService.addCustomerDetails(details)));
    }

    /**
     * Endpoint for Updating Existing Customer Details
     * @param details
     * @return
     * @throws IOException
     * @throws JSONException
     */
    @PutMapping(value = "/updateCustomer", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> updateCustomerDetails(@RequestBody CustomerDetails details)
            throws IOException, JSONException{
        LOGGER.info("Calling Service Impl for Updating Existing Customer Details");
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(gson.toJson(customerService.updateCustomerDetails(details)));
    }

    /**
     * Endpoint for Deleting Existing Customer Details
     * @param id
     * @return
     * @throws IOException
     * @throws JSONException
     */
    @DeleteMapping(value = "/deleteCustomer/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> deleteCustomerDetails(@PathVariable String id) throws IOException, JSONException{
        LOGGER.info("Deleting Customer Details from DB");
       if ( customerService.getCustomerDetailsById(Integer.parseInt(id)).isEmpty()){
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                   .body("Please Enter a Valid Customer Id");
       }else {
           customerService.deleteCustomerDetails(Integer.parseInt(id));
           return ResponseEntity.status(HttpStatus.OK)
                   .body("Customer Details Deleted Succesfully !");
       }
    }
}
