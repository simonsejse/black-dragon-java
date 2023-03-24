package dk.acto.blackdragon;

import dk.acto.blackdragon.service.DataFetcher;
import io.vavr.control.Option;
import okhttp3.*;

import java.net.URL;

public class DataFetcherImpl implements DataFetcher {

    @Override
    public String fetchData(URL url) {
        return Option.of(url)
                .map(theUrl -> new Request.Builder()
                        .url(theUrl)
                        .build())
                .map(request -> new OkHttpClient()
                        .newCall(request))
                .toTry()
                .mapTry(Call::execute)
                .mapTry(Response::body)
                .mapTry(ResponseBody::string)
                .getOrElse(String::new);
    }
}
