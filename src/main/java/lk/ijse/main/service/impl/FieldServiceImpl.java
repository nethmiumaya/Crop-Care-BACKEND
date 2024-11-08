package lk.ijse.main.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.main.customObj.FieldResponse;
import lk.ijse.main.dto.FieldDTO;
import lk.ijse.main.entity.Field;
import lk.ijse.main.exception.DataPersistFailedException;
import lk.ijse.main.exception.FieldNotFoundException;
import lk.ijse.main.repository.FieldRepository;
import lk.ijse.main.service.FieldService;
import lk.ijse.main.util.Mapping;
import lk.ijse.main.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class FieldServiceImpl implements FieldService {
    @Autowired
    private final FieldRepository fieldRepository;

    private final Mapping mapping;
    @Override
    public void saveField(FieldDTO fieldDTO) {
        fieldDTO.setFieldCode(Util.createFieldCode());
        Field savedField = fieldRepository.save(mapping.convertToFieldEntity(fieldDTO));
        if (savedField == null && savedField.getFieldCode()==null){
            throw new DataPersistFailedException("Cannot data saved");
        }
    }

    @Override
    public void updateField(FieldDTO fieldDTO) {
        Optional<Field> tmpFieldEntity = fieldRepository.findById(fieldDTO.getFieldCode());
        if (!tmpFieldEntity.isPresent()) {
            throw new FieldNotFoundException("Field not Found");
        } else {
            tmpFieldEntity.get().setFieldName(fieldDTO.getFieldName());

            // Convert field location from String to java.awt.Point
            String[] location = fieldDTO.getFieldLocation().split(",");
            java.awt.Point point = new java.awt.Point(Integer.parseInt(location[0]), Integer.parseInt(location[1]));
            tmpFieldEntity.get().setFieldLocation(point);

            tmpFieldEntity.get().setExtentSize(Double.parseDouble(fieldDTO.getExtentSize()));
            tmpFieldEntity.get().setFieldImage1(String.valueOf(fieldDTO.getFieldImage1()));
            tmpFieldEntity.get().setFieldImage2(String.valueOf(fieldDTO.getFieldImage2()));
        }
    }

    @Override
    public void deleteField(String fieldId) {
        Optional<Field> tmpFieldEntity =
                fieldRepository.findById(fieldId);
        if (!tmpFieldEntity.isPresent()){
            throw new FieldNotFoundException("Field not Found");
        }else {
            fieldRepository.deleteById(fieldId);
        }

    }

    @Override
    public List<FieldDTO> getAllField() {
        return mapping.convertToFieldDTO(fieldRepository.findAll());
    }

    @Override
    public FieldResponse getSelectField(String fieldId) {
        if (fieldRepository.existsById(fieldId)){
            Field field = fieldRepository.getFieldByFieldId(fieldId);
            return (FieldResponse) mapping.convertToFieldDTO(field);
        }else {
            throw new FieldNotFoundException("Field not Found");
        }
    }
}
