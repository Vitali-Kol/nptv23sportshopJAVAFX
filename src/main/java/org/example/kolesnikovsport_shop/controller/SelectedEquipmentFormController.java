package org.example.kolesnikovsport_shop.controller;

import javafx.event.ActionEvent;
import org.example.kolesnikovsport_shop.model.entity.Equipment;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import org.example.kolesnikovsport_shop.service.CustomerService;
import org.example.kolesnikovsport_shop.service.FormService;
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

    private Equipment selectedEquipment;

    private final FormService formService;
    private final CustomerService customerService;

    public SelectedEquipmentFormController(FormService formService, CustomerService customerService) {
        this.formService = formService;
        this.customerService = customerService;
    }

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

        // Если пользователь не администратор или менеджер, скрываем кнопки редактирования и удаления
        // Эти кнопки удалены, поэтому этот код больше не нужен
    }

    /**
     * Метод для отображения предупреждения о недостаточности прав.
     */
    private void showAccessDeniedAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Доступ запрещён");
        alert.setHeaderText("Недостаточно прав");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
