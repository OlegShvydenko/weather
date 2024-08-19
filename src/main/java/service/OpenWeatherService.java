package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.dto.WeatherDTO;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class OpenWeatherService {
    public static void main(String[] args) throws IOException, ParseException {
        OpenWeatherService openWeatherService = new OpenWeatherService();

        WeatherDTO weatherDTO = openWeatherService.getWeatherByCityName("Москва");
        //WeatherDTO weatherDTO2 = openWeatherService.getWeatherByCoordinates(37.6156, 55.7522);
    }

    ObjectMapper objectMapper;
    private static final String OPEN_WEATHER_API_KEY = "b9acfad0073eee639d170adde3dd3162";

    public OpenWeatherService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public OpenWeatherService() {
        this.objectMapper = new ObjectMapper();
    }

    public WeatherDTO getWeatherByCityName(String city) throws IOException, ParseException {
        CloseableHttpClient httpclient = HttpClients.createDefault();

        HttpUriRequest httpGet = new HttpGet(
                "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + OPEN_WEATHER_API_KEY);

        CloseableHttpResponse response = httpclient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        String weatherJson = EntityUtils.toString(entity);
        return objectMapper.readValue(weatherJson, WeatherDTO.class);
    }

    public WeatherDTO getWeatherByCoordinates(double latitude, double longitude) throws IOException, ParseException {
        CloseableHttpClient httpclient = HttpClients.createDefault();

        HttpUriRequest httpGet = new HttpGet(
                "https://api.openweathermap.org/data/2.5/weather?" +
                        "lat=" + latitude + "&lon=" + longitude + "&appid=" + OPEN_WEATHER_API_KEY);

        CloseableHttpResponse response = httpclient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        String weatherJson = EntityUtils.toString(entity);
        return objectMapper.readValue(weatherJson, WeatherDTO.class);
    }
}
