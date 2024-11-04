package lk.ijse.main.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.main.customObj.VehicleErrorResponse;
import lk.ijse.main.customObj.VehicleResponse;
import lk.ijse.main.dao.VehicleDao;
import lk.ijse.main.dto.VehicleDTO;
import lk.ijse.main.entity.Vehicle;
import lk.ijse.main.exception.DataPersistFailedException;
import lk.ijse.main.exception.VehicleNotFound;
import lk.ijse.main.service.VehicleService;
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
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private  VehicleDao vehicleDao;

    @Autowired
    private Mapping mapping;

    @Override
    public void saveVehicle(VehicleDTO vehicleDTO) {

        vehicleDTO.setVehicleCode(Util.createVehicleCode());
        Vehicle savedVehicle =
                vehicleDao.save(mapping.convertToVehicleEntity(vehicleDTO));
        if (savedVehicle == null && savedVehicle.getVehicleCode() == null){
            throw new DataPersistFailedException("Cannot data saved");
        }
    }

    @Override
    public void updateVehicle(String vehicleCode,VehicleDTO vehicleDTO) {
        Optional<Vehicle> tmpvehicleEntity =
                vehicleDao.findById(vehicleCode);

        if (!tmpvehicleEntity.isPresent()){
            throw new VehicleNotFound("Vehicle not Found");
        }else {
            tmpvehicleEntity.get().setVehicleCategory(vehicleDTO.getVehicleCategory());
            tmpvehicleEntity.get().setStatus(vehicleDTO.getStatus());
            tmpvehicleEntity.get().setRemarks(vehicleDTO.getRemarks());
            tmpvehicleEntity.get().setFuelType(vehicleDTO.getFuelType());
            tmpvehicleEntity.get().setLicensePlateNo(vehicleDTO.getLicensePlateNo());
        }
    }

    @Override
    public void deleteVehicle(String vehicleCode) {
        Optional<Vehicle> findId = vehicleDao.findById(vehicleCode);
        if (!findId.isPresent()){
            throw new VehicleNotFound("Vehicle not found");
        }else {
            vehicleDao.deleteById(vehicleCode);
        }
    }

    @Override
    public List<VehicleDTO> getAllVehicle() {
        return mapping.convertToVehicleDTO(vehicleDao.findAll());
    }

    @Override
    public VehicleResponse getSelectVehicle(String vehicleCode) {
        if (vehicleDao.existsById(vehicleCode)){
            return mapping.convertToVehicleDTO(vehicleDao.getReferenceById(vehicleCode));
        }else {
            return new VehicleErrorResponse(0,"VEHICLE NOTE FOUND");
        }

    }
}