package ro.pub.cs.systems.eim.myapplication;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherApiService {
    public static WeatherForecastInfo getWeatherForecast(String city) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            String urlString = Constants.WEATHER_API_URL +
                    "?q=" + city +
                    "&appid=" + Constants.API_KEY;

            URL url = new URL(urlString);

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                return null;
            }

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            return WeatherForecastInfo.fromJson(response.toString());

        } catch (Exception e) {
            e.printStackTrace();
            return null;

        } finally {
            try {
                if (reader != null) reader.close();
                if (connection != null) connection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
