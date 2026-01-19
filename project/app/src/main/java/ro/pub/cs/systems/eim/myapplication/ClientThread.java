package ro.pub.cs.systems.eim.myapplication;

import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread {
    private String serverAddress;
    private int serverPort;
    private String city;
    private String informationType;
    private TextView resultTextView;

    public ClientThread(String serverAddress, int serverPort, String city, String informationType, TextView resultTextView) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        this.city = city;
        this.informationType = informationType;
        this.resultTextView = resultTextView;
    }

    @Override
    public void run() {
        // Implement the client logic here to connect to the server,
        // send the request, and update the resultTextView with the response.
        Socket socket = null;
        BufferedReader reader = null;
        PrintWriter writer = null;

        try {
            // 1. Conectare la server
            socket = new Socket(serverAddress, serverPort);

            // 2. Creează stream-uri pentru comunicare
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);

            // 3. Trimite cererea la server
            // Format: "city,informationType"
            String request = city + "," + informationType;
            writer.println(request);

            // 4. Primește răspunsul de la server
            String response = reader.readLine();

            // 5. Afișează răspunsul în UI (pe main thread)
            if (resultTextView != null) {
                resultTextView.post(new Runnable() {
                    @Override
                    public void run() {
                        resultTextView.setText(response);
                    }
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
            if (resultTextView != null) {
                resultTextView.post(new Runnable() {
                    @Override
                    public void run() {
                        resultTextView.setText("Error: " + e.getMessage());
                    }
                });
            }

        } finally {
            // Închide conexiunile
            try {
                if (reader != null) reader.close();
                if (writer != null) writer.close();
                if (socket != null) socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
