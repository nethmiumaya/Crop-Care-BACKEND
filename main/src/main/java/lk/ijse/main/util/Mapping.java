package lk.ijse.main.util;

import lk.ijse.main.dto.*;
import lk.ijse.main.entity.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class Mapping {
    @Autowired
    private ModelMapper modelMapper;

    public VehicleDTO convertToVehicleDTO(Vehicle vehicle) {
        return modelMapper.map(vehicle, VehicleDTO.class);
    }

    public Vehicle convertToVehicleEntity(VehicleDTO dto) {
        return modelMapper.map(dto, Vehicle.class);
    }

    public List<VehicleDTO> convertToVehicleDTO(List<Vehicle> vehicles) {
        return modelMapper.map(vehicles, new TypeToken<List<VehicleDTO>>() {
        }.getType());
    }

    public UserDTO convertToUserDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    public User convertToUserEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    public List<UserDTO> convertToUserDTO(List<User> users) {
        return modelMapper.map(users, new TypeToken<List<UserDTO>>() {
        }.getType());
    }

    public EquipmentDTO convertToEquipmentDTO(Equipment equipment) {
        return modelMapper.map(equipment, EquipmentDTO.class);
    }

    public Equipment convertToEquipmentEntity(EquipmentDTO dto) {
        return modelMapper.map(dto, Equipment.class);
    }

    public List<EquipmentDTO> convertToEquipmentDTO(List<Equipment> equipment) {
        return modelMapper.map(equipment, new TypeToken<List<EquipmentDTO>>() {
        }.getType());
    }

    public StaffDTO convertToStaffDTO(Staff staff) {
        return modelMapper.map(staff, StaffDTO.class);
    }

    public Staff convertToStaffEntity(StaffDTO dto) {
        return modelMapper.map(dto, Staff.class);
    }

    public List<StaffDTO> convertToStaffDTO(List<Staff> staff) {
        return modelMapper.map(staff, new TypeToken<List<StaffDTO>>() {
        }.getType());
    }

    public CropDTO convertToCropDTO(Crop crop) {
        return modelMapper.map(crop, CropDTO.class);
    }

    public Crop convertToCropEntity(CropDTO dto) {
        return modelMapper.map(dto, Crop.class);
    }

    public List<CropDTO> convertToCropDTO(List<Crop> crops) {
        return modelMapper.map(crops, new TypeToken<List<CropDTO>>() {
        }.getType());
    }

    public FieldDTO convertToFieldDTO(Field field) {
        return modelMapper.map(field, FieldDTO.class);
    }

    public Field convertToFieldEntity(FieldDTO dto) {
        return modelMapper.map(dto, Field.class);
    }

    public List<FieldDTO> convertToFieldDTO(List<Field> fields) {
        return modelMapper.map(fields, new TypeToken<List<FieldDTO>>() {
        }.getType());
    }

    public MonitoryLogDTO convertToMonitoryLogDTO(MonitoringLog monitoringLog) {
        return modelMapper.map(monitoringLog, MonitoryLogDTO.class);
    }

    public MonitoringLog convertToMonitoryLogEntity(MonitoryLogDTO monitoryLogDTO) {
        return modelMapper.map(monitoryLogDTO, MonitoringLog.class);
    }

    public List<MonitoryLogDTO> convertToMonitoryLogDTO(List<MonitoringLog> monitoryLogDTOS) {
        return modelMapper.map(monitoryLogDTOS, new TypeToken<List<MonitoryLogDTO>>() {
        }.getType());
    }
}
