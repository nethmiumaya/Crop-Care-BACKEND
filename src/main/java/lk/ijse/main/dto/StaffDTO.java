package lk.ijse.main.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lk.ijse.main.customObj.StaffResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StaffDTO implements SuperDTO, StaffResponse {
    private String id;
    private String firstName;
    private String lastName;
    private String designation;
    private String gender;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    private Date joinDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    private Date DOB;
    private String addLine01;
    private String addLine02;
    private String addLine03;
    private String addLine04;
    private String addLine05;
    private String conNo;
    private String email;
    private String role;
}