package org.example.kolesnikovsport_shop.model.repository;

import org.example.kolesnikovsport_shop.model.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
}
