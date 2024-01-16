package at.technikum.springrestbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class GetLawyerProfilesRequest {
    private String searchTerm;
    private int page;
    private int size;
}
