package org.example.kolesnikovsport_shop.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.example.kolesnikovsport_shop.model.entity.Equipment;
import org.example.kolesnikovsport_shop.service.EquipmentService;
import org.example.kolesnikovsport_shop.service.FormService;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class EquipmentListController implements Initializable {

    private final EquipmentService equipmentService;
    private final FormService formService;

    @FXML
    private TableView<Equipment> tvEquipment;

    @FXML
    private TableColumn<Equipment, String> tcId;

    @FXML
    private TableColumn<Equipment, String> tcName;

    @FXML
    private TableColumn<Equipment, String> tcPrice;

    @FXML
    private TableColumn<Equipment, String> tcQuantity;

    @FXML
    private TableColumn<Equipment, String> tcStock;

    public EquipmentListController(EquipmentService equipmentService, FormService formService) {
        this.equipmentService = equipmentService;
        this.formService = formService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Настраиваем колонки таблицы
        tcId.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getId().toString()));
        tcName.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getName()));
        tcPrice.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getPrice())));
        tcQuantity.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getQuantity())));
        tcStock.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getStock())));

        // Загружаем список оборудования из EquipmentService
        List<Equipment> equipmentList = equipmentService.getAllEquipment();
        tvEquipment.setItems(FXCollections.observableArrayList(equipmentList));
    }

    @FXML
    private void goToMainForm() {
        formService.loadMainForm();
    }

    @FXML
    private void editSelectedEquipment() {
        Equipment selected = tvEquipment.getSelectionModel().getSelectedItem();
        if (selected != null) {
            formService.loadEditEquipmentForm(selected);
        } else {
            System.out.println("Оборудование не выбрано!");
        }
    }
}
