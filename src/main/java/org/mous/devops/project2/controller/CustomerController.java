package org.mous.devops.project2.controller;


import org.apache.coyote.BadRequestException;
import org.mous.devops.project2.exception.ResourceNotFoundException;
import org.mous.devops.project2.models.Customer;
import org.mous.devops.project2.repository.CustomerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        System.out.printf("Customer: %s\n", customers);
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable("id") Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id));
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) throws ResponseStatusException {
        try {
            if (customer.getName() == null || customer.getName().isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Customer name cannot be empty");
            }
            return new ResponseEntity<>(customerRepository.save(customer), HttpStatus.CREATED);
        } catch (Exception e) {
            if (e instanceof BadRequestException) {
                throw e;
            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Failed to create customer: " + e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable("id") Long id, @RequestBody Customer customerDetails) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id));

        if (customerDetails.getName() != null) {
            customer.setName(customerDetails.getName());
        }

        return new ResponseEntity<>(customerRepository.save(customer), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable("id") Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id));

        customerRepository.delete(customer);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}