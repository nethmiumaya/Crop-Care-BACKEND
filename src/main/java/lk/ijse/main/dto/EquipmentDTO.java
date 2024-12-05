package lk.ijse.main.dto;

import jakarta.validation.constraints.NotEmpty;
import lk.ijse.main.customObj.EquipmentResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EquipmentDTO implements SuperDTO, EquipmentResponse {
    private String equipmentCode;
    @NotEmpty(message = "Name is required")
    private String name;
    @NotEmpty(message = "Type is required")
    private String type;
    @NotEmpty(message = "Status is required")
    private String status;
}
