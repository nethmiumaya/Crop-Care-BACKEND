package lk.ijse.main.controller;

import lk.ijse.main.customObj.EquipmentResponse;
import lk.ijse.main.dto.EquipmentDTO;
import lk.ijse.main.exception.DataPersistFailedException;
import lk.ijse.main.exception.EquipmentNotFoundException;
import lk.ijse.main.service.EquipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/equipment")
@RequiredArgsConstructor
public class EquipmentController {

    private final EquipmentService equipmentService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createEquipment(@RequestBody EquipmentDTO equipmentDTO) {
        try {
            equipmentService.saveEquipment(equipmentDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DataPersistFailedException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{equipmentCode}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateEquipment(@PathVariable("equipmentCode") String equipmentCode, @RequestBody EquipmentDTO equipmentDTO) {
        try {
            if (equipmentDTO == null || equipmentCode == null || equipmentCode.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            equipmentService.updateEquipment(equipmentCode, equipmentDTO);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EquipmentNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{equipmentCode}")
    public ResponseEntity<Void> deleteEquipment(@PathVariable("equipmentCode") String equipmentCode) {
        try {
            equipmentService.deleteEquipment(equipmentCode);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EquipmentNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EquipmentDTO> findAll() {
        return equipmentService.getAllEquipment();
    }

    @GetMapping(value = "/{equipmentCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EquipmentResponse getEquipment(@PathVariable("equipmentCode") String equipmentCode) {
        return equipmentService.getSelectEquipment(equipmentCode);
    }
}