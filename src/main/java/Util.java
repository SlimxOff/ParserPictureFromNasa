import java.io.IOException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.config.RequestConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;

public class Util {
  private static final CloseableHttpClient httpClient = HttpClientBuilder.create()
            .setDefaultRequestConfig(RequestConfig.custom()
                    .setConnectTimeout(5000)
                    .setSocketTimeout(30000)
                    .setRedirectsEnabled(true).build())
            .build();
    private static final ObjectMapper mapper = new ObjectMapper();

    public static String getUrl(String uri) throws IOException {
        CloseableHttpResponse response;
        response = httpClient.execute(new HttpGet(uri));
        NasaObject nasaObject = mapper.readValue(response.getEntity().getContent(), NasaObject.class);
        return nasaObject.getUrl();
    }
    }


