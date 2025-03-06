package org.example.kolesnikovsport_shop.controller;

import org.example.kolesnikovsport_shop.model.entity.Equipment;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class SelectedEquipmentFormController {

    @FXML
    private Label nameLabel;

    @FXML
    private Label suppliersLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private Label quantityLabel;

    @FXML
    private Label stockLabel;

    @FXML
    private Button buyEquipmentButton;

    @FXML
    private Button returnEquipmentButton;

    private Equipment selectedEquipment;

    /**
     * Метод вызывается извне (FormService), чтобы передать выбранное оборудование.
     */
    public void setEquipment(Equipment equipment) {
        this.selectedEquipment = equipment;
        if (equipment != null) {
            // Название
            nameLabel.setText(equipment.getName());
            // Поставщики
            String suppliersStr = equipment.getSuppliers().stream()
                    .map(supplier -> supplier.getName())
                    .collect(Collectors.joining(", "));
            suppliersLabel.setText(suppliersStr);
            // Цена, количество, в наличии
            priceLabel.setText(String.valueOf(equipment.getPrice()));
            quantityLabel.setText(String.valueOf(equipment.getQuantity()));
            stockLabel.setText(String.valueOf(equipment.getStock()));
        }
    }

    @FXML
    private void handleBuyEquipment() {
        if (selectedEquipment != null) {
            System.out.println("Покупка оборудования: " + selectedEquipment.getName());
            // Здесь логика покупки (например, добавление в корзину)
        }
    }

    @FXML
    private void handleReturnEquipment() {
        if (selectedEquipment != null) {
            System.out.println("Возврат оборудования: " + selectedEquipment.getName());
            // Логика возврата, если применимо
        }
    }
}
