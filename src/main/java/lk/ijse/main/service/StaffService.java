package lk.ijse.main.service;
import lk.ijse.main.customObj.StaffResponse;
import lk.ijse.main.dto.StaffDTO;
import java.util.List;

public interface StaffService {
    void saveStaff(StaffDTO dto);
    void updateStaff(String id,StaffDTO dto);
    void deleteStaff(String id);
    List<StaffDTO> getAllStaff();
    StaffResponse getSelectStaff(String id);
}
