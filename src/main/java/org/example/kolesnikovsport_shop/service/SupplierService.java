package org.example.kolesnikovsport_shop.service;

import org.example.kolesnikovsport_shop.model.entity.Supplier;
import org.example.kolesnikovsport_shop.model.repository.SupplierRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierService {

    private final SupplierRepository supplierRepository;

    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    public Supplier add(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    // Новый метод для обновления
    public Supplier updateSupplier(Supplier supplier) {
        // Можно сделать дополнительную валидацию, если нужно
        return supplierRepository.save(supplier);
    }

    // Другие методы...
}
