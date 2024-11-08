package lk.ijse.main.repository;

import lk.ijse.main.entity.Crop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CropRepository extends JpaRepository<Crop,String> {
}
