
package lk.ijse.main.dto;

import jakarta.validation.constraints.NotEmpty;
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
  @NotEmpty(message = "Observation is required")
    private String observation;
    private String observedImage;
  @NotEmpty(message = "Field ID is required")
    private String fieldId;
    private List<CropDTO> crops;
    private List<StaffDTO> staff;
}