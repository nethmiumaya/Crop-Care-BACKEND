package lk.ijse.main.service;

import lk.ijse.main.customObj.UserResponse;
import lk.ijse.main.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {
    void updateUser(UserDTO userDTO);
    void deleteUser(String userId);
    UserResponse getSelectedUser(String userId);
    List<UserDTO> getAllUsers();
    UserDetailsService userDetailsService();
}
