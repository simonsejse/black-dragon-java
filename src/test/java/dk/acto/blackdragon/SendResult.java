package dk.acto.blackdragon;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dk.acto.blackdragon.model.AuthorData;
import dk.acto.blackdragon.model.Stats;
import io.vavr.control.Option;
import okhttp3.*;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import java.net.URL;
import java.util.Map;

public class SendResult {
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final String authHeader = "qaUPLmkXcmCnNhfUwKNGBEbtsqCdywAg";
    private static final String HEADER_AUTHORIZATION = "Authorization";
    private static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    @Test(dependsOnGroups = "transform")
    public void testResult(ITestContext context) throws Exception {
        Stats stats = (Stats) context.getAttribute("result");
        AuthorData authorData = (AuthorData) context.getAttribute( "author");

        String result = Option.of(new URL("https://dispatcher.acto.dk/hic_sunt_dracones"))
                .map(x -> new Request.Builder()
                        .url(x)
                        .addHeader(HEADER_AUTHORIZATION, authHeader)
                        .post(RequestBody.create(
                                JSON,
                                gson.toJson(
                                        Map.of("author", authorData,
                                                "stats", stats))))
                        .build())
                .map(x -> new OkHttpClient.Builder()
                        .build()
                        .newCall(x))
                .toTry()
                .mapTry(Call::execute)
                .mapTry(x -> x.body().string())
                .getOrNull();
        Assert.assertNotNull(result);


    }
}
