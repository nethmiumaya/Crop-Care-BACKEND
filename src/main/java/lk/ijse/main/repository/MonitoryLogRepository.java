// MonitoryLogRepository.java
package lk.ijse.main.repository;

import lk.ijse.main.entity.MonitoringLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonitoryLogRepository extends JpaRepository<MonitoringLog, String> {
}