package com.titan.path.countries;

import com.titan.path.services.INavigationService;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.logging.Logger;

@Component
@Lazy(false)
public class CountrySourceConnection implements ICountySourceConnection {

    OkHttpClient client = new OkHttpClient();

    Request request = new Request.Builder()
            .url("https://raw.githubusercontent.com/mledoze/countries/master/countries.json")
            .build();

    @Override
    public void rawCountriesGetAsync(INavigationService navigationService) {
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            if (response.body() != null) {
                navigationService.setRawCountry(response.body().string());
            }
        } catch (IOException e) {
            Logger logger = Logger.getLogger(this.getClass().getName());
            logger.warning(e.getLocalizedMessage());
        }
    }

    @Override
    public void rawCountriesGetSync(INavigationService navigationService) throws IOException {
        Call call = client.newCall(request);
        Response response = call.execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
        if (response.body() != null) {
            navigationService.setRawCountry(response.body().string());
        }
    }

}
