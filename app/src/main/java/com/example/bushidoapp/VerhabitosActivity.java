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
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bushidoapp.room.HabitoDao;
import com.example.bushidoapp.room.DatabaseClient;

import java.util.List;

public class VerhabitosActivity extends AppCompatActivity {

    private RecyclerView habitoRecyclerView;
    private HabitoAdapter habitoAdapter;
    private Button pantallaAnadirHabitoBtn;
    private Button volverPantallaSeleccionBtn;
    private List<Habito> habitoList;
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
        setContentView(R.layout.activity_verhabitos);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        pantallaAnadirHabitoBtn = findViewById(R.id.pantallaAnadirHabitoBtn);
        volverPantallaSeleccionBtn = findViewById(R.id.volverPantallaSeleccionBtn);

        habitoRecyclerView = findViewById(R.id.habitoRecyclerView);
        habitoRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadHabitosFromDatabase();

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(habitoAdapter));
        itemTouchHelper.attachToRecyclerView(habitoRecyclerView);

        pantallaAnadirHabitoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toPantallaAnadirHabito();
            }
        });

        volverPantallaSeleccionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toPantallaSeleccion();
            }
        });

    }

    private void toPantallaAnadirHabito() {
        Intent intentAdd = new Intent(this, AnadirHabitoActivity.class);
        startActivity(intentAdd);
    }

    // Método para volver a la pantalla anterior
    private void toPantallaSeleccion() {
        Intent intentAdd = new Intent(this, SeleccionActivity.class);
        startActivity(intentAdd);
    }

    // Método para cargar los hábitos desde la base de datos
    private void loadHabitosFromDatabase() {

        habitoList = DatabaseClient.getInstance(getApplicationContext()).getHabitoDatabase().HabitoDao().getAllHabitos();
        HabitoDao habitoDao = DatabaseClient.getInstance(getApplicationContext()).getHabitoDatabase().HabitoDao();
        habitoAdapter = new HabitoAdapter(habitoList, habitoDao, getApplicationContext());
        habitoRecyclerView.setAdapter(habitoAdapter);
    }



}