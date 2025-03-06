package org.example.kolesnikovsport_shop.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

    public CustomerListController(CustomerService customerService, FormService formService) {
        this.customerService = customerService;
        this.formService = formService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Предполагается, что метод getAllCustomers() возвращает список покупателей из базы
        List<Customer> customers = customerService.getAllCustomers();
        tvCustomerList.setItems(FXCollections.observableArrayList(customers));

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
    }

    @FXML
    private void goToMainForm() {
        formService.loadMainForm();
    }
}
