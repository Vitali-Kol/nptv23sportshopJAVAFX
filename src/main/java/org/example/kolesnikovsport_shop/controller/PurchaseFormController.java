package org.example.kolesnikovsport_shop.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.kolesnikovsport_shop.service.FormService;
import org.example.kolesnikovsport_shop.service.PurchaseService;
import org.springframework.stereotype.Component;

@Component
public class PurchaseFormController {

    private final PurchaseService purchaseService;
    private final FormService formService;

    @FXML
    private TextField tfCustomerId;
    @FXML
    private TextField tfEquipmentId;
    @FXML
    private TextField tfQuantity;
    @FXML
    private Label lblPurchaseResult;

    public PurchaseFormController(PurchaseService purchaseService, FormService formService) {
        this.purchaseService = purchaseService;
        this.formService = formService;
    }

    @FXML
    private void handlePurchase() {
        try {
            Long customerId = Long.parseLong(tfCustomerId.getText().trim());
            Long equipmentId = Long.parseLong(tfEquipmentId.getText().trim());
            int quantity = Integer.parseInt(tfQuantity.getText().trim());
            String result = purchaseService.buyEquipment(customerId, equipmentId, quantity);
            lblPurchaseResult.setText(result);
        } catch (NumberFormatException e) {
            lblPurchaseResult.setText("Неверный формат чисел!");
        }
    }

    @FXML
    private void goToMainForm() {
        formService.loadMainForm();
    }
}
