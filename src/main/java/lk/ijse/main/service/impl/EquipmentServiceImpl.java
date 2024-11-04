package lk.ijse.main.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.main.customObj.EquipmentErrorResponse;
import lk.ijse.main.customObj.EquipmentResponse;
import lk.ijse.main.dao.EquipmentDao;
import lk.ijse.main.dto.EquipmentDTO;
import lk.ijse.main.entity.Equipment;
import lk.ijse.main.exception.DataPersistFailedException;
import lk.ijse.main.exception.EquipmentNotFoundException;
import lk.ijse.main.service.EquipmentService;
import lk.ijse.main.util.Mapping;
import lk.ijse.main.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EquipmentServiceImpl implements EquipmentService {

    @Autowired
    private EquipmentDao equipmentDao;
    @Autowired
    private Mapping mapping;

    @Override
    public void saveEquipment(EquipmentDTO equipmentDTO) {
    equipmentDTO.setEquipmentCode(Util.createEquipmentCode());
    var EquipmentEntity = mapping.convertToEquipmentEntity(equipmentDTO);
    var savedEquipment = equipmentDao.save(EquipmentEntity);
    if(savedEquipment == null){
        throw new DataPersistFailedException("Cannot data saved");
    }
    }

    @Override
    public void updateEquipment(String equipmentCode, EquipmentDTO equipmentDTO) {
        Optional<Equipment> tmpEquipmentEntity =
                equipmentDao.findById(equipmentCode);
        if (!tmpEquipmentEntity.isPresent()){
            throw new EquipmentNotFoundException("Equipment not Found");
        }else {
            tmpEquipmentEntity.get().setStatus(equipmentDTO.getStatus());
            tmpEquipmentEntity.get().setName(equipmentDTO.getName());
            tmpEquipmentEntity.get().setType(equipmentDTO.getType());

        }
    }

    @Override
    public void deleteEquipment(String equipmentCode) {
        Optional<Equipment> findId = equipmentDao.findById(equipmentCode);
        if(findId.isPresent()){
            throw new EquipmentNotFoundException("Equipment not Found");
        }else {
            equipmentDao.deleteById(equipmentCode);
        }
    }

    @Override
    public List<EquipmentDTO> getAllEquipment() {
        return mapping.convertToEquipmentDTO(equipmentDao.findAll());
    }

    @Override
    public EquipmentResponse getSelectEquipment(String equipmentCode) {
//        if(equipmentDao.existsById(equipmentCode)){
//            return mapping.convertToEquipmentDTO(equipmentDao.getReferenceById(equipmentCode));
//        }else {
//            return new EquipmentErrorResponse(0,"Note not found");
//        }
        return null;
    }
}