
package lk.ijse.main.dto;

import lk.ijse.main.customObj.MonitoryLogResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MonitoryLogDTO implements SuperDTO, MonitoryLogResponse {
    private String logCode;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date logDate;
    private String observation;
    private String observedImage
            ;
    private String fieldId;
    private List<CropDTO> crops;
    private List<StaffDTO> staff;
}