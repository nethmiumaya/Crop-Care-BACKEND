package lk.ijse.main.controller;

import jakarta.validation.Valid;
import lk.ijse.main.customObj.EquipmentResponse;
import lk.ijse.main.dto.EquipmentDTO;
import lk.ijse.main.exception.DataPersistFailedException;
import lk.ijse.main.exception.EquipmentNotFoundException;
import lk.ijse.main.service.EquipmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * REST controller for managing Equipment entities.
 */
@RestController
@RequestMapping("/api/v1/equipment")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
public class EquipmentController {

    private final EquipmentService equipmentService;

    /**
     * POST /api/v1/equipment : Create a new equipment.
     *
     * @param equipmentDTO the equipment data transfer object
     * @return the ResponseEntity with status 201 (Created) or 400 (Bad Request) if the data is invalid
     */

    @PreAuthorize("hasAnyRole('MANAGER','ADMINISTRATIVE')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createEquipment(@Valid @RequestBody EquipmentDTO equipmentDTO) {
        try {
            log.info("Creating equipment: {}", equipmentDTO);
            equipmentService.saveEquipment(equipmentDTO);
            log.info("Equipment created successfully");
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DataPersistFailedException e) {
            log.error("Data persist failed during equipment creation");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Internal server error during equipment creation");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * PUT /api/v1/equipment/{equipmentCode} : Update an existing equipment.
     *
     * @param equipmentCode the code of the equipment to update
     * @param equipmentDTO the equipment data transfer object
     * @return the ResponseEntity with status 204 (No Content) or 404 (Not Found) if the equipment does not exist
     */

    @PreAuthorize("hasAnyRole('MANAGER','ADMINISTRATIVE')")
    @PutMapping(value = "/{equipmentCode}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateEquipment(@PathVariable("equipmentCode") String equipmentCode, @RequestBody EquipmentDTO equipmentDTO) {
        try {
            log.info("Updating equipment with code: {}", equipmentCode);
            if (equipmentDTO == null || equipmentCode == null || equipmentCode.isEmpty()) {
                log.error("EquipmentDTO or equipmentCode is null or empty");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            equipmentService.updateEquipment(equipmentCode, equipmentDTO);
            log.info("Equipment updated successfully");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EquipmentNotFoundException e) {
            log.error("Equipment not found: ");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Internal server error during equipment update");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * PUT /api/v1/equipment/updateField : Update the field associated.
     *
     * @param equipmentCode the code of the equipment to update
     * @param fieldCode the new field code
     * @return the ResponseEntity with status 204 (No Content) or 404 (Not Found) if the equipment does not exist
     */
    @PreAuthorize("hasAnyRole('MANAGER','ADMINISTRATIVE')")
    @PutMapping(value = "/updateField")
    public ResponseEntity<Void> updateEquipmentField(@RequestParam("equipmentCode") String equipmentCode, @RequestParam("fieldCode") String fieldCode) {
        try {
            log.info("Updating equipment field with equipmentCode: {} and fieldCode: {}", equipmentCode, fieldCode);
            if (fieldCode == null || equipmentCode == null || equipmentCode.isEmpty()) {
                log.error("FieldCode or equipmentCode is null or empty");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            equipmentService.updateEquipmentField(equipmentCode, fieldCode);
            log.info("Equipment field updated successfully");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EquipmentNotFoundException e) {
            log.error("Equipment cannot found: ");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Internal server error during equipment field update", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * PUT /api/v1/equipment/updateStaff : Update the staff associated with an equipment.
     *
     * @param equipmentCode the code of the equipment to update
     * @param staffId the new staff ID
     * @return the ResponseEntity with status 204 (No Content) or 404 (Not Found) if the equipment does not exist
     */
    @PreAuthorize("hasAnyRole('MANAGER','ADMINISTRATIVE')")
    @PutMapping(value = "/updateStaff")
    public ResponseEntity<Void> updateEquipmentStaff(@RequestParam("equipmentCode") String equipmentCode, @RequestParam("staffId") String staffId) {
        try {
            log.info("Updating equipment staff with equipmentCode: {} and staffId: {}", equipmentCode, staffId);
            if (staffId == null || equipmentCode == null || equipmentCode.isEmpty()) {
                log.error("StaffId or equipmentCode is null or empty");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            equipmentService.updateEquipmentStaff(equipmentCode, staffId);
            log.info("Equipment staff updated successfully");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EquipmentNotFoundException e) {
            log.error("Equipment not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Internal server error during equipment staff update");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * DELETE /api/v1/equipment/{equipmentCode} : Delete an equipment.
     *
     * @param equipmentCode the code of the equipment to delete
     * @return the ResponseEntity with status 204 (No Content) or 404 (Not Found) if the equipment does not exist
     */
    @PreAuthorize("hasAnyRole('MANAGER','ADMINISTRATIVE')")
    @DeleteMapping(value = "/{equipmentCode}")
    public ResponseEntity<Void> deleteEquipment(@PathVariable("equipmentCode") String equipmentCode) {
        try {
            log.info("Deleting equipment with code: {}", equipmentCode);
            equipmentService.deleteEquipment(equipmentCode);
            log.info("Equipment deleted successfully");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EquipmentNotFoundException e) {
            log.error("Equipment can not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Internal server error during equipment deletion");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * GET /api/v1/equipment/allequipments : Get all equipment.
     *
     * @return the list of equipment
     */
    @GetMapping(value = "/allequipments", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EquipmentDTO> findAll() {
        log.info("Fetching all equipment");
        return equipmentService.getAllEquipment();
    }

    /**
     * GET /api/v1/equipment/{equipmentCode} : Get a specific equipment by code.
     *
     * @param equipmentCode the code of the equipment to retrieve
     * @return the ResponseEntity with the equipment or 404 (Not Found) if the equipment does not exist
     */
    @GetMapping(value = "/{equipmentCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EquipmentResponse getEquipment(@PathVariable("equipmentCode") String equipmentCode) {
        log.info("Fetching equipment with code: {}", equipmentCode);
        return equipmentService.getSelectEquipment(equipmentCode);
    }

    /**
     * GET /api/v1/equipment/field/{fieldCode} : Get all equipment in use for a specific field.
     *
     * @param fieldCode the code of the field
     * @return the ResponseEntity with the list of equipment
     */
    @GetMapping(value = "/field/{fieldCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EquipmentDTO>> getInUseFieldEquipments(@PathVariable("fieldCode") String fieldCode) {
        log.info("Request to get in-use field equipments for fieldCode: {}", fieldCode);
        List<EquipmentDTO> response = equipmentService.getFieldEquipments(fieldCode);
        log.info("In-use field equipments for fieldCode: {} retrieved successfully", fieldCode);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}