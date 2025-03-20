package org.example.kolesnikovsport_shop.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import org.example.kolesnikovsport_shop.model.entity.Customer;
import org.example.kolesnikovsport_shop.service.CustomerService;
import org.example.kolesnikovsport_shop.service.FormService;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class CustomerListController implements Initializable {

    private final CustomerService customerService;
    private final FormService formService;

    @FXML
    private TableView<Customer> tvCustomerList;
    @FXML
    private TableColumn<Customer, String> tcId;
    @FXML
    private TableColumn<Customer, String> tcUsername;
    @FXML
    private TableColumn<Customer, String> tcFirstname;
    @FXML
    private TableColumn<Customer, String> tcLastname;
    @FXML
    private TableColumn<Customer, String> tcBalance;

    @FXML
    private Button editCustomerButton;  // Кнопка редактирования покупателя

    public CustomerListController(CustomerService customerService, FormService formService) {
        this.customerService = customerService;
        this.formService = formService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Загружаем всех покупателей
        List<Customer> customers = customerService.getAllCustomers();
        tvCustomerList.setItems(FXCollections.observableArrayList(customers));

        // Устанавливаем значения для каждой колонки
        tcId.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getId().toString()));
        tcUsername.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getUsername()));
        tcFirstname.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getFirstname()));
        tcLastname.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getLastname()));
        tcBalance.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getBalance())));

        // Делаем кнопку редактирования доступной только для администраторов
        if (!customerService.currentUserHasRole(CustomerService.ROLES.ADMINISTRATOR)) {
            editCustomerButton.setDisable(true);  // Отключаем кнопку для обычных пользователей
        }
    }

    // Метод для редактирования покупателя
    @FXML
    private void editCustomer() {
        Customer selectedCustomer = tvCustomerList.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {
            // Передаем выбранного покупателя в метод загрузки формы редактирования
            formService.loadEditCustomerForm(selectedCustomer);
        } else {
            showAccessDeniedAlert("Пожалуйста, выберите покупателя для редактирования.");
        }
    }

    // Метод для отображения ошибки, если покупатель не выбран
    private void showAccessDeniedAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText("Ошибка при редактировании");
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Переход на главную форму
    @FXML
    private void goToMainForm() {
        formService.loadMainForm();
    }
}
