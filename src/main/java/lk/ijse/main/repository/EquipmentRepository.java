package lk.ijse.main.repository;

import lk.ijse.main.entity.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, String> {
    @Query("SELECT e FROM Equipment e WHERE e.assignedFieldDetails.fieldCode = :fieldCode")
    List<Equipment> findByFieldCode(String fieldCode);
}
