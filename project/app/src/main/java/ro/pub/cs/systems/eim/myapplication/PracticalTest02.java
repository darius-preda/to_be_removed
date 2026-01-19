package ro.pub.cs.systems.eim.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PracticalTest02 extends AppCompatActivity {
    private EditText serverPortEditText = null;
    private Button connectButton = null;
    private EditText clientAddressEditText = null;
    private EditText clientPortEditText = null;

    private EditText cityEditText = null;
    private Spinner informationTypeSpinner = null;
    private Button getWeatherForecastButton = null;

    private void initializeViews() {
        serverPortEditText = findViewById(R.id.server_port_edit_text);
        connectButton = findViewById(R.id.connect_button);
        clientAddressEditText = findViewById(R.id.client_address_edit_text);
        clientPortEditText = findViewById(R.id.client_port_edit_text);
        cityEditText = findViewById(R.id.city_edit_text);
        informationTypeSpinner = findViewById(R.id.information_type_spinner);
        getWeatherForecastButton = findViewById(R.id.get_weather_forecast_button);
    }

    private void setupListeners() {
        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleConnectButton();
            }
        });

        getWeatherForecastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleGetWeatherForecast();
            }
        });
    }

    private void handleConnectButton() {
        String serverPort = serverPortEditText.getText().toString();

        if (serverPort.isEmpty()) {
            Toast.makeText(this, "Server port must be filled!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Connecting to server on port: " + serverPort, Toast.LENGTH_SHORT).show();
            // start server here
        }
    }

    private void handleGetWeatherForecast() {
        String clientAddress = clientAddressEditText.getText().toString();
        String clientPort = clientPortEditText.getText().toString();
        String city = cityEditText.getText().toString();
        String informationType = informationTypeSpinner.getSelectedItem().toString();

        if (clientAddress.isEmpty() || clientPort.isEmpty() || city.isEmpty() || informationType.isEmpty()) {
            Toast.makeText(this, "All fields must be filled!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Getting " + informationType + " for " + city + " from " + clientAddress + ":" + clientPort, Toast.LENGTH_SHORT).show();
            // start client here
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // link xml
        setContentView(R.layout.activity_practical_test02_main);

        initializeViews();
        setupListeners();
    }
}