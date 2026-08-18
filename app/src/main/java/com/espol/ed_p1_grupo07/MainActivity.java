package com.espol.ed_p1_grupo07;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private Button buttonPlay;
    private Button buttonClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        this.buttonPlay = findViewById(R.id.buttonPlay);
        this.buttonPlay.setOnClickListener(v -> {
            play();
        });

        this.buttonClose = findViewById(R.id.buttonCloseApp);
        this.buttonClose.setOnClickListener(v -> {
            closeApp();
        });
    }

    // Enviará al usuario a la actividad para escoger su símbolo.
    private void play() {
        Intent intent = new Intent(MainActivity.this, SymbolSelectionActivity.class);
        startActivity(intent);
    }

    // Cierra todas las actividades de la App.
    private void closeApp() {
        finishAffinity();
    }

}