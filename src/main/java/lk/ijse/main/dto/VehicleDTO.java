package lk.ijse.main.dto;


import lk.ijse.main.customObj.VehicleResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VehicleDTO implements SuperDTO, VehicleResponse {
    private String vehicleCode;
    private String licensePlateNo;
    private String vehicleCategory;
    private String fuelType;
    private String status;
    private String Remarks;
    private String staffId;
}
