package ro.pub.cs.systems.eim.myapplication;

import java.util.HashMap;
import java.util.Map;

public class WeatherForecastCache {
    Map<String, WeatherForecastInfo> cache;

    WeatherForecastCache() {
        this.cache = new HashMap<String, WeatherForecastInfo>();
    }

    // we use the name of the city as a key
    public void put(String city, WeatherForecastInfo weatherForecastInfo) {
        cache.put(city, weatherForecastInfo);
    }

    public WeatherForecastInfo get(String city) {
        return cache.get(city);
    }

    public boolean contains(String city) {
        return cache.containsKey(city);
    }
}
