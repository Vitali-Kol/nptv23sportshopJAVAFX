package org.example.kolesnikovsport_shop.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.example.kolesnikovsport_shop.model.entity.Equipment;
import org.example.kolesnikovsport_shop.service.CustomerService;
import org.example.kolesnikovsport_shop.service.EquipmentService;
import org.example.kolesnikovsport_shop.service.FormService;
import org.example.kolesnikovsport_shop.service.SupplierService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class EditEquipmentFormController implements Initializable {

    private final FormService formService;
    private final EquipmentService equipmentService;
    private final SupplierService supplierService;
    private Equipment editEquipment;

    @FXML
    private TextField tfId;
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfPrice;
    @FXML
    private TextField tfQuantity;
    @FXML
    private TextField tfStock;

    private final CustomerService customerService;

    public EditEquipmentFormController(FormService formService, EquipmentService equipmentService, SupplierService supplierService, CustomerService customerService) {
        this.formService = formService;
        this.equipmentService = equipmentService;
        this.supplierService = supplierService;
        this.customerService = customerService;
    }

    @FXML
    private void goEdit() throws IOException {
        // Используем объект customerService вместо вызова через класс
        if (!customerService.currentUserHasRole(CustomerService.ROLES.ADMINISTRATOR) &&
                !customerService.currentUserHasRole(CustomerService.ROLES.MANAGER)) {
            showAccessDeniedAlert("У вас нет прав на редактирование оборудования.");
            return;
        }

        editEquipment.setName(tfName.getText());
        equipmentService.update(editEquipment);
        formService.loadMainForm();
    }

    private void showAccessDeniedAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка прав доступа");
        alert.setHeaderText("Доступ запрещён");
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void goToMainForm() throws IOException {
        formService.loadMainForm();
    }

    public void setEditEquipment(Equipment editEquipment) {
        this.editEquipment = editEquipment;
        tfId.setText(editEquipment.getId().toString());
        tfName.setText(editEquipment.getName());
        tfPrice.setText(String.valueOf(editEquipment.getPrice()));
        tfQuantity.setText(String.valueOf(editEquipment.getQuantity()));
        tfStock.setText(String.valueOf(editEquipment.getStock()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // остальные настройки и инициализация компонентов
    }
}
