package org.example.kolesnikovsport_shop.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
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

    @FXML private ComboBox<Customer> cbCustomer;
    @FXML private ComboBox<Equipment> cbEquipment;
    @FXML private TextField tfQuantity;
    @FXML private Label lblPurchaseResult;

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
        // Заполняем ComboBox-ы
        cbCustomer.setItems(FXCollections.observableArrayList(customerService.getAllCustomers()));
        cbEquipment.setItems(FXCollections.observableArrayList(equipmentService.getAllEquipment()));

        // Кастомный рендер для покупателей (то же было у вас)
        cbCustomer.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Customer item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null
                        ? null
                        : String.format("ID:%d, %s, баланс:%.2f",
                        item.getId(),
                        item.getUsername(),
                        item.getBalance()));
            }
        });
        cbCustomer.setButtonCell(cbCustomer.getCellFactory().call(null));

        // Новая кастомизация для товаров — структурированный вывод
        cbEquipment.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Equipment item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.format("ID:%d  %s  (цена:%.2f, в наличии:%d)",
                            item.getId(),
                            item.getName(),
                            item.getPrice(),
                            item.getStock()));
                }
            }
        });
        // Чтобы выбранный товар тоже отображался красиво на кнопке
        cbEquipment.setButtonCell(cbEquipment.getCellFactory().call(null));

        // Если это обычный CUSTOMER, зафиксировать выбор на себе и отключить переключение
        boolean privileged = customerService.currentUserHasAnyRole(
                CustomerService.ROLES.MANAGER,
                CustomerService.ROLES.ADMINISTRATOR);
        if (!privileged) {
            Customer me = customerService.getCurrentCustomer();
            if (me != null) {
                cbCustomer.getSelectionModel().select(me);
            }
            cbCustomer.setDisable(true);
        }
    }

    @FXML
    private void handlePurchase() {
        try {
            Customer customer = cbCustomer.getValue();
            Equipment equipment = cbEquipment.getValue();
            int qty = Integer.parseInt(tfQuantity.getText().trim());

            if (customer == null || equipment == null) {
                lblPurchaseResult.setText("Выберите покупателя и товар!");
                return;
            }
            if (equipment.getStock() < qty) {
                lblPurchaseResult.setText("Недостаточно товара!");
                return;
            }

            String res = purchaseService.buyEquipment(
                    customer.getId(),
                    equipment.getId(),
                    qty);
            lblPurchaseResult.setText(res);

        } catch (NumberFormatException ex) {
            lblPurchaseResult.setText("Неверный формат числа!");
        }
    }

    @FXML
    private void goToMainForm() {
        formService.loadMainForm();
    }
}
