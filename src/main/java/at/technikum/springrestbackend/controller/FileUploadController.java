package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.service.FileUploaderService;
import io.minio.errors.MinioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.UUID;

@RestController
public class FileUploadController {

    private final FileUploaderService fileUploaderService;

    @Autowired
    public FileUploadController(FileUploaderService fileUploaderService) {
        this.fileUploaderService = fileUploaderService;
    }
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasPermission(#id, 'at.technikum.springrestbackend.model.User', 'post')")
    @PostMapping("/api/files/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file,
                                             @RequestParam("bucketName") String bucketName,
                                             @RequestParam("objectName") String objectName,
                                             @RequestParam("id") UUID id) {
        try {
            // Check if the file is an image (JPEG or PNG)
            String contentType = file.getContentType();
            if (contentType == null ||
                    (!contentType.equals("image/jpeg") &&
                            !contentType.equals("image/jpg") &&
                            !contentType.equals("image/png"))) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File type not supported. Please upload a JPEG or PNG image.");
            }

            // Proceed with file upload
            String tempFileName = System.getProperty("java.io.tmpdir") + file.getOriginalFilename();
            file.transferTo(new java.io.File(tempFileName));
            objectName = id + "_" + objectName;
            fileUploaderService.uploadFile(bucketName, objectName, tempFileName);
            return ResponseEntity.ok("Image uploaded successfully as " + objectName);
        } catch (IOException | MinioException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading file: " + e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasPermission(#id, 'at.technikum.springrestbackend.model.User', 'get')")
    @GetMapping("/api/files/download/image/{bucketName}/{fileName}/{id}")
    public ResponseEntity<Resource> downloadImage(@PathVariable String bucketName, @PathVariable String fileName, @PathVariable UUID id) {
        try {
            byte[] data = fileUploaderService.downloadImage(bucketName, fileName);
            InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(data));

            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(resource);
        } catch (IOException | MinioException e) {
            return ResponseEntity.status(500).body(new InputStreamResource(new ByteArrayInputStream(("Error downloading image: " + e.getMessage()).getBytes())));
        }
    }
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasPermission(#id, 'at.technikum.springrestbackend.model.User', 'delete')")
    @DeleteMapping("/api/files/delete/{bucketName}/{objectName}/{id}")
    public ResponseEntity<String> deleteFile(@PathVariable String bucketName, @PathVariable String objectName, @PathVariable UUID id) {
        try {
            fileUploaderService.deleteFile(bucketName, objectName);
            return ResponseEntity.ok("File deleted successfully: " + objectName);
        } catch (IOException | MinioException e) {
            return ResponseEntity.status(500).body("Error deleting file: " + e.getMessage());
        }
    }

}
