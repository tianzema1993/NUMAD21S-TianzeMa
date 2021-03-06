package edu.neu.madcourse.numad21s_tianzema;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class WebServiceActivity extends AppCompatActivity {
    private String url = "https://api.zippopotam.us/us/";
    // the handler enables update view inside the thread
    private final Handler textHandler = new Handler();
    private EditText myEditText;
    private TextView resultAddressText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_service);
        myEditText = findViewById(R.id.editZip);
        resultAddressText = findViewById(R.id.address);
    }

    public void webserviceButtonClick(View view) {
        WebServiceThread thread = new WebServiceThread();
        new Thread(thread).start();
    }

    private class WebServiceThread implements Runnable {
        @SuppressLint("SetTextI18n")
        @Override
        public void run() {
            try {
                String zip = myEditText.getText().toString();
                URL newUrl = new URL(url + zip);
                HttpURLConnection conn = (HttpURLConnection) newUrl.openConnection();
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();

                // simulate processing time
                textHandler.post(() -> {
                    resultAddressText.setText("Waiting for response...");
                });
                Thread.sleep(1000);

                // Read response.
                InputStream inputStream = conn.getInputStream();
                final String resp = convertStreamToString(inputStream);
                JSONObject jObject = new JSONObject(resp);
                String country = jObject.getString("country abbreviation");
                String zipCode = jObject.getString("post code");
                JSONArray arr = jObject.getJSONArray("places");
                JSONObject object = arr.getJSONObject(0);
                String city = object.getString("place name");
                String state = object.getString("state");
                String longitude = object.getString("longitude");
                String latitude = object.getString("latitude");
                String resultAddress = "";
                resultAddress += "Result: " + "\n";
                resultAddress += "ZipCode: " + zipCode + "\n";
                resultAddress += "City: " + city + "\n";
                resultAddress += "State: " + state + "\n";
                resultAddress += "Country: " + country + "\n";
                resultAddress += "Longitude: " + longitude + "\n";
                resultAddress += "Latitude: " + latitude + "\n";
                String finalResultAddress = resultAddress;
                textHandler.post(() -> {
                    resultAddressText.setText(finalResultAddress);
                });
            } catch (IOException | JSONException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private String convertStreamToString(InputStream is) {
        Scanner s = new Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next().replace(",", ",\n") : "";
    }
}