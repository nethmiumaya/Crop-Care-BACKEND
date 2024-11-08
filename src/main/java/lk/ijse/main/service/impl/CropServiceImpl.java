package lk.ijse.main.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.main.customObj.CropErrorResponse;
import lk.ijse.main.customObj.CropResponse;
import lk.ijse.main.dto.CropDTO;
import lk.ijse.main.entity.Crop;
import lk.ijse.main.exception.CropNotFoundException;
import lk.ijse.main.exception.DataPersistFailedException;
import lk.ijse.main.repository.CropRepository;
import lk.ijse.main.service.CropService;
import lk.ijse.main.util.Mapping;
import lk.ijse.main.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CropServiceImpl implements CropService {

    private final CropRepository cropRepository;
    private final Mapping mapping;

    @Override
    public void saveCrop(CropDTO cropDTO) {
        cropDTO.setCode(Util.createCropCode());
        Crop savedCrop = cropRepository.save(mapping.convertToCropEntity(cropDTO));
        if (savedCrop == null || savedCrop.getCode() == null) {
            throw new DataPersistFailedException("Crop not saved");
        }
    }

    @Override
    public void updateCrop(CropDTO cropDTO) {
        Optional<Crop> tmpCrop = cropRepository.findById(cropDTO.getCode());
        if (!tmpCrop.isPresent()) {
            throw new CropNotFoundException("Crop not found");
        } else {
            tmpCrop.get().setCommonName(cropDTO.getCommonName());
            tmpCrop.get().setScientificName(cropDTO.getScientificName());
            tmpCrop.get().setCropImage(cropDTO.getCropImage());
            tmpCrop.get().setCategory(cropDTO.getCategory());
            tmpCrop.get().setSeason(cropDTO.getSeason());
           // tmpCrop.get().setField(mapping.convertToFieldEntity(cropDTO.getField()));
        }
    }

    @Override
    public void deleteCrop(String cropId) {
        Optional<Crop> findId = cropRepository.findById(cropId);
        if (!findId.isPresent()) {
            throw new CropNotFoundException("Crop not found");
        } else {
            cropRepository.deleteById(cropId);
        }
    }

    @Override
    public List<CropDTO> getAllCrop() {
        return mapping.convertToCropDTO(cropRepository.findAll());
    }

    @Override
    public CropResponse getSelectCrop(String cropId) {
        if (cropRepository.existsById(cropId)) {
            return mapping.convertToCropDTO(cropRepository.getReferenceById(cropId));
        } else {
            return new CropErrorResponse(0, "Crop not found");
        }
    }
}