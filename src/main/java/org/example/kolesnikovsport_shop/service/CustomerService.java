package org.example.kolesnikovsport_shop.service;

import org.example.kolesnikovsport_shop.model.entity.Customer;
import org.example.kolesnikovsport_shop.model.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    public static Customer currentCustomer;
    public enum ROLES { CUSTOMER, MANAGER, ADMINISTRATOR }
    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
        initSuperUser();
    }

    private void initSuperUser() {
        if (repository.count() > 0) {
            return;
        }
        Customer admin = new Customer();
        admin.setUsername("admin");
        admin.setPassword("12345");
        admin.setFirstname("Admin");
        admin.setLastname("SuperAdmin");
        // Устанавливаем баланс для администратора, например, 0.0
        admin.setBalance(0.0);
        admin.getRoles().add(ROLES.ADMINISTRATOR.toString());
        admin.getRoles().add(ROLES.CUSTOMER.toString());
        admin.getRoles().add(ROLES.MANAGER.toString());
        repository.save(admin);
    }

    public void add(Customer customer) {
        repository.save(customer);
    }

    // Метод для обновления данных покупателя
    public Customer update(Customer customer) {
        return repository.save(customer);
    }

    // Метод для получения списка всех покупателей
    public List<Customer> getAllCustomers() {
        return repository.findAll();
    }

    // Метод для поиска покупателя по ID
    public Optional<Customer> findById(Long id) {
        return repository.findById(id);
    }

    public boolean authenticate(String username, String password) {
        Optional<Customer> optionalCustomer = repository.findByUsername(username);
        if (optionalCustomer.isEmpty()) {
            return false;
        }
        Customer loginCustomer = optionalCustomer.get();
        if (!loginCustomer.getPassword().equals(password)) {
            return false;
        }
        currentCustomer = loginCustomer;
        return true;
    }
}
