package lk.ijse.main.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lk.ijse.main.customObj.StaffResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StaffDTO implements SuperDTO, StaffResponse {
    private String id;
    @NotEmpty(message = "First name is required")
    @Size(min = 2, max = 50, message = "Customer name must be between 2 and 50 characters")
    private String firstName;
    @NotEmpty(message = "Last name is required")
    @Size(min = 2, max = 50, message = "Customer name must be between 2 and 50 characters")
    private String lastName;
    @NotEmpty(message = "Designation is required")
    private String designation;
    @NotEmpty(message = "Gender is required")
    private String gender;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    private Date joinDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    private Date DOB;
    private String addLine01;
    @NotEmpty(message = "Contact number is required")
    @Pattern(regexp = "^[0-9]{3}-[0-9]{7}$", message = "Contact number should be in the format of 071-1234567")
    private String conNo;
    @NotEmpty(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;
    @NotEmpty(message = "Role is required")
    private String role;
}