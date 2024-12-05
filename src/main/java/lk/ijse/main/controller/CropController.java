package lk.ijse.main.controller;

import jakarta.validation.Valid;
import lk.ijse.main.customObj.CropResponse;
import lk.ijse.main.dto.CropDTO;
import lk.ijse.main.exception.CropNotFoundException;
import lk.ijse.main.exception.DataPersistFailedException;
import lk.ijse.main.service.CropService;
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
 * REST controller for managing Crop entities.
 */

@RestController
@RequestMapping("/api/v1/crop")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class CropController {
    private final CropService cropService;


    /**
     * POST /api/v1/crop : Create a new crop.
     *
     * @param commonName     the common name of the crop
     * @param scientificName the scientific name of the crop
     * @param category       the category of the crop
     * @param season         the season of the crop
     * @param fieldId        the ID of the field where the crop is planted
     * @param cropImage      the image of the crop
     * @return the ResponseEntity with status 201 (Created) or 400 (Bad Request) if the data is invalid
     */

    @PreAuthorize("hasAnyRole('MANAGER','SCIENTIST')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> createCrop(
            @Valid
            @RequestPart("commonName") String commonName,
            @RequestPart("scientificName") String scientificName,
            @RequestPart("category") String category,
            @RequestPart("season") String season,
            @RequestPart("fieldId") String fieldId,
            @RequestPart("cropImage") MultipartFile cropImage) {
        try {
            log.info("Creating crop with commonName: {}, scientificName: {}", commonName, scientificName);
            byte[] imagebyteCollection = cropImage.getBytes();
            String base64CropImage = Util.toBase64ProfilePic(imagebyteCollection);
            CropDTO buildCropDTO = new CropDTO();
            buildCropDTO.setCommonName(commonName);
            buildCropDTO.setScientificName(scientificName);
            buildCropDTO.setCategory(category);
            buildCropDTO.setSeason(season);
            buildCropDTO.setFieldId(fieldId);
            buildCropDTO.setCropImage(base64CropImage);

            cropService.saveCrop(buildCropDTO);
            log.info("Crop created successfully");
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DataPersistFailedException e) {
            log.error("Data persist failed during crop creation");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Internal server error during crop creation");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * PUT /api/v1/crop/{cropId} : Update an existing crop.
     *
     * @param cropId         the ID of the crop to update
     * @param commonName     the new common name of the crop
     * @param scientificName the new scientific name of the crop
     * @param category       the new category of the crop
     * @param season         the new season of the crop
     * @param fieldId        the new ID of the field where the crop is planted
     * @param cropImage      the new image of the crop
     * @return the ResponseEntity with status 204 (No Content) or 404 (Not Found) if the crop does not exist
     */
    @PreAuthorize("hasAnyRole('MANAGER','SCIENTIST')")
    @PutMapping(value = "/{cropId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateCrop(
            @PathVariable("cropId") String cropId,
            @RequestPart("commonName") String commonName,
            @RequestPart("scientificName") String scientificName,
            @RequestPart("category") String category,
            @RequestPart("season") String season,
            @RequestPart("fieldId") String fieldId,
            @RequestPart("cropImage") MultipartFile cropImage
    ) {
        try {
            log.info("Updating crop with id: {}", cropId);
            byte[] imagebyteCollection = cropImage.getBytes();
            String base64CropImage = Util.toBase64ProfilePic(imagebyteCollection);

            var updateCropDto = new CropDTO();
            updateCropDto.setCode(cropId);
            updateCropDto.setCommonName(commonName);
            updateCropDto.setScientificName(scientificName);
            updateCropDto.setCategory(category);
            updateCropDto.setSeason(season);
            updateCropDto.setFieldId(fieldId);
            updateCropDto.setCropImage(base64CropImage);
            cropService.updateCrop(updateCropDto);
            log.info("Crop updated successfully");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (CropNotFoundException e) {
            log.error("Crop not found: ");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Internal server error during crop update");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * PUT /api/v1/crop : Update the field associated with a crop.
     *
     * @param cropId  the ID of the crop to update
     * @param fieldId the new ID of the field where the crop is planted
     * @return the ResponseEntity with status 204 (No Content) or 404 (Not Found) if the crop does not exist
     */
    @PreAuthorize("hasAnyRole('MANAGER','SCIENTIST')")
    @PutMapping
    public ResponseEntity<Void> updateCropField(@RequestParam("cropId") String cropId, @RequestParam("fieldId") String fieldId) {
        try {
            log.info("Updating crop field with cropId: {} and fieldId: {}", cropId, fieldId);
            if (fieldId == null && cropId == null) {
                log.error("FieldId or CropId is null");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            cropService.updateCropField(cropId, fieldId);
            log.info("Crop field updated successfully");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (CropNotFoundException e) {
            log.error("Crop cannot found: ");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Internal server error during crop field update");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * DELETE /api/v1/crop/{cropId} : Delete a crop.
     *
     * @param cropId the ID of the crop to delete
     * @return the ResponseEntity with status 204 (No Content) or 404 (Not Found) if the crop does not exist
     */
    @PreAuthorize("hasAnyRole('MANAGER','SCIENTIST')")
    @DeleteMapping(value = "/{cropId}")
    public ResponseEntity<Void> deleteCrop(@PathVariable("cropId") String cropId) {
        try {
            log.info("Deleting crop with id: {}", cropId);
            cropService.deleteCrop(cropId);
            log.info("Crop deleted successfully");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (CropNotFoundException e) {
            log.error("Crop can't found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Internal server error during crop deletion");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * GET /api/v1/crop/{cropId} : Get a specific crop by ID.
     *
     * @param cropId the ID of the crop to retrieve
     * @return the ResponseEntity with the crop or 404 (Not Found) if the crop does not exist
     */
    @GetMapping(value = "/{cropId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CropResponse getSelectCrop(@PathVariable("cropId") String cropId) {
        log.info("Fetching crop with id");
        return cropService.getSelectCrop(cropId);
    }

    /**
     * GET /api/v1/crop/all : Get all crops.
     *
     * @return the list of crops
     */
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CropDTO> FindAllCrop() {
        log.info("Fetching all crops");
        return cropService.getAllCrop();
    }
}

