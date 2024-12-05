package lk.ijse.main.service.impl;

import org.springframework.transaction.annotation.Transactional;
import lk.ijse.main.customObj.FieldResponse;
import lk.ijse.main.dto.FieldDTO;
import lk.ijse.main.entity.Field;
import lk.ijse.main.entity.Staff;
import lk.ijse.main.exception.DataPersistFailedException;
import lk.ijse.main.exception.FieldNotFoundException;
import lk.ijse.main.exception.StaffNotFoundException;
import lk.ijse.main.repository.FieldRepository;
import lk.ijse.main.repository.StaffRepository;
import lk.ijse.main.service.FieldService;
import lk.ijse.main.util.Mapping;
import lk.ijse.main.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.geo.Point;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class FieldServiceImpl implements FieldService {
    private final FieldRepository fieldRepository;
    private final StaffRepository staffRepository;
    private final Mapping mapping;

    @Override
    public void saveField(FieldDTO fieldDTO) {
        fieldDTO.setFieldCode(Util.createFieldCode());
        Field field = mapping.convertToFieldEntity(fieldDTO);
        String[] location = fieldDTO.getFieldLocation().split(",");
        double latitude = Double.parseDouble(location[0].trim());
        double longitude = Double.parseDouble(location[1].trim());
        field.setFieldLocation( new Point(latitude, longitude));

        Field savedField = fieldRepository.save(field);
        if (savedField == null || savedField.getFieldCode() == null) {
            throw new DataPersistFailedException("Cannot save data");
        }
    }

    @Override
    public void updateField(FieldDTO fieldDTO) {
        Optional<Field> tmpFieldEntity = fieldRepository.findById(fieldDTO.getFieldCode());
        if (!tmpFieldEntity.isPresent()) {
            throw new FieldNotFoundException("Field not found");
        } else {
            Field field = tmpFieldEntity.get();
            field.setFieldName(fieldDTO.getFieldName());

            String[] location = fieldDTO.getFieldLocation().split(",");
            double latitude = Double.parseDouble(location[0].trim());
            double longitude = Double.parseDouble(location[1].trim());
            field.setFieldLocation( new Point(latitude, longitude));

            try {
                field.setExtentSize(Double.parseDouble(fieldDTO.getExtentSize()));
                field.setFieldImage1(new String(fieldDTO.getFieldImage1().getBytes()));
                field.setFieldImage2(new String(fieldDTO.getFieldImage2().getBytes()));
                fieldRepository.save(field);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid extent size: " + fieldDTO.getExtentSize(), e);
            }
        }
    }

    @Override
    public void updateStaffField( List<String> staffIds,String fieldId) {
        Field field = fieldRepository.findById(fieldId)
                .orElseThrow(() -> new FieldNotFoundException("Field not found"));
        List<Staff> staffList = staffRepository.findAllById(staffIds);
        if (staffList.size() != staffIds.size()) {
            throw new StaffNotFoundException("One or more staff not found");
        }
        field.setStaffList(staffList);
        fieldRepository.save(field);
    }

    @Override
    public void deleteField(String fieldId) {
        Optional<Field> tmpFieldEntity = fieldRepository.findById(fieldId);
        if (!tmpFieldEntity.isPresent()) {
            throw new FieldNotFoundException("Field not found");
        } else {
            fieldRepository.deleteById(fieldId);
        }
    }

    @Override
    public List<FieldDTO> getAllField() {
        return mapping.convertToFieldDTO(fieldRepository.findAll());
    }

    @Override
    public FieldResponse getSelectField(String fieldId) {
        if (fieldRepository.existsById(fieldId)) {
            Field field = fieldRepository.getFieldByFieldCode(fieldId);
            return mapping.convertToFieldDTO(field);
        } else {
            throw new FieldNotFoundException("Field not found");
        }
    }
}