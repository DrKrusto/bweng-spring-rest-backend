package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.model.Blog;
import at.technikum.springrestbackend.service.BlogService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class BlogControllerIT {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BlogService blogService;

    @Autowired
    private ObjectMapper mapper;

    @Test
    @WithMockUser(roles = "ADMIN")
    void createBlog_shouldReturnCreated() throws Exception {
        // Arrange
        Blog newBlog = new Blog("Test Title", "Test Content", "Test Author");
        when(blogService.createBlog(any(Blog.class))).thenReturn(new ResponseEntity<>(newBlog, HttpStatus.CREATED));

        // Act and Assert
        mvc.perform(post("/api/blog/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(newBlog)))
                .andExpect(status().isCreated());
    }

    @Test
    void getLatestBlogs_shouldReturnListOfBlogs() throws Exception {
        // Arrange
        List<Blog> blogs = new ArrayList<>();
        blogs.add(new Blog("Test Title 1", "Test Content 1", "Test Author 1"));
        blogs.add(new Blog("Test Title 2", "Test Content 2", "Test Author 2"));
        when(blogService.getLatestBlogs()).thenReturn(new ResponseEntity<>(blogs, HttpStatus.OK));

        // Act and Assert
        mvc.perform(get("/api/blog/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void getBlog_shouldReturnBlogById() throws Exception {
        // Arrange
        UUID blogId = UUID.randomUUID();
        Blog blog = new Blog("Test Title", "Test Content", "Test Author");
        blog.setId(blogId);
        when(blogService.getBlog(blogId)).thenReturn(new ResponseEntity<>(blog, HttpStatus.OK));

        // Act and Assert
        mvc.perform(get("/api/blog/{id}", blogId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(blogId.toString()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateBlog_shouldReturnUpdatedBlog() throws Exception {
        // Arrange
        UUID blogId = UUID.randomUUID();
        Blog updatedBlog = new Blog("Test Title", "Test Content", "Test Author");
        updatedBlog.setId(blogId);
        when(blogService.updateBlog(eq(blogId), any(Blog.class))).thenReturn(new ResponseEntity<>(updatedBlog, HttpStatus.OK));

        // Act and Assert
        mvc.perform(put("/api/blog/{id}", blogId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updatedBlog)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(blogId.toString()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteBlog_shouldReturnNoContent() throws Exception {
        // Arrange
        UUID blogId = UUID.randomUUID();
        when(blogService.deleteBlog(blogId)).thenReturn(new ResponseEntity<>("Blog deleted successfully", HttpStatus.NO_CONTENT));

        // Act and Assert
        mvc.perform(delete("/api/blog/{id}", blogId))
                .andExpect(status().isNoContent());
    }

}
