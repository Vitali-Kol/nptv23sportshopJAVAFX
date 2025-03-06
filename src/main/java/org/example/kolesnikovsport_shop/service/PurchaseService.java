package org.example.kolesnikovsport_shop.service;

import org.example.kolesnikovsport_shop.model.entity.Customer;
import org.example.kolesnikovsport_shop.model.entity.Equipment;
import org.example.kolesnikovsport_shop.model.entity.Purchase;
import org.example.kolesnikovsport_shop.model.repository.PurchaseRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

@Service
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final EquipmentService equipmentService;
    private final CustomerService customerService;

    public PurchaseService(PurchaseRepository purchaseRepository,
                           EquipmentService equipmentService,
                           CustomerService customerService) {
        this.purchaseRepository = purchaseRepository;
        this.equipmentService = equipmentService;
        this.customerService = customerService;
    }

    /**
     * Покупка товара.
     * Уменьшается баланс покупателя на цену товара * количество, а также количество товара на складе.
     * Если средств недостаточно или товара меньше требуемого количества, возвращается сообщение об ошибке.
     *
     * @param customerId ID покупателя.
     * @param equipmentId ID товара.
     * @param quantity количество товара для покупки.
     * @return Результат операции.
     */
    public String buyEquipment(Long customerId, Long equipmentId, int quantity) {
        Customer customer = customerService.findById(customerId).orElse(null);
        Equipment equipment = equipmentService.findById(equipmentId);
        if (customer == null) {
            return "Покупатель не найден!";
        }
        if (equipment == null) {
            return "Товар не найден!";
        }
        if (equipment.getQuantity() < quantity) {
            return "Недостаточно товара на складе!";
        }
        double totalPrice = equipment.getPrice() * quantity;
        if (customer.getBalance() < totalPrice) {
            return "Недостаточно средств у покупателя!";
        }
        // Уменьшаем баланс покупателя и количество товара
        customer.setBalance(customer.getBalance() - totalPrice);
        equipment.setQuantity(equipment.getQuantity() - quantity);
        customerService.update(customer);
        equipmentService.update(equipment);
        // Создаем и сохраняем запись о покупке
        Purchase purchase = new Purchase(equipment, customer, quantity, totalPrice, LocalDateTime.now());
        purchaseRepository.save(purchase);
        return "Покупка успешно выполнена!";
    }

    /**
     * Возвращает доход магазина за период.
     */
    public double getIncome(LocalDateTime start, LocalDateTime end) {
        Double sum = purchaseRepository.getIncomeBetween(start, end);
        return (sum == null ? 0.0 : sum);
    }

    public double getIncomeByDay(LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.plusDays(1).atStartOfDay().minusNanos(1);
        return getIncome(start, end);
    }

    public double getIncomeByMonth(YearMonth ym) {
        LocalDateTime start = ym.atDay(1).atStartOfDay();
        LocalDateTime end = ym.atEndOfMonth().atTime(23, 59, 59, 999999999);
        return getIncome(start, end);
    }

    public double getIncomeByYear(int year) {
        LocalDateTime start = LocalDate.of(year, 1, 1).atStartOfDay();
        LocalDateTime end = LocalDate.of(year, 12, 31).atTime(23, 59, 59, 999999999);
        return getIncome(start, end);
    }

    // Рейтинг продаваемости товаров за указанный период
    public List<Object[]> getTopEquipment(LocalDateTime start, LocalDateTime end) {
        return purchaseRepository.getTopEquipmentBetween(start, end);
    }

    // Рейтинг товаров за всё время
    public List<Object[]> getTopEquipmentAllTime() {
        return purchaseRepository.getTopEquipmentAllTime();
    }

    public List<Object[]> getTopEquipmentByDay(LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.plusDays(1).atStartOfDay().minusNanos(1);
        return purchaseRepository.getTopEquipmentBetween(start, end);
    }

    public List<Object[]> getTopEquipmentByMonth(YearMonth ym) {
        LocalDateTime start = ym.atDay(1).atStartOfDay();
        LocalDateTime end = ym.atEndOfMonth().atTime(23, 59, 59, 999999999);
        return purchaseRepository.getTopEquipmentBetween(start, end);
    }

    public List<Object[]> getTopEquipmentByYear(int year) {
        LocalDateTime start = LocalDate.of(year, 1, 1).atStartOfDay();
        LocalDateTime end = LocalDate.of(year, 12, 31).atTime(23, 59, 59, 999999999);
        return purchaseRepository.getTopEquipmentBetween(start, end);
    }

    // Для указанной недели (начало недели передается как LocalDate)
    public List<Object[]> getTopEquipmentByWeek(LocalDate weekStart) {
        LocalDateTime start = weekStart.atStartOfDay();
        LocalDateTime end = weekStart.plusDays(7).atStartOfDay().minusNanos(1);
        return purchaseRepository.getTopEquipmentBetween(start, end);
    }
}
