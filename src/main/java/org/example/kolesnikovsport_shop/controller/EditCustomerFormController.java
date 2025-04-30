package org.example.kolesnikovsport_shop.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.example.kolesnikovsport_shop.model.entity.Customer;
import org.example.kolesnikovsport_shop.service.CustomerService;
import org.example.kolesnikovsport_shop.service.FormService;
import org.springframework.stereotype.Component;

@Component
public class EditCustomerFormController {

    public TextField firstnameField;
    public TextField lastnameField;
    public TextField usernameField;
    public TextField balanceField;

    private final CustomerService customerService;
    private final FormService formService;
    private Customer customer;

    public EditCustomerFormController(CustomerService customerService, FormService formService) {
        this.customerService = customerService;
        this.formService = formService;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
        // Проверяем, имеет ли текущий пользователь права на редактирование (через инстанс customerService)
        if (!customerService.currentUserHasRole(CustomerService.ROLES.ADMINISTRATOR)) {
            showError("У вас нет прав на редактирование покупателя!");
            formService.loadCustomerListForm();
            return;
        }
        // Заполняем поля
        firstnameField.setText(customer.getFirstname());
        lastnameField.setText(customer.getLastname());
        usernameField.setText(customer.getUsername());
        balanceField.setText(String.valueOf(customer.getBalance()));
    }

    public void saveCustomer() {
        if (customer != null) {
            customer.setFirstname(firstnameField.getText());
            customer.setLastname(lastnameField.getText());
            customer.setUsername(usernameField.getText());
            try {
                double balance = Double.parseDouble(balanceField.getText());
                customer.setBalance(balance);
            } catch (NumberFormatException e) {
                showError("Баланс должен быть числом!");
                return;
            }
            try {
                customerService.update(customer);
                showSuccess("Покупатель успешно обновлен!");
                formService.loadCustomerListForm();
            } catch (Exception e) {
                showError("Ошибка обновления: " + e.getMessage());
            }
        }
    }

    public void cancelEdit() {
        formService.loadCustomerListForm();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText("Что-то пошло не так");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Успех");
        alert.setHeaderText("Операция выполнена успешно");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
