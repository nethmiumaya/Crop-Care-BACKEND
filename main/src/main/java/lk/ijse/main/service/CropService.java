package lk.ijse.main.service;

import lk.ijse.main.customObj.CropResponse;
import lk.ijse.main.dto.CropDTO;

import java.util.List;

public interface CropService {
    void saveCrop(CropDTO cropDTO);
    void updateCrop(CropDTO cropDTO);
    void updateCropField(String cropId, String fieldId);
    void deleteCrop(String cropId);
    List<CropDTO> getAllCrop();
    CropResponse getSelectCrop(String cropId);
}
