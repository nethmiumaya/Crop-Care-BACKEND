package lk.ijse.main.controller;

import jakarta.validation.Valid;
import lk.ijse.main.customObj.StaffResponse;
import lk.ijse.main.dto.StaffDTO;
import lk.ijse.main.exception.DataPersistFailedException;
import lk.ijse.main.exception.StaffNotFoundException;
import lk.ijse.main.service.StaffService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * REST controller for managing Staff entities.
 */
@RestController
@RequestMapping("/api/v1/staff")
@RequiredArgsConstructor
@Slf4j
public class StaffController {
    private final StaffService staffService;

    /**
     * POST /api/v1/staff : Create a new staff.
     *
     * @param staffDTO the staff data transfer object
     * @return the ResponseEntity with status 201 (Created) or 400 (Bad Request) if the data is invalid
     */
    @PreAuthorize("hasAnyRole('MANAGER','ADMINISTRATIVE')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createStaff(@Valid @RequestBody StaffDTO staffDTO) {
        try {
            log.info("Creating staff: {}", staffDTO);
            staffService.saveStaff(staffDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DataPersistFailedException e) {
            log.error("Data persist failed during staff creation");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Internal server error during staff creation");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * PUT /api/v1/staff/{id} : Update an existing staff.
     *
     * @param id the ID of the staff to update
     * @param staffDTO the staff data transfer object
     * @return the ResponseEntity with status 204 (No Content) or 404 (Not Found) if the staff does not exist
     */
    @PreAuthorize("hasAnyRole('MANAGER','ADMINISTRATIVE')")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateStaff(@PathVariable("id") String id, @RequestBody StaffDTO staffDTO) {
        log.info("Starting updateStaff method with id: {} and staffDTO: {}", id, staffDTO);
        try {
            if (staffDTO == null) {
                log.error("StaffDTO is null");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            if (id == null || id.isEmpty()) {
                log.error("Path variable id is null or empty");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            staffService.updateStaff(id,staffDTO );
            log.info("Staff updated successfully");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (StaffNotFoundException e) {
            log.error("Staff not found: ");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Error updating staff: ");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * DELETE /api/v1/staff/{id} : Delete a staff.
     *
     * @param id the ID of the staff to delete
     * @return the ResponseEntity with status 204 (No Content) or 404 (Not Found) if the staff does not exist
     */
    @PreAuthorize("hasAnyRole('MANAGER','ADMINISTRATIVE')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteStaff(@PathVariable("id") String id) {
        try {
            log.info("Deleting staff with id: {}", id);
            staffService.deleteStaff(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (StaffNotFoundException e) {
            log.error("Staff can not found: ");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Error deleting staff: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * GET /api/v1/staff/all : Get all staff.
     *
     * @return the list of staff
     */
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<StaffDTO> findAll() {
        log.info("Fetching all staff");
        return staffService.getAllStaff();
    }

    /**
     * GET /api/v1/staff/{id} : Get a specific staff by ID.
     *
     * @param id the ID of the staff to retrieve
     * @return the ResponseEntity with the staff or 404 (Not Found) if the staff does not exist
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public StaffResponse getStaff(@PathVariable("id") String id) {
        log.info("Fetching staff with id: {}", id);
        return staffService.getSelectStaff(id);
    }
}