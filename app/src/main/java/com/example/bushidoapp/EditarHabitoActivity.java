package com.example.bushidoapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.bushidoapp.room.DatabaseClient;

import java.util.List;


public class EditarHabitoActivity extends AppCompatActivity {

    private int position;
    private Habito habito;
    private Button editarHabitoBtn;
    private Button volverVerHabitosBtn2;
    private List<Habito> HabitoList;
    EditText textoNombre;
    EditText textoTipo;
    EditText textoDuracion;
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
        setContentView(R.layout.activity_editar_habito);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        position = intent.getIntExtra("index", -1);

        editarHabitoBtn = findViewById(R.id.editarHabitoBtn);
        volverVerHabitosBtn2 = findViewById(R.id.volverVerHabitosBtn2);

        textoNombre = findViewById(R.id.editTextNombre2);
        textoTipo = findViewById(R.id.editTextTipo2);
        textoDuracion = findViewById(R.id.editTextDuracion2);

        Intent ianterior = getIntent();
        int position = ianterior.getIntExtra("index",-1);

        HabitoList = DatabaseClient.getInstance(getApplicationContext()).getHabitoDatabase().HabitoDao().getAllHabitos();
        Habito habito = HabitoList.get(position);

        textoNombre.setText(habito.getNombre());
        textoTipo.setText(habito.getTipo());
        textoDuracion.setText(habito.getDuracion());

        editarHabitoBtn.setOnClickListener(v -> actualizarHabito());
        volverVerHabitosBtn2.setOnClickListener(v -> toPantallaVerHabito());


    }

    private void actualizarHabito() {

        HabitoList = DatabaseClient.getInstance(getApplicationContext()).getHabitoDatabase().HabitoDao().getAllHabitos();
        Habito habito = HabitoList.get(position);

        habito.setNombre(textoNombre.getText().toString());
        habito.setTipo(textoTipo.getText().toString());
        habito.setDuracion(textoDuracion.getText().toString());

        DatabaseClient.getInstance(getApplicationContext()).getHabitoDatabase().HabitoDao().update(habito);

        toPantallaVerHabito();
    }

    private void toPantallaVerHabito() {
        Intent intent = new Intent(this, VerhabitosActivity.class);
        startActivity(intent);

    }

}