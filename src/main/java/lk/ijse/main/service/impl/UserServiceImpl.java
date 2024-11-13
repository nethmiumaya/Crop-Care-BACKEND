package lk.ijse.main.service.impl;

import lk.ijse.main.customObj.UserErrorResponse;
import lk.ijse.main.customObj.UserResponse;
import lk.ijse.main.dto.UserDTO;
import lk.ijse.main.entity.User;
import lk.ijse.main.exception.UserNotFoundException;
import lk.ijse.main.repository.UserRepository;
import lk.ijse.main.service.UserService;
import lk.ijse.main.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final Mapping mapping;

    @Override
    public void updateUser(UserDTO userDTO) {
        Optional<User> tmpUser = userRepository.findById(userDTO.getEmail());
        if (!tmpUser.isPresent()) throw new UserNotFoundException("User not found");
        else {
            User userEntity = tmpUser.get();
            userEntity.setEmail(userDTO.getEmail());
            userEntity.setPassword(userDTO.getPassword());
           userEntity.setRole(userDTO.getRole());
            // bcz of the @Transactional annotation, we don't need to call the save method
        }
    }

    @Override
    public void deleteUser(String userId) {
        Optional<User> selectedUser = userRepository.findById(userId);
        if (!selectedUser.isPresent()) {
            throw new UserNotFoundException("User not found");
        } else userRepository.deleteById(userId);
    }

    @Override
    public UserResponse getSelectedUser(String userId) {
        return (userRepository.existsById(userId))
                ? mapping.convertToUserDTO(userRepository.getReferenceById(userId))
                : new UserErrorResponse(0, "User not found");
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return mapping.convertToUserDTO(userRepository.findAll());
    }


    @Override
    public UserDetailsService userDetailsService() {
     return email -> userRepository.findByEmail(email)
             .orElseThrow(() -> new UserNotFoundException("User not found"));

      }
}
