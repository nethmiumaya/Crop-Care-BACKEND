package lk.ijse.main.service.impl;

import lk.ijse.main.entity.Crop;
import lk.ijse.main.entity.Field;
import lk.ijse.main.entity.Staff;
import lk.ijse.main.exception.FieldNotFoundException;
import lk.ijse.main.exception.StaffNotFoundException;
import lk.ijse.main.repository.CropRepository;
import lk.ijse.main.repository.FieldRepository;
import lk.ijse.main.repository.StaffRepository;
import org.springframework.transaction.annotation.Transactional;
import lk.ijse.main.customObj.MonitoryLogResponse;
import lk.ijse.main.dto.MonitoryLogDTO;
import lk.ijse.main.entity.MonitoringLog;
import lk.ijse.main.exception.MonitoryLogException;
import lk.ijse.main.repository.MonitoryLogRepository;
import lk.ijse.main.service.MonitoryLogService;
import lk.ijse.main.util.Mapping;
import lk.ijse.main.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MonitoryLogServiceImpl implements MonitoryLogService {

    private final MonitoryLogRepository monitoryLogRepository;
    private final Mapping mapping;
    private final StaffRepository staffRepository;
    private final FieldRepository fieldRepository;
    private final CropRepository cropRepository;

    @Override
    public void saveMonitoryLog(MonitoryLogDTO monitoryLogDTO) {
        monitoryLogDTO.setLogCode(Util.createMonitoryLogCode());
        MonitoringLog monitoringLog = mapping.convertToMonitoryLogEntity(monitoryLogDTO);
        Field field = fieldRepository.findById(monitoryLogDTO.getFieldId())
                .orElseThrow(() -> new FieldNotFoundException("Field not found"));
        monitoringLog.setField(field);
       monitoryLogRepository.save(monitoringLog);
    }

    @Override
    public void updateMonitoryLog(MonitoryLogDTO monitoryLogDTO) {
        Optional<MonitoringLog> tmpLogEntity = monitoryLogRepository.findById(monitoryLogDTO.getLogCode());
        if (!tmpLogEntity.isPresent()) {
            throw new MonitoryLogException("Monitory Log not found");
        } else {
            tmpLogEntity.get().setLogDate(monitoryLogDTO.getLogDate());
            tmpLogEntity.get().setObservation(monitoryLogDTO.getObservation());
            tmpLogEntity.get().setObservedImage(String.valueOf(monitoryLogDTO.getObservedImage()));
        }
    }

    @Override
    public void updateMonitoryLogStaff(List<String> staff, String logCode) {
        MonitoringLog monitoringLog = monitoryLogRepository.findById(logCode)
                .orElseThrow(() -> new MonitoryLogException("Monitory Log Not Found"));
        List<Staff> staffList = staffRepository.findAllById(staff);
        if (staffList.size() != staff.size()) {
            throw new StaffNotFoundException("One or more staff not found");
        }
        monitoringLog.setStaff(staffList);
        monitoryLogRepository.save(monitoringLog);
    }

    @Override
    public void updateMonitoryLogCrops(List<String> crops, String logCode) {
        MonitoringLog monitoringLog = monitoryLogRepository.findById(logCode)
                .orElseThrow(() -> new MonitoryLogException("Monitory Log Not Found"));
        List<Crop> crops1 = cropRepository.findAllById(crops);
        if (crops1.size() != crops.size()) {
            throw new StaffNotFoundException("One or more staff not found");
        }
        monitoringLog.setCrops(crops1);
        monitoryLogRepository.save(monitoringLog);
    }

    @Override
    public void deleteMonitoryLog(String logCode) {
        Optional<MonitoringLog> tmpLogEntity = monitoryLogRepository.findById(logCode);
        if (!tmpLogEntity.isPresent()) {
            throw new MonitoryLogException("Monitory Log not found");
        } else {
            monitoryLogRepository.deleteById(logCode);
        }
    }

    @Override
    public List<MonitoryLogDTO> getAllMonitoryLogs() {
        return mapping.convertToMonitoryLogDTO(monitoryLogRepository.findAll());
    }

    @Override
    public MonitoryLogResponse getSelectMonitoryLog(String logCode) {
        if (monitoryLogRepository.existsById(logCode)) {
            MonitoringLog log = monitoryLogRepository.findById(logCode).orElse(null);
            return mapping.convertToMonitoryLogDTO(log);
        } else {
            throw new MonitoryLogException("Monitory Log not found");
        }
    }
}