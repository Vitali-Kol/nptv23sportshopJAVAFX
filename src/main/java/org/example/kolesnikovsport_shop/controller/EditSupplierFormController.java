package org.example.kolesnikovsport_shop.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.example.kolesnikovsport_shop.model.entity.Supplier;
import org.example.kolesnikovsport_shop.service.CustomerService;
import org.example.kolesnikovsport_shop.service.FormService;
import org.example.kolesnikovsport_shop.service.SupplierService;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class EditSupplierFormController {

    private final SupplierService supplierService;
    private final FormService formService;
    // Внедряем CustomerService, чтобы иметь доступ к методам проверки прав
    private final CustomerService customerService;

    @FXML
    private TextField tfId;

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfContact;

    private Supplier editSupplier;

    // Обязательно добавляем CustomerService в конструктор (Spring автоматически внедрит его)
    public EditSupplierFormController(SupplierService supplierService, FormService formService, CustomerService customerService) {
        this.supplierService = supplierService;
        this.formService = formService;
        this.customerService = customerService;
    }

    /**
     * Этот метод вызывается из FormService сразу после загрузки FXML.
     * Заполняем поля формы данными выбранного поставщика.
     */
    public void setEditSupplier(Supplier supplier) {
        this.editSupplier = supplier;
        if (supplier != null) {
            tfId.setText(String.valueOf(supplier.getId()));
            tfName.setText(supplier.getName());
            tfContact.setText(supplier.getContact());
        }
    }

    @FXML
    private void saveSupplier() throws IOException {
        // Используем внедрённый customerService для проверки прав
        if (!customerService.currentUserHasRole(CustomerService.ROLES.ADMINISTRATOR) &&
                !customerService.currentUserHasRole(CustomerService.ROLES.MANAGER)) {
            showAccessDeniedAlert("У вас нет прав на редактирование поставщика.");
            return;
        }

        // Если всё ок, продолжаем
        editSupplier.setName(tfName.getText());
        editSupplier.setContact(tfContact.getText());
        supplierService.updateSupplier(editSupplier);
        formService.loadSupplierListForm();
    }

    private void showAccessDeniedAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка прав доступа");
        alert.setHeaderText("Доступ запрещён");
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void cancelEdit() throws IOException {
        formService.loadSupplierListForm();
    }
}
