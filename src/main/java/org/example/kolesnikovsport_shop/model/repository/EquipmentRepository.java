package org.example.kolesnikovsport_shop.model.repository;

import org.example.kolesnikovsport_shop.model.entity.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
}
