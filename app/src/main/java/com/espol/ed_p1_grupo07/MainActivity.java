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

    private Button buttonModo0;
    private Button buttonModo1;
    private Button buttonModo2;
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

        // Mapeo de los tres botones de modos de juego
        this.buttonModo0 = findViewById(R.id.buttonModo0);
        this.buttonModo0.setOnClickListener(v -> play(0)); // 0 = Humano vs PC

        this.buttonModo1 = findViewById(R.id.buttonModo1);
        this.buttonModo1.setOnClickListener(v -> play(1)); // 1 = Humano vs Humano

        this.buttonModo2 = findViewById(R.id.buttonModo2);
        this.buttonModo2.setOnClickListener(v -> play(2)); // 2 = PC vs PC

        // Mapeo del botón de cerrar
        this.buttonClose = findViewById(R.id.buttonCloseApp);
        this.buttonClose.setOnClickListener(v -> closeApp());

    }

    // Enviará al usuario a la actividad para escoger su símbolo.
    private void play(int modoSeleccionado) {

        Intent intent = new Intent(MainActivity.this, SymbolSelectionActivity.class);
        intent.putExtra("modoJuego", modoSeleccionado);
        startActivity(intent);

    }

    // Cierra todas las actividades de la App.
    private void closeApp() {

        finishAffinity();

    }

}