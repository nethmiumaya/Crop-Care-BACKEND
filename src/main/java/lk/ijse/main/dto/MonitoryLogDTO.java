// MonitoryLogDTO.java
package lk.ijse.main.dto;

import lk.ijse.main.customObj.MonitoryLogResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MonitoryLogDTO implements SuperDTO, MonitoryLogResponse {
    private String logCode;
    private String logDate;
    private String observation;
    private MultipartFile logImage;
    private String fieldId;
    private List<CropDTO> crops;
    private List<StaffDTO> staff;
}