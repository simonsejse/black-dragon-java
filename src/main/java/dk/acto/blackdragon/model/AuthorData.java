package dk.acto.blackdragon.model;

import lombok.Builder;
import lombok.Data;

import java.net.URL;

@Data
@Builder
public class AuthorData {
    private final String name;
    private URL linkedInProfile;
    private URL solutionRepository;
}
