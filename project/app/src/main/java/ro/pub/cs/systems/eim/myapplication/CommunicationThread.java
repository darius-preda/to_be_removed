package ro.pub.cs.systems.eim.myapplication;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class CommunicationThread extends Thread {
    private Socket clientSocket;
    private ServerThread serverThread;

    public CommunicationThread(ServerThread serverThread, Socket clientSocket) {
        this.serverThread = serverThread;
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        BufferedReader reader = null;
        PrintWriter writer = null;

        try {
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writer = new PrintWriter(clientSocket.getOutputStream(), true);

            String request = reader.readLine();

            if (request == null || request.isEmpty()) {
                writer.println("Error: Empty request");
                return;
            }

            String[] parts = request.split(",");
            if (parts.length != 2) {
                writer.println("Error: Invalid format. Use: city,informationType");
                return;
            }

            String city = parts[0].trim();
            String informationType = parts[1].trim().toLowerCase();

            WeatherForecastInfo weatherInfo = serverThread.getWeatherForecast(city);

            if (weatherInfo == null) {
                writer.println("Error: Could not get weather for " + city);
                return;
            }

            String response = buildResponse(weatherInfo, informationType);
            writer.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) reader.close();
                if (writer != null) writer.close();
                if (clientSocket != null) clientSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String buildResponse(WeatherForecastInfo info, String informationType) {
        switch (informationType) {
            case Constants.TEMPERATURE:
                return info.getTemperature();

            case Constants.WIND_SPEED:
                return info.getWindSpeed();

            case Constants.HUMIDITY:
                return info.getHumidity();

            case Constants.CONDITION:
                return info.getCondition();

            case Constants.ALL:
            default:
                return info.toString();
        }
    }
}
