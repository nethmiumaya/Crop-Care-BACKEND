package lk.ijse.main.dto;

import lk.ijse.main.customObj.FieldResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FieldDTO implements SuperDTO, FieldResponse {
    private String fieldCode;
    private String fieldName;
    private String fieldLocation;
    private String extentSize;
    private String fieldImage1;
    private String fieldImage2;
//    private List<CropDTO> cropDTOS;
//    private List<StaffDTO> staffDTOS;
//    private List<EquipmentDTO> equipmentDTOS;
//    private List<MonitoryLogDTO> monitoringLogDTOS;
}
