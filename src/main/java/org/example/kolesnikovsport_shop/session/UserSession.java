package org.example.kolesnikovsport_shop.session;

import org.example.kolesnikovsport_shop.model.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class UserSession {
    private Customer currentCustomer;

    public Customer getCurrentCustomer() {
        return currentCustomer;
    }

    public void setCurrentCustomer(Customer currentCustomer) {
        this.currentCustomer = currentCustomer;
    }
}
