package at.technikum.springrestbackend.dto.lawyer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GetLawyerProfilesRequest {
    private String searchTerm;
    private int page;
    private int size;
}
