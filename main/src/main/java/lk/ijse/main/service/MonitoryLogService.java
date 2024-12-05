package lk.ijse.main.service;

import lk.ijse.main.customObj.MonitoryLogResponse;
import lk.ijse.main.dto.MonitoryLogDTO;
import java.util.List;

public interface MonitoryLogService {
    void saveMonitoryLog(MonitoryLogDTO monitoryLogDTO);
    void updateMonitoryLog(MonitoryLogDTO monitoryLogDTO);
    void updateMonitoryLogStaff(List<String> staff, String logCode);
    void updateMonitoryLogCrops(List<String> crops, String logCode);
    void deleteMonitoryLog(String logCode);
    List<MonitoryLogDTO> getAllMonitoryLogs();
    MonitoryLogResponse getSelectMonitoryLog(String logCode);
}