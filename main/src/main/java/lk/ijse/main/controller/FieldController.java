package lk.ijse.main.controller;

import jakarta.validation.Valid;
import lk.ijse.main.customObj.FieldResponse;
import lk.ijse.main.dto.FieldDTO;
import lk.ijse.main.exception.DataPersistFailedException;
import lk.ijse.main.exception.FieldNotFoundException;
import lk.ijse.main.service.FieldService;
import lk.ijse.main.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

/**
 * REST controller for managing Field entities.
 */
@RestController
@RequestMapping("/api/v1/field")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin("*")
public class FieldController {

    private final FieldService fieldService;

    /**
     * POST /api/v1/field : Create a new field.
     *
     * @param fieldName the name of the field
     * @param fieldLocation the location of the field
     * @param extentSize the size of the field
     * @param fieldImage1 the first image of the field
     * @param fieldImage2 the second image of the field
     * @return the ResponseEntity with status 201 (Created) or 400 (Bad Request) if the data is invalid
     */
    @PreAuthorize("hasAnyRole('MANAGER','SCIENTIST')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> createField(
            @Valid
            @RequestPart("fieldName") String fieldName,
            @RequestPart("fieldLocation") String fieldLocation,
            @RequestPart("extentSize") String extentSize,
            @RequestPart("fieldImage1") MultipartFile fieldImage1,
            @RequestPart("fieldImage2") MultipartFile fieldImage2) {
        try {
            byte[] image1Bytes = fieldImage1.getBytes();
            byte[] image2Bytes = fieldImage2.getBytes();
            String base64Image1 = Util.toBase64ProfilePic(image1Bytes);
            String base64Image2 = Util.toBase64ProfilePic(image2Bytes);

            FieldDTO fieldDTO = new FieldDTO();
            fieldDTO.setFieldName(fieldName);
            fieldDTO.setFieldLocation(fieldLocation);
            fieldDTO.setExtentSize(extentSize);
            fieldDTO.setFieldImage1(base64Image1);
            fieldDTO.setFieldImage2(base64Image2);
            fieldService.saveField(fieldDTO);
            log.info("Field created successfully");
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DataPersistFailedException e) {
            log.error("Data persist failed during field creation");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Internal server error during field creation");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * PUT /api/v1/field/{fieldCode} : Update an existing field.
     *
     * @param fieldCode the code of the field to update
     * @param fieldName the name of the field
     * @param fieldLocation the location of the field
     * @param extentSize the size of the field
     * @param fieldImage1 the first image of the field
     * @param fieldImage2 the second image of the field
     * @return the ResponseEntity with status 204 (No Content) or 404 (Not Found) if the field does not exist
     */
  @PreAuthorize("hasAnyRole('MANAGER','SCIENTIST')")
    @PutMapping(value = "/{fieldCode}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateField(
            @PathVariable("fieldCode") String fieldCode,
            @RequestPart("fieldName") String fieldName,
            @RequestPart("fieldLocation") String fieldLocation,
            @RequestPart("extentSize") String extentSize,
            @RequestPart("fieldImage1") MultipartFile fieldImage1,
            @RequestPart("fieldImage2") MultipartFile fieldImage2) {
        try {
            log.info("Updating field with id: {}", fieldCode);
            byte[] image1Bytes = fieldImage1.getBytes();
            byte[] image2Bytes = fieldImage2.getBytes();
            String base64Image1 = Util.toBase64ProfilePic(image1Bytes);
            String base64Image2 = Util.toBase64ProfilePic(image2Bytes);

            FieldDTO fieldDTO = new FieldDTO();
            fieldDTO.setFieldCode(fieldCode);
            fieldDTO.setFieldName(fieldName);
            fieldDTO.setFieldLocation(fieldLocation);
            fieldDTO.setExtentSize(extentSize);
            fieldDTO.setFieldImage1(base64Image1);
            fieldDTO.setFieldImage2(base64Image2);
            fieldService.updateField(fieldDTO);
            log.info("Field updated successfully");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (FieldNotFoundException e) {
            log.error("Field not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Internal server error during field update");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * PUT /api/v1/field/{fieldCode}/staff : Update the staff associated with a field.
     *
     * @param fieldCode the code of the field to update
     * @param staffIds the list of staff IDs to associate with the field
     * @return the ResponseEntity with status 204 (No Content) or 404 (Not Found) if the field does not exist
     */
   @PreAuthorize("hasAnyRole('MANAGER','SCIENTIST')")
    @PutMapping(value = "/{fieldCode}/staff", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateStaffField(@PathVariable("fieldCode") String fieldCode, @RequestBody List<String> staffIds) {
        try {
            if (staffIds == null || staffIds.isEmpty() || fieldCode == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            fieldService.updateStaffField(staffIds, fieldCode);
            log.info("Field Staff updated successfully");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (FieldNotFoundException e) {
            log.error("Field cannot found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Internal server error during field staff update");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * DELETE /api/v1/field/{fieldCode} : Delete a field.
     *
     * @param fieldCode the code of the field to delete
     * @return the ResponseEntity with status 204 (No Content) or 404 (Not Found) if the field does not exist
     */
    @PreAuthorize("hasAnyRole('MANAGER','SCIENTIST')")
    @DeleteMapping(value = "/{fieldCode}")
    public ResponseEntity<Void> deleteField(@PathVariable("fieldCode") String fieldCode) {
        try {
            log.info("Deleting field with code: {}", fieldCode);
            fieldService.deleteField(fieldCode);
            log.info("Field deleted successfully");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (FieldNotFoundException e) {
            log.error("Field can't found ");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Internal server error during field deletion");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * GET /api/v1/field/all : Get all fields.
     *
     * @return the list of fields
     */
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FieldDTO> findAll() {
        log.info("Fetching field with id");
        return fieldService.getAllField();
    }

    /**
     * GET /api/v1/field/{fieldCode} : Get a specific field by code.
     *
     * @param fieldCode the code of the field to retrieve
     * @return the ResponseEntity with the field or 404 (Not Found) if the field does not exist
     */
    @GetMapping(value = "/{fieldCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public FieldResponse getField(@PathVariable("fieldCode") String fieldCode) {
        log.info("Fetching all fields");
        return fieldService.getSelectField(fieldCode);
    }
}