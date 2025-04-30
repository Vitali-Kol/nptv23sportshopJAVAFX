package org.example.kolesnikovsport_shop.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import org.example.kolesnikovsport_shop.service.CustomerService;
import org.example.kolesnikovsport_shop.service.FormService;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class MenuFormController implements Initializable {

    private final FormService formService;
    private final CustomerService customerService;

    @FXML private Menu menuAdministrator;
    @FXML private Menu menuEquipment;
    @FXML private Menu menuSuppliers;
    @FXML private Menu menuCustomers;

    // Команды списка уже привязаны через FXML, здесь только для примера:
    @FXML private MenuItem miListEquipment;
    @FXML private MenuItem miListSuppliers;
    @FXML private MenuItem miListCustomers;

    public MenuFormController(FormService formService, CustomerService customerService) {
        this.formService = formService;
        this.customerService = customerService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        boolean isManagerOrAdmin = customerService.currentUserHasAnyRole(
                CustomerService.ROLES.MANAGER,
                CustomerService.ROLES.ADMINISTRATOR);

        // Прячет раздел Administrator, если не админ
        menuAdministrator.setVisible(
                customerService.currentUserHasRole(CustomerService.ROLES.ADMINISTRATOR)
        );

        // Прячет разделы «Оборудование», «Поставщики», «Покупатели» для чистого CUSTOMER
        menuEquipment.setVisible(isManagerOrAdmin);
        menuSuppliers.setVisible(isManagerOrAdmin);
        menuCustomers.setVisible(isManagerOrAdmin);
    }

    private void showAccessDeniedAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Доступ запрещён");
        alert.setHeaderText("Недостаточно прав");
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML private void showEquipmentForm() {
        if (!customerService.currentUserHasAnyRole(
                CustomerService.ROLES.MANAGER,
                CustomerService.ROLES.ADMINISTRATOR)) {
            showAccessDeniedAlert("У вас нет прав на добавление оборудования!");
            return;
        }
        formService.loadNewEquipmentForm();
    }

    @FXML private void showEquipmentList() {
        // теперь скачивается только если manager/admin
        if (!customerService.currentUserHasAnyRole(
                CustomerService.ROLES.MANAGER,
                CustomerService.ROLES.ADMINISTRATOR)) {
            showAccessDeniedAlert("Вам недоступен просмотр списка оборудования!");
            return;
        }
        formService.loadEquipmentListForm();
    }

    @FXML private void showSupplierForm() {
        if (!customerService.currentUserHasAnyRole(
                CustomerService.ROLES.MANAGER,
                CustomerService.ROLES.ADMINISTRATOR)) {
            showAccessDeniedAlert("Вам недоступно добавление поставщиков!");
            return;
        }
        formService.loadSupplierForm();
    }

    @FXML private void showSupplierList() {
        if (!customerService.currentUserHasAnyRole(
                CustomerService.ROLES.MANAGER,
                CustomerService.ROLES.ADMINISTRATOR)) {
            showAccessDeniedAlert("Вам недоступен просмотр списка поставщиков!");
            return;
        }
        formService.loadSupplierListForm();
    }

    @FXML private void showNewCustomerForm() {
        if (!customerService.currentUserHasAnyRole(
                CustomerService.ROLES.MANAGER,
                CustomerService.ROLES.ADMINISTRATOR)) {
            showAccessDeniedAlert("Вам недоступно добавление покупателей!");
            return;
        }
        formService.loadNewCustomerForm();
    }

    @FXML private void showCustomerListForm() {
        if (!customerService.currentUserHasAnyRole(
                CustomerService.ROLES.MANAGER,
                CustomerService.ROLES.ADMINISTRATOR)) {
            showAccessDeniedAlert("Вам недоступен просмотр списка покупателей!");
            return;
        }
        formService.loadCustomerListForm();
    }

    @FXML private void showPurchaseForm() {
        formService.loadPurchaseForm(); // обычно все могут покупать
    }

    @FXML private void showIncomeForm() {
        if (!customerService.currentUserHasAnyRole(
                CustomerService.ROLES.MANAGER,
                CustomerService.ROLES.ADMINISTRATOR)) {
            showAccessDeniedAlert("Вам недоступен просмотр дохода магазина!");
            return;
        }
        formService.loadIncomeForm();
    }

    @FXML private void showRatingForm() {
        if (!customerService.currentUserHasAnyRole(
                CustomerService.ROLES.MANAGER,
                CustomerService.ROLES.ADMINISTRATOR)) {
            showAccessDeniedAlert("Вам недоступен просмотр рейтинга товаров!");
            return;
        }
        formService.loadRatingForm();
    }

    @FXML private void someAdminFunction() {
        // админ-функция
    }

    @FXML private void showChangePasswordForm() {
        formService.loadChangePasswordForm();
    }

    @FXML private void logout() {
        customerService.logout();
        formService.loadLoginForm();
    }

    @FXML private void showLoginForm() {
        formService.loadLoginForm();
    }

    @FXML
    private void showChatForm() {
        formService.loadChatForm();
    }
}
