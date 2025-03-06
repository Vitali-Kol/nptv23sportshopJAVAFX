package org.example.kolesnikovsport_shop.model.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Название оборудования
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "equipment_suppliers",
            joinColumns = @JoinColumn(name = "equipment_id"),
            inverseJoinColumns = @JoinColumn(name = "supplier_id")
    )
    private Set<Supplier> suppliers = new HashSet<>();

    // Цена оборудования
    private double price;

    // Общее количество
    private int quantity;

    // Количество в наличии (stock)
    private int stock;

    public Equipment() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Supplier> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(Set<Supplier> suppliers) {
        this.suppliers = suppliers;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Equipment)) return false;
        Equipment equipment = (Equipment) o;
        return Double.compare(equipment.price, price) == 0 &&
                quantity == equipment.quantity &&
                stock == equipment.stock &&
                Objects.equals(id, equipment.id) &&
                Objects.equals(name, equipment.name) &&
                Objects.equals(suppliers, equipment.suppliers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, suppliers, price, quantity, stock);
    }

    @Override
    public String toString() {
        return "Equipment{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", suppliers=" + suppliers +
                ", price=" + price +
                ", quantity=" + quantity +
                ", stock=" + stock +
                '}';
    }
}
