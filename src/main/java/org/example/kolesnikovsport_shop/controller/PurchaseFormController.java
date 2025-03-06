package org.example.kolesnikovsport_shop.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.kolesnikovsport_shop.model.entity.Customer;
import org.example.kolesnikovsport_shop.model.entity.Equipment;
import org.example.kolesnikovsport_shop.service.CustomerService;
import org.example.kolesnikovsport_shop.service.EquipmentService;
import org.example.kolesnikovsport_shop.service.FormService;
import org.example.kolesnikovsport_shop.service.PurchaseService;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class PurchaseFormController implements Initializable {

    private final PurchaseService purchaseService;
    private final FormService formService;
    private final CustomerService customerService;
    private final EquipmentService equipmentService;

    @FXML
    private ComboBox<Customer> cbCustomer;
    @FXML
    private ComboBox<Equipment> cbEquipment;
    @FXML
    private TextField tfQuantity;
    @FXML
    private Label lblPurchaseResult;

    // Конструктор с внедрением зависимостей через Spring
    public PurchaseFormController(PurchaseService purchaseService,
                                  FormService formService,
                                  CustomerService customerService,
                                  EquipmentService equipmentService) {
        this.purchaseService = purchaseService;
        this.formService = formService;
        this.customerService = customerService;
        this.equipmentService = equipmentService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Заполняем ComboBox списком покупателей и товаров
        cbCustomer.setItems(FXCollections.observableArrayList(customerService.getAllCustomers()));
        cbEquipment.setItems(FXCollections.observableArrayList(equipmentService.getAllEquipment()));

    }

    @FXML
    private void handlePurchase() {
        try {
            Customer customer = cbCustomer.getSelectionModel().getSelectedItem();
            Equipment equipment = cbEquipment.getSelectionModel().getSelectedItem();
            int quantity = Integer.parseInt(tfQuantity.getText().trim());

            if (customer == null || equipment == null) {
                lblPurchaseResult.setText("Выберите покупателя и товар!");
                return;
            }

            // Вызываем метод покупки, который должен обновить баланс покупателя и количество товара
            String result = purchaseService.buyEquipment(customer.getId(), equipment.getId(), quantity);
            lblPurchaseResult.setText(result);
        } catch (NumberFormatException e) {
            lblPurchaseResult.setText("Неверный формат количества!");
        }
    }

    @FXML
    private void goToMainForm() {
        formService.loadMainForm();
    }
}
