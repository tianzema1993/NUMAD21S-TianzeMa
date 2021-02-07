package edu.neu.madcourse.numad21s_tianzema;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ClickyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicky);
    }

    @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
    public void onButtonClick(View view) {
        TextView textView = findViewById(R.id.displayResult);
        String s;
        switch (view.getId()) {
            case R.id.A:
                s = "A";
                break;
            case R.id.B:
                s = "B";
                break;
            case R.id.C:
                s = "C";
                break;
            case R.id.D:
                s = "D";
                break;
            case R.id.E:
                s = "E";
                break;
            case R.id.F:
                s = "F";
                break;
            default:
                s = "";
        }
        textView.setText("Pressed: " + s);
    }
}