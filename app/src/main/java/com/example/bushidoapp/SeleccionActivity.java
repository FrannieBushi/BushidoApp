package com.example.bushidoapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;



public class SeleccionActivity extends AppCompatActivity {

    private Button pantallaVerHabitoBtn;
    private Button volverPantallaInicioBtn;
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
        setContentView(R.layout.activity_seleccion);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        pantallaVerHabitoBtn = findViewById(R.id.pantallaVerHabitoBtn);
        volverPantallaInicioBtn = findViewById(R.id. volverPantallaInicioBtn);

        pantallaVerHabitoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               toPantallaVerHabito();
            }
        });

        volverPantallaInicioBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                toPantallaInicio();
            }
        });

    }

    private void toPantallaVerHabito() {
        Intent intentAdd = new Intent(this, VerhabitosActivity.class);
        startActivity(intentAdd);
    }

    private void toPantallaInicio() {
        Intent intentAdd = new Intent(this, MainActivity.class);
        startActivity(intentAdd);
    }

}