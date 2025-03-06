package org.example.kolesnikovsport_shop.controller;

import org.example.kolesnikovsport_shop.service.CustomerService;
import org.example.kolesnikovsport_shop.service.FormService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class MenuFormController implements Initializable {

    private final FormService formService;

    @FXML
    private Menu menuAdministrator; // привязка к fx:id="menuAdministrator"

    public MenuFormController(FormService formService) {
        this.formService = formService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Проверяем, вошёл ли текущий пользователь и есть ли у него роль ADMINISTRATOR
        if (CustomerService.currentCustomer == null ||
                !CustomerService.currentCustomer.getRoles().contains("ADMINISTRATOR")) {
            menuAdministrator.setVisible(false);
        }
    }

    @FXML
    private void showEquipmentForm() {
        formService.loadNewEquipmentForm();
    }

    @FXML
    private void showEquipmentList() {
        formService.loadEquipmentListForm();
    }

    @FXML
    private void showSupplierForm() {
        formService.loadSupplierForm();
    }

    @FXML
    private void showSupplierList() {
        formService.loadSupplierListForm();
    }

    @FXML
    private void showNewCustomerForm() {
        formService.loadNewCustomerForm();
    }

    @FXML
    private void showCustomerListForm() {
        formService.loadCustomerListForm();
    }

    @FXML
    private void someAdminFunction() {
        System.out.println("Админская функция!");
    }

    @FXML
    private void showLoginForm() {
        formService.loadLoginForm();
    }

    @FXML
    private void logout() {
        CustomerService.currentCustomer = null;
        formService.loadLoginForm();
    }

    // Новые пункты меню для покупки, дохода и рейтинга товаров:
    @FXML
    private void showPurchaseForm() {
        formService.loadPurchaseForm();
    }

    @FXML
    private void showIncomeForm() {
        formService.loadIncomeForm();
    }

    @FXML
    private void showRatingForm() {
        formService.loadRatingForm();
    }
}
