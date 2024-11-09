// MonitoryLogController.java
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/monitoryLog")
@RequiredArgsConstructor
public class MonitoryLogController {

    private final MonitoryLogService monitoryLogService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> createMonitoryLog(
            @RequestPart("logDate") String logDate,
            @RequestPart("observation") String observation,
            @RequestPart("logImage") MultipartFile logImage,
            @RequestPart("fieldId") String fieldId) {
        try {
            byte[] imageBytes = logImage.getBytes();
            String base64Image = Util.toBase64ProfilePic(imageBytes);

            MonitoryLogDTO monitoryLogDTO = new MonitoryLogDTO();
            monitoryLogDTO.setLogDate(logDate);
            monitoryLogDTO.setObservation(observation);
            monitoryLogDTO.setLogImage(logImage);
            monitoryLogDTO.setFieldId(fieldId);


            monitoryLogService.saveMonitoryLog(monitoryLogDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DataPersistFailedException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{logCode}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateMonitoryLog(
            @PathVariable("logCode") String logCode,
            @RequestPart("logDate") String logDate,
            @RequestPart("observation") String observation,
            @RequestPart("logImage") MultipartFile logImage,
            @RequestPart("fieldId") String fieldId) {
        try {
            byte[] imageBytes = logImage.getBytes();
            String base64Image = Util.toBase64ProfilePic(imageBytes);

            MonitoryLogDTO monitoryLogDTO = new MonitoryLogDTO();
            monitoryLogDTO.setLogCode(logCode);
            monitoryLogDTO.setLogDate(logDate);
            monitoryLogDTO.setObservation(observation);
            monitoryLogDTO.setLogImage(logImage);
            monitoryLogDTO.setFieldId(fieldId);

            monitoryLogService.updateMonitoryLog(monitoryLogDTO);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (MonitoryLogException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

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

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MonitoryLogDTO> findAll() {
        return monitoryLogService.getAllMonitoryLogs();
    }

    @GetMapping(value = "/{logCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public MonitoryLogResponse getMonitoryLog(@PathVariable("logCode") String logCode) {
        return monitoryLogService.getSelectMonitoryLog(logCode);
    }
}