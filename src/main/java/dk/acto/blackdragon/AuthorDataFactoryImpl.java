package dk.acto.blackdragon;

import dk.acto.blackdragon.model.AuthorData;
import dk.acto.blackdragon.service.AuthorDataFactory;
import io.vavr.control.Option;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.http.HttpClient;
import java.util.Optional;

public class AuthorDataFactoryImpl implements AuthorDataFactory {

    private static final String linkedInURL = "https://github.com/simonsejse/black-dragon-java";
    private static final String repositoryURL = "https://github.com/simonsejse/black-dragon-java";

    @Override
    public AuthorData create() {
        URL linkedIn = Option.of(linkedInURL)
                .toTry()
                .mapTry(URL::new)
                .getOrNull();

        URL repository = Option.of(repositoryURL)
                .toTry()
                .mapTry(URL::new)
                .getOrNull();

        return AuthorData.builder()
                .name("Simon")
                .solutionRepository(linkedIn)
                .linkedInProfile(repository)
                .build();
    }
}
