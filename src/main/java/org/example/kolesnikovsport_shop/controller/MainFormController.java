package org.example.kolesnikovsport_shop.controller;

import org.example.kolesnikovsport_shop.model.entity.Equipment;
import org.example.kolesnikovsport_shop.service.EquipmentService;
import org.example.kolesnikovsport_shop.service.FormService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Component
public class MainFormController implements Initializable {

    private final FormService formService;
    private final EquipmentService equipmentService;

    @FXML
    private VBox vbMainFormRoot;

    @FXML
    private TableView<Equipment> tvEquipmentList;

    @FXML
    private TableColumn<Equipment, String> tcId;

    @FXML
    private TableColumn<Equipment, String> tcName;

    @FXML
    private TableColumn<Equipment, String> tcSuppliers;

    @FXML
    private TableColumn<Equipment, String> tcPrice;

    @FXML
    private TableColumn<Equipment, String> tcQuantity;

    @FXML
    private TableColumn<Equipment, String> tcStock;

    @FXML
    private HBox hbEditEquipment;

    public MainFormController(FormService formService, EquipmentService equipmentService) {
        this.formService = formService;
        this.equipmentService = equipmentService;
    }

    // Открытие окна редактирования оборудования
    @FXML
    private void showEditEquipmentForm() {
        formService.loadEditEquipmentForm(tvEquipmentList.getSelectionModel().getSelectedItem());
    }

    // Открытие окна с подробной информацией о выбранном оборудовании
    @FXML
    private void showSelectedEquipmentForm() {
        Equipment selectedEquipment = tvEquipmentList.getSelectionModel().getSelectedItem();
        if (selectedEquipment != null) {
            formService.loadSelectedEquipmentForm(selectedEquipment);
        } else {
            System.out.println("Оборудование не выбрано!");
        }
    }

    // Новый метод для удаления выбранного оборудования
    @FXML
    private void deleteSelectedEquipment() {
        Equipment selectedEquipment = tvEquipmentList.getSelectionModel().getSelectedItem();
        if (selectedEquipment != null) {
            equipmentService.delete(selectedEquipment.getId());
            // Обновляем список оборудования после удаления
            tvEquipmentList.setItems(equipmentService.getAllEquipment());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        vbMainFormRoot.getChildren().addFirst(formService.loadMenuForm());
        tvEquipmentList.setItems(equipmentService.getAllEquipment());
        tcId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId().toString()));
        tcName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        tcSuppliers.setCellValueFactory(cellData -> {
            Equipment equipment = cellData.getValue();
            if (equipment.getSuppliers() == null || equipment.getSuppliers().isEmpty()) {
                return new SimpleStringProperty("");
            }
            String suppliers = equipment.getSuppliers().stream()
                    .map(supplier -> supplier.getName())
                    .collect(Collectors.joining(", "));
            return new SimpleStringProperty(suppliers);
        });
        tcPrice.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPrice())));
        tcQuantity.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getQuantity())));
        tcStock.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getStock())));
        tvEquipmentList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Equipment>() {
            @Override
            public void changed(ObservableValue<? extends Equipment> observable, Equipment oldValue, Equipment newValue) {
                hbEditEquipment.setVisible(newValue != null);
            }
        });
    }
}
