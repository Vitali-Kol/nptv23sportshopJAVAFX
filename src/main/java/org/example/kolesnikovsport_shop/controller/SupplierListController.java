package org.example.kolesnikovsport_shop.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.example.kolesnikovsport_shop.model.entity.Supplier;
import org.example.kolesnikovsport_shop.service.FormService;
import org.example.kolesnikovsport_shop.service.SupplierService;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class SupplierListController implements Initializable {

    private final SupplierService supplierService;
    private final FormService formService;

    @FXML
    private TableView<Supplier> tvSupplierList;

    @FXML
    private TableColumn<Supplier, String> tcId;

    @FXML
    private TableColumn<Supplier, String> tcName;

    @FXML
    private TableColumn<Supplier, String> tcContact;

    public SupplierListController(SupplierService supplierService, FormService formService) {
        this.supplierService = supplierService;
        this.formService = formService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tvSupplierList.setItems(FXCollections.observableArrayList(supplierService.getAllSuppliers()));
        tcId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId().toString()));
        tcName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        tcContact.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getContact()));
    }

    @FXML
    private void goToMainForm() {
        formService.loadMainForm();
    }

    @FXML
    private void editSelectedSupplier() {
        Supplier selectedSupplier = tvSupplierList.getSelectionModel().getSelectedItem();
        if (selectedSupplier != null) {
            formService.loadEditSupplierForm(selectedSupplier);
        } else {
            System.out.println("Поставщик не выбран!");
        }
    }
}
