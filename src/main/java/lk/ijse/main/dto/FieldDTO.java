package lk.ijse.main.dto;

import jakarta.validation.constraints.NotEmpty;
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
 @NotEmpty(message = "Field name is required")
    private String fieldName;
 @NotEmpty(message = "Field location is required")
    private String fieldLocation;
  @NotEmpty(message = "Extent size is required")
    private String extentSize;
    private String fieldImage1;
    private String fieldImage2;
}
