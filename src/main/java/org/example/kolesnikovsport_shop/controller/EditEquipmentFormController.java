package org.example.kolesnikovsport_shop.controller;

import org.example.kolesnikovsport_shop.model.entity.Equipment;
import org.example.kolesnikovsport_shop.model.entity.Supplier;
import org.example.kolesnikovsport_shop.service.SupplierService;
import org.example.kolesnikovsport_shop.service.EquipmentService;
import org.example.kolesnikovsport_shop.service.FormService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.List;
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
    private ListView<Supplier> lvSuppliers;

    @FXML
    private TextField tfPrice;

    @FXML
    private TextField tfQuantity;

    @FXML
    private TextField tfStock;

    public EditEquipmentFormController(FormService formService, EquipmentService equipmentService, SupplierService supplierService) {
        this.formService = formService;
        this.equipmentService = equipmentService;
        this.supplierService = supplierService;
    }

    @FXML
    private void goEdit() throws IOException {
        editEquipment.setName(tfName.getText());


        editEquipment.getSuppliers().clear();
        editEquipment.getSuppliers().addAll(lvSuppliers.getSelectionModel().getSelectedItems());

        editEquipment.setPrice(Double.parseDouble(tfPrice.getText()));
        editEquipment.setQuantity(Integer.parseInt(tfQuantity.getText()));

        editEquipment.setStock(editEquipment.getQuantity());
        equipmentService.update(editEquipment);
        formService.loadMainForm();
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
        lvSuppliers.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        List<Supplier> suppliers = supplierService.getAllSuppliers();
        lvSuppliers.getItems().setAll(FXCollections.observableArrayList(suppliers));
        lvSuppliers.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Supplier supplier, boolean empty) {
                super.updateItem(supplier, empty);
                if (empty || supplier == null) {
                    setText(null);
                } else {
                    setText("ID: " + supplier.getId() + " - " + supplier.getName());
                }
            }
        });
    }
}
