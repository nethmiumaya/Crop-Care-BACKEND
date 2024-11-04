package lk.ijse.main.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EquipmentDTO {
    private String equipmentCode;
    private String name;
    private String type;
    private String status;
    private String assignedStaffDetails;
    private String assignedFieldDetails;
}
