package com.example.bushidoapp;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.bushidoapp.room.DatabaseClient;

import java.util.Calendar;
import java.util.TimeZone;

public class AnadirHabitoActivity extends AppCompatActivity {

    private EditText editTextNombre;
    private EditText editTextTipo;
    private EditText editTextDuracion;
    private Button ingresarHabitoBtn;
    private Button volverVerHabitosBtn;
    private SharedPreferences sharedPreferences;
    private boolean isNightModeOn;


    private static final String TAG = "Notify";
    private static final int REQUEST_CODE = 1;

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
        setContentView(R.layout.activity_anadir_habito);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ingresarHabitoBtn = findViewById(R.id.editarHabitoBtn);
        volverVerHabitosBtn = findViewById(R.id.volverVerHabitosBtn2);
        editTextNombre = findViewById(R.id.editTextNombre2);
        editTextTipo = findViewById(R.id.editTextTipo);
        editTextDuracion = findViewById(R.id.editTextDuracion2);

        ingresarHabitoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {addHabito();}
        });

        volverVerHabitosBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toVerHabitos();
            }
        });
    }

    private void addHabito() {

        String habitoNombre = editTextNombre.getText().toString();
        String habitoTipo = editTextTipo.getText().toString();
        String habitoDuracion = editTextDuracion.getText().toString();

        Habito nuevoHabito = new Habito(habitoNombre, habitoTipo, habitoDuracion);

        DatabaseClient.getInstance(getApplicationContext()).getHabitoDatabase().HabitoDao().insert(nuevoHabito);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // Android 13 y superior
            if (ContextCompat.checkSelfPermission(this, "android.permission.POST_NOTIFICATIONS") != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{"android.permission.POST_NOTIFICATIONS"}, REQUEST_CODE);
            } else {

                createNotificationChannel();
                scheduleDailyNotification();

            }
        } else {
            
            createNotificationChannel();
            scheduleDailyNotification();

        }

        toVerHabitos();
    }

    private void toVerHabitos() {

        Intent intentAdd = new Intent(this, VerhabitosActivity.class);
        startActivity(intentAdd);

    }

    private void scheduleDailyNotification(){

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getDefault());
        calendar.setTimeInMillis(System.currentTimeMillis());

        calendar.set(Calendar.SECOND, 60);

        if(calendar.getTimeInMillis() < System.currentTimeMillis()){
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        Intent intent = new Intent(this, NotificationReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
//                AlarmManager.INTERVAL_DAY, pendingIntent);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

        Log.d(TAG, "Notificacion diaria programada a las " + calendar.getTime());

    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // Solo para Android 8.0 y superior
            CharSequence name = "DailyReminderChannel";
            String description = "Channel for Daily Reminders";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("daily_reminder_channel",
                    name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            Log.d(TAG, "Canal de notificaciones creado");
        }
    }

}