package lk.ijse.main.controller;

import lk.ijse.main.customObj.UserResponse;
import lk.ijse.main.dto.UserDTO;
import lk.ijse.main.exception.UserNotFoundException;
import lk.ijse.main.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * REST controller for managing User entities.
 */
@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private  final UserService userService;
    private final PasswordEncoder passwordEncoder;

    /**
     * PATCH /api/v1/user/{email} : Update an existing user.
     *
     * @param email the email of the user to update
     * @param userDTO the user data transfer object
     * @return the ResponseEntity with status 204 (No Content) or 400 (Bad Request) if the data is invalid
     */
    @PatchMapping(value = "/{email}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateUser(@PathVariable("email") String email, @RequestBody UserDTO userDTO) {
        try {
            if (userDTO == null || email == null || email.isEmpty()) {
                log.error("UserDTO or email is null or empty");
                return ResponseEntity.badRequest().build();
            }
            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            userService.updateUser(userDTO);
            log.info("User updated successfully with email: {}", email);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Internal server error during user update", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * DELETE /api/v1/user/{email} : Delete a user.
     *
     * @param userId the email of the user to delete
     * @return the ResponseEntity with status 204 (No Content) or 404 (Not Found) if the user does not exist
     */
    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteUser(@PathVariable("email") String userId) {
        try {
            userService.deleteUser(userId);
            log.info("User deleted successfully with email: {}", userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (UserNotFoundException e) {
            log.error("User not found with email: {}", userId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Internal server error during user deletion");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * GET /api/v1/user/{email} : Get a specific user by email.
     *
     * @param email the email of the user to retrieve
     * @return the ResponseEntity with the user or 404 (Not Found) if the user does not exist
     */
    @GetMapping(value = "/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> getUser(@PathVariable("email") String email) {
        try {
            UserResponse selectedUser = userService.getSelectedUser(email);
            log.info("User fetched successfully with email: {}", email);
            return ResponseEntity.status(HttpStatus.OK).body(selectedUser);
        } catch (Exception e) {
            log.error("Internal server error during fetching user with email: {}", email, e);
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * GET /api/v1/user/all : Get all users.
     *
     * @return the ResponseEntity with the list of users
     */
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        try {
            List<UserDTO> users = userService.getAllUsers();
            log.info("All users fetched successfully");
            return ResponseEntity.status(HttpStatus.OK).body(users);
        } catch (Exception e) {
            log.error("Internal server error during fetching all users", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}