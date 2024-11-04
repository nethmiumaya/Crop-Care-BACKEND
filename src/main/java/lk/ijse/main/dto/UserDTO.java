package lk.ijse.main.dto;

import lk.ijse.main.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO implements SuperDTO{
    private String email;
    private String password;
    private Role role;
}
