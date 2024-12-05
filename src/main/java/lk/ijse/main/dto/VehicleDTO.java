package lk.ijse.main.dto;

import lk.ijse.main.customObj.VehicleResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VehicleDTO implements SuperDTO, VehicleResponse {
    private String vehicleCode;
    @NotEmpty(message = "License plate number is required")
    @Pattern(regexp = "^[A-Z0-9-]+$", message = "License plate number should be alphanumeric and can include hyphens")
    private String licensePlateNo;
    @NotEmpty(message = "Vehicle category is required")
    private String vehicleCategory;
    @NotEmpty(message = "Fuel type is required")
    private String fuelType;
    @NotEmpty(message = "Status is required")
    private String status;
    @NotEmpty(message = "Staff ID is required")
    private String remarks;
    private String staffId;
}
