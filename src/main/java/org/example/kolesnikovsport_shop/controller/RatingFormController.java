package org.example.kolesnikovsport_shop.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.example.kolesnikovsport_shop.service.FormService;
import org.springframework.stereotype.Component;

@Component
public class RatingFormController {

    private final FormService formService;

    @FXML
    private ComboBox<String> cbPeriodType;
    @FXML
    private TextField tfPeriodValue;
    @FXML
    private TableView<?> tvRating;
    @FXML
    private TableColumn<?, ?> tcEquipment;
    @FXML
    private TableColumn<?, ?> tcTotalSold;

    // Конструктор, в который Spring внедряет FormService
    public RatingFormController(FormService formService) {
        this.formService = formService;
    }

    @FXML
    public void initialize() {
        // Заполняем ComboBox программно
        cbPeriodType.setItems(FXCollections.observableArrayList(
                "Все время", "Год", "Месяц", "Неделя"
        ));
    }

    @FXML
    private void handleCalculateRating() {
        // Логика расчёта рейтинга
        System.out.println("Рассчитываем рейтинг для: " + cbPeriodType.getValue());
    }

    @FXML
    private void goToMainForm() {
        // Переход на главную форму через FormService
        formService.loadMainForm();
    }
}
