package lk.ijse.main.controller;

import jakarta.validation.Valid;
import lk.ijse.main.customObj.VehicleResponse;
import lk.ijse.main.dto.VehicleDTO;
import lk.ijse.main.exception.DataPersistFailedException;
import lk.ijse.main.exception.StaffNotFoundException;
import lk.ijse.main.exception.VehicleNotFoundException;
import lk.ijse.main.service.VehicleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * REST controller for managing Vehicle entities.
 */
@RestController
@RequestMapping("/api/v1/vehicles")
@RequiredArgsConstructor
@Slf4j
public class VehicleController {
    private final VehicleService vehicleService;

    /**
     * POST /api/v1/vehicles : Create a new vehicle.
     *
     * @param vehicleDTO the vehicle data transfer object
     * @return the ResponseEntity with status 201 (Created) or 400 (Bad Request) if the data is invalid
     */
    @PreAuthorize("hasAnyRole('MANAGER','ADMINISTRATIVE')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createVehicle(@Valid @RequestBody VehicleDTO vehicleDTO){
        try {
            log.info("Creating vehicle: {}", vehicleDTO);
            vehicleService.saveVehicle(vehicleDTO);
            log.info("Vehicle created successfully");
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DataPersistFailedException e) {
            log.error("Data persist failed during vehicle creation");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Internal server error during vehicle creation");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * PUT /api/v1/vehicles/{vehicleCode} : Update an existing vehicle.
     *
     * @param vehicleCode the code of the vehicle to update
     * @param vehicleDTO the vehicle data transfer object
     * @return the ResponseEntity with status 204 (No Content) or 404 (Not Found) if the vehicle does not exist
     */
    @PreAuthorize("hasAnyRole('MANAGER','ADMINISTRATIVE')")
    @PutMapping(value = "/{vehicleCode}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateVehicle(@PathVariable("vehicleCode") String vehicleCode,@RequestBody VehicleDTO vehicleDTO){
        try {
            log.info("Updating vehicle with code: {}", vehicleCode);
            if (vehicleDTO == null && (vehicleCode == null || vehicleDTO.equals(""))){
                log.error("VehicleDTO or vehicleCode is null or empty");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            vehicleService.updateVehicle(vehicleCode,vehicleDTO);
            log.info("Vehicle updated successfully");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (VehicleNotFoundException e){
            log.error("Vehicle not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            log.error("Internal server error during vehicle update");
            return new ResponseEntity<>
                    (HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    /**
     * PUT /api/v1/vehicles : Update the driver associated with a vehicle.
     *
     * @param vehicleCode the code of the vehicle to update
     * @param driverId the ID of the driver to associate with the vehicle
     * @return the ResponseEntity with status 204 (No Content) or 404 (Not Found) if the vehicle or driver does not exist
     */
    @PreAuthorize("hasAnyRole('MANAGER','ADMINISTRATIVE')")
    @PutMapping
    public ResponseEntity<Void> updateVehicleDriver(@RequestParam("vehicleCode") String vehicleCode,@RequestParam("driverId") String driverId){
        try {
            if (driverId == null && vehicleCode == null){
                log.error("VehicleCode or Staff is null or empty");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            vehicleService.updateVehicleDriver(vehicleCode,driverId);
            log.info("Vehicle Staff updated successfully");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (VehicleNotFoundException | StaffNotFoundException e){
            log.error("Vehicle or Staff not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e){
            log.error("Internal server error during vehicle staff update");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * DELETE /api/v1/vehicles/{vehicleCode} : Delete a vehicle.
     *
     * @param vehicleCode the code of the vehicle to delete
     * @return the ResponseEntity with status 204 (No Content) or 404 (Not Found) if the vehicle does not exist
     */
    @PreAuthorize("hasAnyRole('MANAGER','ADMINISTRATIVE')")
    @DeleteMapping(value = "/{vehicleCode}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable("vehicleCode") String vehicleCode){
        try {
            vehicleService.deleteVehicle(vehicleCode);
            log.info("Vehicle deleted successfully");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }  catch (VehicleNotFoundException e){
            log.error("Vehicle can not found");
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }catch (Exception e){
            log.error("Internal server error during vehicle deletion");
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
         }
    }

    /**
     * GET /api/v1/vehicles/allvehicles : Get all vehicles.
     *
     * @return the list of vehicles
     */
    @GetMapping(value = "/allvehicles",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VehicleDTO> FindAll(){
        log.info("Fetching all vehicle");
        return vehicleService.getAllVehicle();}

    /**
     * GET /api/v1/vehicles/{vehicleCode} : Get a specific vehicle by code.
     *
     * @param vehicleCode the code of the vehicle to retrieve
     * @return the ResponseEntity with the vehicle or 404 (Not Found) if the vehicle does not exist
     */
    @GetMapping(value = "/{vehicleCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public VehicleResponse getVehicle(@PathVariable("vehicleCode")String vehicleCode){
        log.info("Fetching vehicle with code: {}", vehicleCode);
        return vehicleService.getSelectVehicle(vehicleCode);
    }
}
