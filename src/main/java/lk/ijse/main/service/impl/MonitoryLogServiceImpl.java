package lk.ijse.main.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.main.customObj.MonitoryLogResponse;
import lk.ijse.main.dto.MonitoryLogDTO;
import lk.ijse.main.entity.MonitoringLog;
import lk.ijse.main.exception.DataPersistFailedException;
import lk.ijse.main.exception.MonitoryLogException;
import lk.ijse.main.repository.MonitoryLogRepository;
import lk.ijse.main.service.MonitoryLogService;
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
public class MonitoryLogServiceImpl implements MonitoryLogService {
    @Autowired
    private final MonitoryLogRepository monitoryLogRepository;

    private final Mapping mapping;

    @Override
    public void saveMonitoryLog(MonitoryLogDTO monitoryLogDTO) {
        monitoryLogDTO.setLogCode(Util.createLogCode());
        MonitoringLog savedLog = monitoryLogRepository.save(mapping.convertToMonitoryLogEntity(monitoryLogDTO));
        if (savedLog == null || savedLog.getLogCode() == null) {
            throw new DataPersistFailedException("Monitory Log not saved");
        }
    }

    @Override
    public void updateMonitoryLog(MonitoryLogDTO monitoryLogDTO) {
        Optional<MonitoringLog> tmpLogEntity = monitoryLogRepository.findById(monitoryLogDTO.getLogCode());
        if (!tmpLogEntity.isPresent()) {
            throw new MonitoryLogException("Monitory Log not found");
        } else {
            tmpLogEntity.get().setLogDate(Util.parseDate(monitoryLogDTO.getLogDate()));
            tmpLogEntity.get().setObservation(monitoryLogDTO.getObservation());
            tmpLogEntity.get().setObservedImage(String.valueOf(monitoryLogDTO.getLogImage()));
        }
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