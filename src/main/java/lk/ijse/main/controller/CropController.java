package lk.ijse.main.controller;

import lk.ijse.main.customObj.CropResponse;
import lk.ijse.main.dto.CropDTO;
import lk.ijse.main.exception.CropNotFoundException;
import lk.ijse.main.exception.DataPersistFailedException;
import lk.ijse.main.service.CropService;
import lk.ijse.main.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/crop")
@RequiredArgsConstructor
public class CropController {
    @Autowired
    private final CropService cropService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> createCrop(
            @RequestPart("commonName") String commonName,
            @RequestPart("scientificName") String scientificName,
            @RequestPart("category") String category,
            @RequestPart("season") String season,
            @RequestPart("fieldId") String fieldId,
            @RequestPart("cropImage") MultipartFile cropImage) {
        try {
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
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DataPersistFailedException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

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
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (CropNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{cropId}")
    public ResponseEntity<Void> deleteCrop(@PathVariable("cropId") String cropId) {
        try {
            cropService.deleteCrop(cropId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (CropNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{cropId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CropResponse getSelectCrop(@PathVariable("cropId") String cropId) {
        return cropService.getSelectCrop(cropId);
    }
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CropDTO> FindAllCrop() {
        return cropService.getAllCrop();
    }
}

