package lk.ijse.main.controller;

import lk.ijse.main.customObj.MonitoryLogResponse;
import lk.ijse.main.dto.MonitoryLogDTO;
import lk.ijse.main.exception.DataPersistFailedException;
import lk.ijse.main.exception.MonitoryLogException;
import lk.ijse.main.service.MonitoryLogService;
import lk.ijse.main.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
/**
 * REST controller for managing Monitoring Logs.
 */

@RestController
@RequestMapping("/api/v1/monitoryLog")
@RequiredArgsConstructor
public class MonitoryLogController {

    private final MonitoryLogService monitoryLogService;


    /**
     * POST /api/v1/monitoryLog : Create a new monitoring log.
     *
     * @param logDate the date of the log
     * @param observation the observation details
     * @param observedImage the image associated with the log
     * @param fieldId the ID of the field
     * @return the ResponseEntity with status 201 (Created) or 400 (Bad Request) if the data is invalid
     */
    @PreAuthorize("hasAnyRole('MANAGER','SCIENTIST')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> createMonitoryLog(
            @RequestPart("logDate") String logDate,
            @RequestPart("observation") String observation,
            @RequestPart("observedImage") MultipartFile observedImage,
            @RequestPart("fieldId") String fieldId) {
        try {

            byte[] imageBytes = observedImage.getBytes();
            String base64Image = Util.toBase64ProfilePic(imageBytes);

            MonitoryLogDTO monitoryLogDTO = new MonitoryLogDTO();
            monitoryLogDTO.setLogDate(Util.parseDate(logDate));
            monitoryLogDTO.setObservation(observation);
            monitoryLogDTO.setObservedImage(base64Image);
            monitoryLogDTO.setFieldId(fieldId);
            monitoryLogService.saveMonitoryLog(monitoryLogDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DataPersistFailedException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /**
     * PUT /api/v1/monitoryLog/{logCode} : Update an existing monitoring log.
     *
     * @param logCode the code of the log to update
     * @param logDate the new date of the log
     * @param observation the new observation details
     * @param observedImage
     * the new image associated with the log
     * @param fieldId the new ID of the field
     * @return the ResponseEntity with status 204 (No Content) or 404 (Not Found) if the log does not exist
     */
    @PreAuthorize("hasAnyRole('MANAGER','SCIENTIST')")
    @PutMapping(value = "/{logCode}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateMonitoryLog(
            @PathVariable("logCode") String logCode,
            @RequestPart("logDate") String logDate,
            @RequestPart("observation") String observation,
            @RequestPart("observedImage") MultipartFile observedImage,
            @RequestPart("fieldId") String fieldId) {
        try {
            byte[] imageBytes = observedImage.getBytes();
            String base64Image = Util.toBase64ProfilePic(imageBytes);

            MonitoryLogDTO monitoryLogDTO = new MonitoryLogDTO();
            monitoryLogDTO.setLogCode(logCode);
            monitoryLogDTO.setLogDate(Util.parseDate(logDate));
            monitoryLogDTO.setObservation(observation);
            monitoryLogDTO.setObservedImage(base64Image);
            monitoryLogDTO.setFieldId(fieldId);

            monitoryLogService.updateMonitoryLog(monitoryLogDTO);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (MonitoryLogException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // MonitoryLogController.java
    /**
     * PUT /api/v1/monitoryLog/{logCode}/staff : Update the staff associated with a monitoring log.
     *
     * @param logCode the code of the log to update
     * @param staffIds the list of staff IDs to associate with the log
     * @return the ResponseEntity with status 204 (No Content) or 404 (Not Found) if the log does not exist
     */
    @PreAuthorize("hasAnyRole('MANAGER','SCIENTIST')")
    @PutMapping(value = "/{logCode}/staff", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateMonitoryLogStaff(@PathVariable("logCode") String logCode, @RequestBody List<String> staffIds) {
        try {
            if (staffIds == null || staffIds.isEmpty() || logCode == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            monitoryLogService.updateMonitoryLogStaff(staffIds, logCode);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (MonitoryLogException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /**
     * PUT /api/v1/monitoryLog/{logCode}/crops : Update the crops associated with a monitoring log.
     *
     * @param logCode the code of the log to update
     * @param crops the list of crop IDs to associate with the log
     * @return the ResponseEntity with status 204 (No Content) or 404 (Not Found) if the log does not exist
     */
    @PreAuthorize("hasAnyRole('MANAGER','SCIENTIST')")
    @PutMapping(value = "/{logCode}/crops", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateMonitoryLogCrops(@PathVariable("logCode") String logCode, @RequestBody List<String> crops) {
        try {
            if (crops == null || crops.isEmpty() || logCode == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            monitoryLogService.updateMonitoryLogCrops(crops, logCode);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (MonitoryLogException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /**
     * DELETE /api/v1/monitoryLog/{logCode} : Delete a monitoring log.
     *
     * @param logCode the code of the log to delete
     * @return the ResponseEntity with status 204 (No Content) or 404 (Not Found) if the log does not exist
     */
    @PreAuthorize("hasAnyRole('MANAGER','SCIENTIST')")
    @DeleteMapping(value = "/{logCode}")
    public ResponseEntity<Void> deleteMonitoryLog(@PathVariable("logCode") String logCode) {
        try {
            monitoryLogService.deleteMonitoryLog(logCode);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (MonitoryLogException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /**
     * GET /api/v1/monitoryLog/all : Get all monitoring logs.
     *
     * @return the list of monitoring logs
     */
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MonitoryLogDTO> findAll() {
        return monitoryLogService.getAllMonitoryLogs();
    }
    /**
     * GET /api/v1/monitoryLog/{logCode} : Get a specific monitoring log by code.
     *
     * @param logCode the code of the log to retrieve
     * @return the ResponseEntity with the monitoring log or 404 (Not Found) if the log does not exist
     */
    @GetMapping(value = "/{logCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public MonitoryLogResponse getMonitoryLog(@PathVariable("logCode") String logCode) {
        return monitoryLogService.getSelectMonitoryLog(logCode);
    }
}