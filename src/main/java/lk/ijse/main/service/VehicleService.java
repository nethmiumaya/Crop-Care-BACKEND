package lk.ijse.main.service;

import lk.ijse.main.customObj.VehicleResponse;
import lk.ijse.main.dto.VehicleDTO;

import java.util.List;

public interface VehicleService {
    void saveVehicle(VehicleDTO vehicleDTO);
    void updateVehicle(String vehicleCode, VehicleDTO vehicleDTO);
    void updateVehicleDriver(String vehicleCode, String driverId);
    void deleteVehicle(String vehicleCode);
    List<VehicleDTO> getAllVehicle();
    VehicleResponse getSelectVehicle(String vehicleCode);
}
