package mkm.ecom.backend.service;

import mkm.ecom.backend.exception.ResourceNotFoundException;
import mkm.ecom.backend.model.Customer;
import mkm.ecom.backend.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    public List<Customer> getAllCustomers(){
        return (List<Customer>)customerRepository.findAll();

    }

    public Customer getCustomerById(Long id){
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer does not exist with id :" + id));
    }

    public Customer createCustomer(Customer customer){
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Customer customer, Long id){
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer does not exist with id :" + id));
        existingCustomer.setFirstName(customer.getFirstName());
        existingCustomer.setLastName(customer.getLastName());
        existingCustomer.setUserName(customer.getUserName());
        existingCustomer.setEmailId(customer.getEmailId());
        existingCustomer.setPhoneNo(customer.getPhoneNo());
        existingCustomer.setCity(customer.getCity());

        Customer updatedCustomer = customerRepository.save(existingCustomer);
        return existingCustomer;

    }

    public String deleteCustomer(Long id){
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer does not exist with id :" + id));
        customerRepository.delete(customer);
        return "Deleted";
    }
}
