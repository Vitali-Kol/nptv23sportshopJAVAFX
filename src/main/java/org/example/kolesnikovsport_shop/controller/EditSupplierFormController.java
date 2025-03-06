package org.example.kolesnikovsport_shop.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.example.kolesnikovsport_shop.model.entity.Supplier;
import org.example.kolesnikovsport_shop.service.FormService;
import org.example.kolesnikovsport_shop.service.SupplierService;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class EditSupplierFormController {

    private final SupplierService supplierService;
    private final FormService formService;

    @FXML
    private TextField tfId;

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfContact;

    private Supplier editSupplier;

    public EditSupplierFormController(SupplierService supplierService, FormService formService) {
        this.supplierService = supplierService;
        this.formService = formService;
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
        // Обновляем данные поставщика
        editSupplier.setName(tfName.getText());
        editSupplier.setContact(tfContact.getText());

        // Вызываем метод обновления в сервисе
        supplierService.updateSupplier(editSupplier);

        // Возвращаемся к списку поставщиков
        formService.loadSupplierListForm();
    }

    @FXML
    private void cancelEdit() throws IOException {
        // Отмена – просто возвращаемся к списку
        formService.loadSupplierListForm();
    }
}
