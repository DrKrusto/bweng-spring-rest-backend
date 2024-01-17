package at.technikum.springrestbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class PagedResults<T> {
    private List<T> results;
    private int page;
    private int totalResults;
    private int totalPages;
}
