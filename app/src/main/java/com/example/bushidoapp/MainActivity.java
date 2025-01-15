package com.example.bushidoapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private Button pantallaSeleccionBtn;
    private ImageButton cambiarModoBtn;
    private SharedPreferences sharedPreferences;
    private boolean isNightModeOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences("AppSettingsPrefs", MODE_PRIVATE);
        isNightModeOn = sharedPreferences.getBoolean("NightMode", false);

        if (isNightModeOn) {
            setTheme(R.style.AppTheme_Dark);


        } else {
            setTheme(R.style.AppTheme_Light);

        }

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        pantallaSeleccionBtn = findViewById(R.id.pantallaSeleccionBtn);
        cambiarModoBtn = findViewById(R.id.cambiarModoBtn);


        pantallaSeleccionBtn.setOnClickListener(v -> pantallaSeleccion());

        cambiarModoBtn.setOnClickListener(v -> cambiarTema());
    }


    private void pantallaSeleccion() {
        Intent intentAdd = new Intent(this, SeleccionActivity.class);
        startActivity(intentAdd);
    }


    private void cambiarTema() {

        isNightModeOn = !isNightModeOn;

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("NightMode", isNightModeOn);
        editor.apply();

        recreate();
    }
}