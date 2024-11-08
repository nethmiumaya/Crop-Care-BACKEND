package lk.ijse.main.dto;

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
    private String commonName;
    private String scientificName;
    private String cropImage;
    private String category;
    private String season;
    private String fieldId;
    private List<MonitoryLogDTO> monitoringLogs;
}
