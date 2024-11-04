package lk.ijse.main.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.main.customObj.StaffResponse;
import lk.ijse.main.customObj.StaffErrorResponse;
import lk.ijse.main.dto.StaffDTO;
import lk.ijse.main.entity.Gender;
import lk.ijse.main.entity.Role;
import lk.ijse.main.entity.Staff;
import lk.ijse.main.exception.DataPersistFailedException;
import lk.ijse.main.exception.StaffNotFoundException;
import lk.ijse.main.repository.StaffRepository;
import lk.ijse.main.service.StaffService;
import lk.ijse.main.util.Mapping;
import lk.ijse.main.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class StaffServiceImpl implements StaffService {

    private final StaffRepository staffRepository;
    private final Mapping mapping;

    @Override
    public void saveStaff(StaffDTO dto) {
        dto.setId(Util.createStaffId());
        Staff savedStaff = staffRepository.save(mapping.convertToStaffEntity(dto));
        if (savedStaff == null || savedStaff.getId() == null) {
            throw new DataPersistFailedException("Staff not saved");
        }
    }

    @Override
    public void updateStaff(StaffDTO dto) {
        Optional<Staff> tmpStaff = staffRepository.findById(dto.getId());
        if (!tmpStaff.isPresent()) {
            throw new StaffNotFoundException("Staff not found");
        } else {
            tmpStaff.get().setFirstName(dto.getFirstName());
            tmpStaff.get().setLastName(dto.getLastName());
            tmpStaff.get().setDesignation(dto.getDesignation());
            tmpStaff.get().setGender(Gender.valueOf(dto.getGender()));
            tmpStaff.get().setJoinDate(dto.getJoinDate());
            tmpStaff.get().setDOB(dto.getDOB());
            tmpStaff.get().setAddLine01(dto.getAddLine01());
            tmpStaff.get().setAddLine02(dto.getAddLine02());
            tmpStaff.get().setAddLine03(dto.getAddLine03());
            tmpStaff.get().setAddLine04(dto.getAddLine04());
            tmpStaff.get().setAddLine05(dto.getAddLine05());
            tmpStaff.get().setConNo(dto.getConNo());
            tmpStaff.get().setEmail(dto.getEmail());
            tmpStaff.get().setRole(Role.valueOf(dto.getRole()));
        }
    }

    @Override
    public void deleteStaff(String id) {
        Optional<Staff> findId = staffRepository.findById(id);
        if (!findId.isPresent()) {
            throw new StaffNotFoundException("Staff not found");
        } else {
            staffRepository.deleteById(id);
        }
    }

    @Override
    public List<StaffDTO> getAllStaff() {
        return mapping.convertToStaffDTO(staffRepository.findAll());
    }

    @Override
    public StaffResponse getSelectStaff(String id) {
        if (staffRepository.existsById(id)) {
            return mapping.convertToStaffDTO(staffRepository.getReferenceById(id));
        } else {
            return new StaffErrorResponse(0, "Staff not found");
        }
    }
}