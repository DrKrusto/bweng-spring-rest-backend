package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.service.FileUploaderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

import static org.junit.matchers.JUnitMatchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class FileUploadControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FileUploaderService fileUploaderService;


    @Test
    @WithMockUser(roles = "ADMIN")
    void uploadFile_ValidImage_ReturnsOk() throws Exception {
        // Arrange
        MockMultipartFile file = new MockMultipartFile("file", "test-image.jpg", "image/jpeg", "content".getBytes());
        UUID id = UUID.randomUUID();
        String bucketName = "testBucket";
        String objectName = "test-objectName";

        doNothing().when(fileUploaderService).uploadFile(anyString(), anyString(), anyString());

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/files/upload")
                        .file(file)
                        .param("bucketName", bucketName)
                        .param("objectName", objectName)
                        .param("id", id.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Image uploaded successfully as " + id + "_" + objectName));

    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void uploadFile_InvalidFileType_ReturnsBadRequest() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "Test Data".getBytes());
        UUID id = UUID.randomUUID();

        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/files/upload")
                        .file(file)
                        .param("bucketName", "testBucket")
                        .param("objectName", "testObject")
                        .param("id", id.toString()))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("File type not supported. Please upload a JPEG or PNG image.")));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void downloadImage_ValidInput_ReturnsImage() throws Exception {
        UUID id = UUID.randomUUID();

        when(fileUploaderService.downloadImage(anyString(), anyString())).thenReturn("MockImageData".getBytes());

        mockMvc.perform(get("/api/files/download/image/testBucket/testObject/" + id)
                        .contentType(MediaType.IMAGE_JPEG))
                .andExpect(status().isOk())
                .andExpect(content().bytes("MockImageData".getBytes()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void deleteFile_ValidInput_ReturnsOkResponse() throws Exception {
        mockMvc.perform(delete("/api/files/delete/testBucket/testObject")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("File deleted successfully")));
    }

}
