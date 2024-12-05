package lk.ijse.main.customObj;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MonitoryLogErrorResponse  implements MonitoryLogResponse, Serializable {
    private int errorCode;
    private String errorMessage;
}
