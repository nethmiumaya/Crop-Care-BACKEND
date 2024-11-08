package lk.ijse.main.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FieldDTO implements SuperDTO{
    private String fieldCode;
    private String fieldName;
    private String fieldLocation;
    private String extentSize;
    private MultipartFile fieldImage1;
    private MultipartFile fieldImage2;
    private List<CropDTO> cropDTOS;
    private List<StaffDTO> staffDTOS;
    private List<EquipmentDTO> equipmentDTOS;
    private List<MonitoryLogDTO> monitoringLogDTOS;
}
