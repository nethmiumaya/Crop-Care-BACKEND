// FieldRepository.java
package lk.ijse.main.repository;

import lk.ijse.main.entity.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldRepository extends JpaRepository<Field, String> {
    Field getFieldByFieldCode(String fieldCode);
}