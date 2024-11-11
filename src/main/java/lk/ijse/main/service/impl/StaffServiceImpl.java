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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class StaffServiceImpl implements StaffService {

    private static final Logger logger = LoggerFactory.getLogger(StaffServiceImpl.class);
    private final StaffRepository staffRepository;
    private final Mapping mapping;

    @Override
    public void saveStaff(StaffDTO dto) {
        System.out.println(dto.getGender() + "GAYANUKA");
        dto.setId(Util.createStaffId());
        Staff savedStaff = mapping.convertToStaffEntity(dto);
        savedStaff.setRole(Role.valueOf(dto.getRole()));
        savedStaff.setGender(Gender.valueOf(dto.getGender()));
        try {
            staffRepository.save(savedStaff);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error saving staff: ", e);
            throw new DataPersistFailedException("Error saving staff");
        }
    }

    @Override
    public void updateStaff(String id,StaffDTO dto) {
        logger.info("Starting updateStaff method");
        try {
            Optional<Staff> tmpStaff = staffRepository.findById(id);
            if (!tmpStaff.isPresent()) {
                logger.error("StaffNotFoundException: Staff not found");
                throw new StaffNotFoundException("Staff not found");
            } else {
                Staff staff = tmpStaff.get();
                staff.setFirstName(dto.getFirstName());
                staff.setLastName(dto.getLastName());
                staff.setDesignation(dto.getDesignation());
                staff.setGender(Gender.valueOf(dto.getGender()));
                staff.setJoinDate(dto.getJoinDate());
                staff.setDOB(dto.getDOB());
                staff.setAddLine01(dto.getAddLine01());
                staff.setAddLine02(dto.getAddLine02());
                staff.setAddLine03(dto.getAddLine03());
                staff.setAddLine04(dto.getAddLine04());
                staff.setAddLine05(dto.getAddLine05());
                staff.setConNo(dto.getConNo());
                staff.setEmail(dto.getEmail());
                staff.setRole(Role.valueOf(dto.getRole()));
                staffRepository.save(staff);
            }
    }catch (Exception e) {
        throw e;
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