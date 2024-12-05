package lk.ijse.main.service.impl;

import lk.ijse.main.customObj.EquipmentResponse;
import lk.ijse.main.entity.Field;
import lk.ijse.main.entity.Staff;
import lk.ijse.main.exception.FieldNotFoundException;
import lk.ijse.main.repository.EquipmentRepository;
import lk.ijse.main.dto.EquipmentDTO;
import lk.ijse.main.entity.Equipment;
import lk.ijse.main.exception.DataPersistFailedException;
import lk.ijse.main.exception.EquipmentNotFoundException;
import lk.ijse.main.repository.FieldRepository;
import lk.ijse.main.repository.StaffRepository;
import lk.ijse.main.service.EquipmentService;
import lk.ijse.main.util.Mapping;
import lk.ijse.main.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class EquipmentServiceImpl implements EquipmentService {
    private final EquipmentRepository equipmentRepository;
    private final FieldRepository fieldRepository;
    private final StaffRepository staffRepository;
    private final Mapping mapping;

    @Override
    public void saveEquipment(EquipmentDTO equipmentDTO) {
        equipmentDTO.setEquipmentCode(Util.createEquipmentCode());
        var EquipmentEntity = mapping.convertToEquipmentEntity(equipmentDTO);
        var savedEquipment = equipmentRepository.save(EquipmentEntity);
        if (savedEquipment == null) {
            throw new DataPersistFailedException("Cannot data saved");
        }
    }

    @Override
    public void updateEquipment(String equipmentCode, EquipmentDTO equipmentDTO) {
        Optional<Equipment> tmpEquipmentEntity =
                equipmentRepository.findById(equipmentCode);
        if (!tmpEquipmentEntity.isPresent()) {
            throw new EquipmentNotFoundException("Equipment not Found");
        } else {
            tmpEquipmentEntity.get().setStatus(equipmentDTO.getStatus());
            tmpEquipmentEntity.get().setName(equipmentDTO.getName());
            tmpEquipmentEntity.get().setType(equipmentDTO.getType());
        }
    }

    @Override
    public void updateEquipmentField(String equipmentCode, String fieldCode) {
        Equipment equipment = equipmentRepository.findById(equipmentCode)
                .orElseThrow(() -> new EquipmentNotFoundException("Equipment not Found"));
        Field field = fieldRepository.findById(fieldCode)
                .orElseThrow(() -> new EquipmentNotFoundException("Field not Found"));
        equipment.setAssignedFieldDetails(field);
    }

    @Override
    public void updateEquipmentStaff(String equipmentCode, String staffId) {
        Equipment equipment = equipmentRepository.findById(equipmentCode)
                .orElseThrow(() -> new EquipmentNotFoundException("Equipment not Found"));
        Staff staff = staffRepository.findById(staffId)
                .orElseThrow(() -> new EquipmentNotFoundException("Staff not Found"));
        equipment.setAssignedStaffDetails(staff);
    }

    @Override
    public void deleteEquipment(String equipmentCode) {
        Optional<Equipment> findId = equipmentRepository.findById(equipmentCode);
        if (!findId.isPresent()) {
            throw new EquipmentNotFoundException("Equipment not Found");
        } else {
            equipmentRepository.deleteById(equipmentCode);
        }
    }

    @Override
    public List<EquipmentDTO> getAllEquipment() {
        return mapping.convertToEquipmentDTO(equipmentRepository.findAll());
    }


    @Override
    public EquipmentResponse getSelectEquipment(String equipmentCode) {
        Equipment equipment = equipmentRepository.findById(equipmentCode)
                .orElseThrow(() -> new EquipmentNotFoundException("Equipment not Found"));
        return mapping.convertToEquipmentDTO(equipment);
    }

    @Override
    public List<EquipmentDTO> getFieldEquipments(String fieldCode) {
        if (!fieldRepository.existsById(fieldCode)) throw new FieldNotFoundException("Field not found");
        List<Equipment> equipments = equipmentRepository.findByFieldCode(fieldCode);
        if (equipments.isEmpty()) throw new EquipmentNotFoundException("No in use Equipment found for the field");
        return mapping.convertToEquipmentDTO(equipments);
    }
}
