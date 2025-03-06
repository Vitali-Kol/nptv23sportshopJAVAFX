package org.example.kolesnikovsport_shop.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.kolesnikovsport_shop.model.entity.Customer;
import org.example.kolesnikovsport_shop.service.CustomerService;
import org.example.kolesnikovsport_shop.service.FormService;
import org.springframework.stereotype.Component;

@Component
public class NewCustomerFormController {

    private final CustomerService customerService;
    private final FormService formService;

    // Поля, соответствующие fx:id в newCustomerForm.fxml
    @FXML
    private TextField tfFirstname;

    @FXML
    private TextField tfLastname;

    @FXML
    private TextField tfUsername;

    @FXML
    private PasswordField pfPassword;

    // (Если хотите хранить баланс, раскомментируйте)
    // @FXML
    // private TextField tfBalance;

    public NewCustomerFormController(CustomerService customerService, FormService formService) {
        this.customerService = customerService;
        this.formService = formService;
    }

    /**
     * Метод вызывается при нажатии на кнопку "Создать".
     */
    @FXML
    private void createCustomer() {
        try {
            // Проверка на пустые поля
            if (tfFirstname.getText().trim().isEmpty() ||
                    tfLastname.getText().trim().isEmpty() ||
                    tfUsername.getText().trim().isEmpty() ||
                    pfPassword.getText().trim().isEmpty()) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setHeaderText("Пустые поля");
                alert.setContentText("Пожалуйста, заполните все поля!");
                alert.showAndWait();
                return;
            }

            // Создаём нового покупателя
            Customer customer = new Customer();
            customer.setFirstname(tfFirstname.getText().trim());
            customer.setLastname(tfLastname.getText().trim());
            customer.setUsername(tfUsername.getText().trim());
            customer.setPassword(pfPassword.getText().trim());
            // customer.setBalance(Double.parseDouble(tfBalance.getText())); // Если используете поле balance
            // Добавляем роль CUSTOMER
            customer.getRoles().add(CustomerService.ROLES.CUSTOMER.toString());

            // Сохраняем в БД
            customerService.add(customer);

            // Переходим, например, на главную форму или список покупателей
            formService.loadMainForm();

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Не удалось создать покупателя");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * Метод вызывается при нажатии на кнопку "Отмена".
     */
    @FXML
    private void cancel() {
        // Переход обратно на главную форму или другую форму по вашему желанию
        formService.loadMainForm();
    }
}
