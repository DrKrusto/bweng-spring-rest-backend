package at.technikum.springrestbackend.dto;

import at.technikum.springrestbackend.dto.lawyer.GetLawyerProfilesRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
public class GetLawyerProfilesRequestTest {

    @Test
    void constructorAndFields_ShouldWorkAsExpected() {
        // Arrange
        String searchTerm = "sampleSearch";
        int page = 1;
        int size = 10;

        // Act
        GetLawyerProfilesRequest request = new GetLawyerProfilesRequest(searchTerm, page, size);

        // Assert
        assertEquals(searchTerm, request.getSearchTerm());
        assertEquals(page, request.getPage());
        assertEquals(size, request.getSize());
    }

    @Test
    void gettersAndSetters_ShouldWorkAsExpected() {
        // Arrange
        GetLawyerProfilesRequest request = new GetLawyerProfilesRequest();
        String searchTerm = "sampleSearch";
        int page = 1;
        int size = 10;

        // Act
        request.setSearchTerm(searchTerm);
        request.setPage(page);
        request.setSize(size);

        // Assert
        assertEquals(searchTerm, request.getSearchTerm());
        assertEquals(page, request.getPage());
        assertEquals(size, request.getSize());
    }

    @Test
    void noArgsConstructor_ShouldInitializeFields() {
        // Arrange & Act
        GetLawyerProfilesRequest request = new GetLawyerProfilesRequest();

        // Assert
        assertNull(request.getSearchTerm());
        assertEquals(0, request.getPage());
        assertEquals(0, request.getSize());
    }
}
