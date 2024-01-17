package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.service.FileUploaderService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.InputStream;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class FileUploadControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FileUploaderService fileUploaderService;

    @Test
    void uploadFile_shouldReturnOk() throws Exception {
        // Arrange
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", MediaType.TEXT_PLAIN_VALUE, "Hello, World!".getBytes());
        String bucketName = "testBucket";
        String objectName = "testObject";

        // Mock behavior
        doNothing().when(fileUploaderService).uploadFile(bucketName, objectName, "tempFilePath");

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/files/upload")
                        .file(file)
                        .param("bucketName", bucketName)
                        .param("objectName", objectName))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("File uploaded successfully as " + objectName));
    }

    @Test
    void downloadFile_shouldReturnOk() throws Exception {
        // Arrange
        String bucketName = "testBucket";
        String objectName = "testObject";

        // Mock behavior
        when(fileUploaderService.downloadImage(bucketName, objectName)).thenReturn(new InputStreamResource(Mockito.mock(InputStream.class)).getContentAsByteArray());

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/files/download")
                        .param("bucketName", bucketName)
                        .param("objectName", objectName))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
