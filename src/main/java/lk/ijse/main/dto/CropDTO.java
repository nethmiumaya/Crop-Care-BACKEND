package lk.ijse.main.dto;

import jakarta.validation.constraints.NotEmpty;
import lk.ijse.main.customObj.CropResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CropDTO implements SuperDTO , CropResponse {
    private String code;
    @NotEmpty(message = "Common name is required")
    private String commonName;
    @NotEmpty(message = "Scientific name is required")
    private String scientificName;
    private String cropImage;
    @NotEmpty(message = "Category is required")
    private String category;
    @NotEmpty(message = "Season is required")
    private String season;
    @NotEmpty(message = "Field ID is required")
    private String fieldId;
    private List<MonitoryLogDTO> monitoringLogs;
}
