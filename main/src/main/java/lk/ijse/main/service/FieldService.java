package lk.ijse.main.service;

import lk.ijse.main.customObj.FieldResponse;
import lk.ijse.main.dto.FieldDTO;

import java.util.List;

public interface FieldService {
    void saveField(FieldDTO fieldDTO);
    void updateField(FieldDTO fieldDTO);
    void updateStaffField(List<String> staffDto, String fieldId);
    void deleteField(String fieldId);
    List<FieldDTO> getAllField();
    FieldResponse getSelectField(String fieldId);
}
