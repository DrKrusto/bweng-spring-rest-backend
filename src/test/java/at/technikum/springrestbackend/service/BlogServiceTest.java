package at.technikum.springrestbackend.service;

import at.technikum.springrestbackend.model.Blog;
import at.technikum.springrestbackend.repository.BlogRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BlogServiceTest {

    @Mock
    private BlogRepository blogRepository;

    @InjectMocks
    private BlogService blogService;

    @Test
    void createBlog_shouldReturnCreatedBlog() {
        // Arrange
        Blog blogToCreate = new Blog("Test Title", "Test Content", "Test Author");

        when(blogRepository.save(any(Blog.class))).thenReturn(blogToCreate);

        // Act
        ResponseEntity<Blog> response = blogService.createBlog(blogToCreate);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(blogToCreate, response.getBody());
    }

    @Test
    void getLatestBlogs_shouldReturnListOfLatestBlogs() {
        // Arrange
        List<Blog> expectedBlogs = Arrays.asList(
                new Blog("Title 1", "Content 1", "Author 1"),
                new Blog("Title 2", "Content 2", "Author 2"),
                new Blog("Title 3", "Content 3", "Author 3"),
                new Blog("Title 4", "Content 4", "Author 4")
        );

        when(blogRepository.findFirst4ByOrderByCreatedOnDesc()).thenReturn(expectedBlogs);

        // Act
        ResponseEntity<List<Blog>> response = blogService.getLatestBlogs();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(expectedBlogs, response.getBody());
    }

    @Test
    void getBlog_shouldReturnExistingBlogById() {
        // Arrange
        UUID existingId = UUID.randomUUID();
        Blog expectedBlog = new Blog("Test Title", "Test Content", "Test Author");

        // Mock behavior
        when(blogRepository.findById(existingId)).thenReturn(Optional.of(expectedBlog));

        // Act
        ResponseEntity<Blog> response = blogService.getBlog(existingId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(expectedBlog, response.getBody());
    }

    @Test
    void getBlog_shouldReturnNotFoundForNonExistingId() {
        // Arrange
        UUID nonExistingId = UUID.randomUUID();

        // Mock behavior
        when(blogRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Blog> response = blogService.getBlog(nonExistingId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void updateBlog_shouldReturnUpdatedBlog() {
        // Arrange
        UUID blogId = UUID.randomUUID();
        Blog updatedBlog = new Blog("Updated Title", "Updated Content", "Updated Author");

        // Mock behavior
        when(blogRepository.findById(blogId)).thenReturn(Optional.of(new Blog("Original Title", "Original Content", "Original Author")));
        when(blogRepository.save(any(Blog.class))).thenReturn(updatedBlog);

        // Act
        ResponseEntity<Blog> response = blogService.updateBlog(blogId, updatedBlog);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(updatedBlog, response.getBody());
    }

    @Test
    void updateBlog_shouldReturnNotFoundForNonExistingId() {
        // Arrange
        UUID nonExistingId = UUID.randomUUID();
        Blog updatedBlog = new Blog("Updated Title", "Updated Content", "Updated Author");

        // Mock behavior
        when(blogRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Blog> response = blogService.updateBlog(nonExistingId, updatedBlog);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void deleteBlog_shouldReturnSuccessForExistingId() {
        // Arrange
        UUID existingId = UUID.randomUUID();

        // Mock behavior
        when(blogRepository.existsById(existingId)).thenReturn(true);

        // Act
        ResponseEntity<String> response = blogService.deleteBlog(existingId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Blog deleted successfully", response.getBody());
    }

    @Test
    void deleteBlog_shouldReturnNotFoundForNonExistingId() {
        // Arrange
        UUID nonExistingId = UUID.randomUUID();

        // Mock behavior
        when(blogRepository.existsById(nonExistingId)).thenReturn(false);

        // Act
        ResponseEntity<String> response = blogService.deleteBlog(nonExistingId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }
}
