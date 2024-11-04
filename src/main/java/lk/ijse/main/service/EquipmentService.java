package lk.ijse.main.service;

import lk.ijse.main.customObj.EquipmentResponse;
import lk.ijse.main.dto.EquipmentDTO;

import java.util.List;

public interface EquipmentService {
    void saveEquipment(EquipmentDTO equipmentDTO);
    void updateEquipment(String equipmentCode, EquipmentDTO equipmentDTO);
    void deleteEquipment(String equipmentCode);
    List<EquipmentDTO> getAllEquipment();
    EquipmentResponse getSelectEquipment(String equipmentCode);
}
