package org.example.kolesnikovsport_shop.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.kolesnikovsport_shop.model.entity.Customer;
import org.example.kolesnikovsport_shop.service.CustomerService;
import org.example.kolesnikovsport_shop.service.FormService;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class ChangePasswordController implements Initializable {

    private final CustomerService customerService;
    private final FormService formService;

    public ChangePasswordController(CustomerService customerService, FormService formService) {
        this.customerService = customerService;
        this.formService = formService;
    }

    @FXML
    private ComboBox<Customer> cbUsers;
    @FXML
    private TextField tfUserId;
    @FXML
    private PasswordField pfNewPassword;
    @FXML
    private Label lblResult;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Если текущий пользователь – администратор, показываем ComboBox со всеми пользователями
        if (customerService.currentUserHasRole(CustomerService.ROLES.ADMINISTRATOR)) {
            List<Customer> allUsers = customerService.getAllCustomers();
            cbUsers.setItems(FXCollections.observableArrayList(allUsers));
            // Настройка отображения элементов ComboBox (только логин)
            cbUsers.setCellFactory(listView -> new ListCell<Customer>() {
                @Override
                protected void updateItem(Customer item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty || item == null ? null : item.getUsername());
                }
            });
            cbUsers.setButtonCell(new ListCell<Customer>() {
                @Override
                protected void updateItem(Customer item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty || item == null ? null : item.getUsername());
                }
            });
            tfUserId.setVisible(false);
        } else {
            cbUsers.setVisible(false);
            Customer current = customerService.getCurrentCustomer();
            if (current != null) {
                tfUserId.setText(current.getUsername());
            }
            tfUserId.setEditable(false);
            tfUserId.setVisible(true);
        }
    }

    @FXML
    private void handleChangePassword() {
        try {
            Long userId;
            if (customerService.currentUserHasRole(CustomerService.ROLES.ADMINISTRATOR)) {
                Customer selectedUser = cbUsers.getSelectionModel().getSelectedItem();
                if (selectedUser == null) {
                    lblResult.setText("Выберите пользователя для смены пароля.");
                    return;
                }
                userId = selectedUser.getId();
            } else {
                userId = customerService.getCurrentCustomer().getId();
            }
            String newPassword = pfNewPassword.getText().trim();
            if (newPassword.isEmpty()) {
                lblResult.setText("Введите новый пароль.");
                return;
            }
            Customer updatedUser = customerService.changePassword(userId, newPassword);
            lblResult.setText("Пароль успешно изменён для пользователя: " + updatedUser.getUsername());
        } catch (SecurityException se) {
            lblResult.setText(se.getMessage());
        } catch (Exception e) {
            lblResult.setText("Ошибка: " + e.getMessage());
        }
    }

    @FXML
    private void goToMainForm() {
        formService.loadMainForm();
    }
}
