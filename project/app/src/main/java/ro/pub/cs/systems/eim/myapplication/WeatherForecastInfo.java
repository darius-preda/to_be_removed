package ro.pub.cs.systems.eim.myapplication;

import org.json.JSONArray;
import org.json.JSONObject;

public class WeatherForecastInfo {
    private String city;
    private String temperature;
    private String windSpeed;
    private String humidity;
    private String condition;

    private String pressure;

    WeatherForecastInfo(String city, String temperature, String windSpeed, String humidity, String condition, String pressure) {
        this.city = city;
        this.temperature = temperature;
        this.windSpeed = windSpeed;
        this.humidity = humidity;
        this.condition = condition;
        this.pressure = pressure;
    }

    WeatherForecastInfo() {}

    public static WeatherForecastInfo fromJson(String jsonString) {
        WeatherForecastInfo info = new WeatherForecastInfo();

        try {
            JSONObject jsonObject = new JSONObject(jsonString);

            info.setCity(jsonObject.getString("name"));

            JSONObject main = jsonObject.getJSONObject("main");

            info.setTemperature(main.getString("temp") + "K");
            info.setHumidity(main.getString("humidity") + "%");
            info.setPressure(main.getString("pressure") + " hPa");

            JSONObject wind = jsonObject.getJSONObject("wind");
            info.setWindSpeed(wind.getString("speed") + " m/s");

            JSONArray weatherArray = jsonObject.getJSONArray("weather");
            JSONObject weather = weatherArray.getJSONObject(0);
            info.setCondition(weather.getString("main"));

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return info;
    }

    public String getCity() {
        return city;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getCondition() {
        return condition;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    @Override
    public String toString() {
        return "City: " + city + " | " +
                "Temperature: " + temperature + " | " +
                "Wind Speed: " + windSpeed + " | " +
                "Humidity: " + humidity + " | " +
                "Condition: " + condition;
    }
}
