package org.example.kolesnikovsport_shop.service;

import org.example.kolesnikovsport_shop.KolesnikovSportShopApplication;
import org.example.kolesnikovsport_shop.controller.EditEquipmentFormController;
import org.example.kolesnikovsport_shop.controller.EditSupplierFormController;
import org.example.kolesnikovsport_shop.controller.SelectedEquipmentFormController;
import org.example.kolesnikovsport_shop.model.entity.Equipment;
import org.example.kolesnikovsport_shop.model.entity.Supplier;
import org.example.kolesnikovsport_shop.model.entity.Customer;
import org.example.kolesnikovsport_shop.tools.SpringFXMLLoader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class FormService {
    private final SpringFXMLLoader springFXMLLoader;

    public FormService(SpringFXMLLoader springFXMLLoader) {
        this.springFXMLLoader = springFXMLLoader;
    }

    public void loadLoginForm() {
        FXMLLoader fxmlLoader = springFXMLLoader.load("/user/loginForm.fxml");
        Parent root;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        getPrimaryStage().setScene(scene);
        getPrimaryStage().setTitle("SportStore - Вход");
        getPrimaryStage().centerOnScreen();
        getPrimaryStage().show();
    }

    public void loadMainForm() {
        FXMLLoader fxmlLoader = springFXMLLoader.load("/main/mainForm.fxml");
        Parent root;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        getPrimaryStage().setScene(scene);
        getPrimaryStage().setTitle("SportStore - Главная");
        getPrimaryStage().centerOnScreen();
        getPrimaryStage().show();
    }

    private Stage getPrimaryStage() {
        return KolesnikovSportShopApplication.primaryStage;
    }

    public void loadNewEquipmentForm() {
        FXMLLoader fxmlLoader = springFXMLLoader.load("/equipment/newEquipmentForm.fxml");
        Parent root;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        getPrimaryStage().setScene(scene);
        getPrimaryStage().setTitle("Создание нового оборудования");
    }

    public void loadEditEquipmentForm(Equipment selectedEquipment) {
        FXMLLoader fxmlLoader = springFXMLLoader.load("/equipment/editEquipmentForm.fxml");
        try {
            Parent editEquipmentFormRoot = fxmlLoader.load();
            EditEquipmentFormController controller = fxmlLoader.getController();
            controller.setEditEquipment(selectedEquipment);
            Scene scene = new Scene(editEquipmentFormRoot);
            getPrimaryStage().setTitle("SportStore - Редактирование оборудования");
            getPrimaryStage().setScene(scene);
            getPrimaryStage().setResizable(false);
            getPrimaryStage().centerOnScreen();
            getPrimaryStage().show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Parent loadMenuForm() {
        FXMLLoader fxmlLoader = springFXMLLoader.load("/menu/menuForm.fxml");
        try {
            return fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadSupplierForm() {
        FXMLLoader fxmlLoader = springFXMLLoader.load("/supplier/supplierForm.fxml");
        Parent root;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        getPrimaryStage().setScene(scene);
        getPrimaryStage().setTitle("Создание нового поставщика");
    }

    public void loadSupplierListForm() {
        FXMLLoader fxmlLoader = springFXMLLoader.load("/supplier/supplierList.fxml");
        Parent root;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        getPrimaryStage().setScene(scene);
        getPrimaryStage().setTitle("Список поставщиков");
        getPrimaryStage().centerOnScreen();
        getPrimaryStage().show();
    }

    public void loadRegistrationForm() {
        FXMLLoader fxmlLoader = springFXMLLoader.load("/user/registrationForm.fxml");
        Parent root;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        getPrimaryStage().setScene(scene);
        getPrimaryStage().setTitle("Регистрация нового пользователя");
    }

    public void loadSelectedEquipmentForm(Equipment selectedEquipment) {
        FXMLLoader fxmlLoader = springFXMLLoader.load("/equipment/selectedEquipmentForm.fxml");
        try {
            Parent selectedEquipmentFormRoot = fxmlLoader.load();
            SelectedEquipmentFormController controller = fxmlLoader.getController();
            controller.setEquipment(selectedEquipment);

            Stage stage = new Stage();
            stage.setTitle("Информация об оборудовании");
            stage.setScene(new Scene(selectedEquipmentFormRoot));
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void loadEditSupplierForm(Supplier selectedSupplier) {
        FXMLLoader fxmlLoader = springFXMLLoader.load("/supplier/editSupplierForm.fxml");
        try {
            Parent editSupplierFormRoot = fxmlLoader.load();
            EditSupplierFormController controller = fxmlLoader.getController();
            controller.setEditSupplier(selectedSupplier);
            Scene scene = new Scene(editSupplierFormRoot);
            getPrimaryStage().setTitle("SportStore - Редактирование поставщика");
            getPrimaryStage().setScene(scene);
            getPrimaryStage().setResizable(false);
            getPrimaryStage().centerOnScreen();
            getPrimaryStage().show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // ========================
    // Методы для работы с покупателями
    // ========================

    public void loadNewCustomerForm() {
        FXMLLoader fxmlLoader = springFXMLLoader.load("/customer/newCustomerForm.fxml");
        Parent root;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        getPrimaryStage().setScene(scene);
        getPrimaryStage().setTitle("Создание нового покупателя");
        getPrimaryStage().centerOnScreen();
        getPrimaryStage().show();
    }

    public void loadCustomerListForm() {
        FXMLLoader fxmlLoader = springFXMLLoader.load("/customer/customerList.fxml");
        Parent root;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        getPrimaryStage().setScene(scene);
        getPrimaryStage().setTitle("Список покупателей");
        getPrimaryStage().centerOnScreen();
        getPrimaryStage().show();
    }

    public void loadEquipmentListForm() {
        FXMLLoader fxmlLoader = springFXMLLoader.load("/equipment/equipmentList.fxml");
        Parent root;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        getPrimaryStage().setScene(scene);
        getPrimaryStage().setTitle("Список оборудования");
        getPrimaryStage().centerOnScreen();
        getPrimaryStage().show();
    }

    // ========================
    // Новые методы для реализации функций 7-9
    // ========================

    // Форма покупки товара
    public void loadPurchaseForm() {
        FXMLLoader fxmlLoader = springFXMLLoader.load("/purchase/purchaseForm.fxml");
        Parent root;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        getPrimaryStage().setScene(scene);
        getPrimaryStage().setTitle("Покупка товара");
        getPrimaryStage().centerOnScreen();
        getPrimaryStage().show();
    }

    // Форма дохода магазина
    public void loadIncomeForm() {
        FXMLLoader fxmlLoader = springFXMLLoader.load("/purchase/incomeForm.fxml");
        Parent root;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        getPrimaryStage().setScene(scene);
        getPrimaryStage().setTitle("Доход магазина");
        getPrimaryStage().centerOnScreen();
        getPrimaryStage().show();
    }

    // Форма рейтинга продаваемости товаров
    public void loadRatingForm() {
        FXMLLoader fxmlLoader = springFXMLLoader.load("/purchase/ratingForm.fxml");
        Parent root;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        getPrimaryStage().setScene(scene);
        getPrimaryStage().setTitle("Рейтинг товаров");
        getPrimaryStage().centerOnScreen();
        getPrimaryStage().show();
    }
}

