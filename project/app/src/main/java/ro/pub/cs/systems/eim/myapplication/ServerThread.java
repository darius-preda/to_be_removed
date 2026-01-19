package ro.pub.cs.systems.eim.myapplication;

import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread extends Thread{
    private int port;
    private ServerSocket serverSocket;
    private boolean isRunning = true;

    private WeatherForecastCache weatherCache;

    public ServerThread(int port) {
        this.port = port;
        this.weatherCache = new WeatherForecastCache();
        this.isRunning = false;
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            isRunning = true;

            while (isRunning) {
                Socket clientSocket = serverSocket.accept();
                // communicate with client

                CommunicationThread communicationThread = new CommunicationThread(this, clientSocket);
                communicationThread.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public WeatherForecastInfo getWeatherForecast(String city) {

        // 1. Verifică în cache
        if (weatherCache.contains(city)) {
            return weatherCache.get(city);
        }

        // 2. Dacă nu e în cache, preia de pe Internet
        WeatherForecastInfo info = WeatherApiService.getWeatherForecast(city);

        // 3. Salvează în cache
        if (info != null) {
            weatherCache.put(city, info);
        }

        return info;
    }

    public void stopServer() {
        isRunning = false;
        try {
            if(!serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // idk why we need this
    public WeatherForecastCache getWeatherCache() {
        return weatherCache;
    }
}
