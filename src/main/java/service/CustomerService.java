package service;

import entity.Account;
import entity.Customer;
import lombok.AllArgsConstructor;
import repository.CustomerRepository;

import java.util.List;

@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Customer getCustomer(int id) {
        return customerRepository.getCustomer(id);
    }

    public void saveCustomer(Customer customer) {
        customerRepository.saveCustomer(customer);
    }

    public void deleteCustomer(Integer customerId) {
        customerRepository.deleteCustomer(customerId);
    }

    public List<Customer> findDebtors() {
        return customerRepository.findDebtors();
    }
    public void deleteEveryCustomerAccount(Integer customerId) {
        customerRepository.deleteEveryCustomerAccount(customerId);
    }


}
