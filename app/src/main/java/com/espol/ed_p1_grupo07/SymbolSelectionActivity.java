package com.espol.ed_p1_grupo07;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.espol.ed_p1_grupo07.model.Board;
import com.espol.ed_p1_grupo07.model.Computer;
import com.espol.ed_p1_grupo07.model.Player;

public class SymbolSelectionActivity extends AppCompatActivity {

    private ImageButton buttonX;
    private ImageButton buttonO;
    private Button buttonStartGame;

    // Símbolo seleccionado por el jugador.
    private int playerSymbol = 0; // Círculo: -1 | X: 1

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_symbol_selection);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        this.buttonX = findViewById(R.id.buttonX);
        this.buttonX.setOnClickListener(v -> {
            selectSymbolX();
        });

        this.buttonO = findViewById(R.id.buttonO);
        this.buttonO.setOnClickListener(v -> {
            selectSymbolO();
        });

        this.buttonStartGame = findViewById(R.id.buttonStartGame);
        this.buttonStartGame.setOnClickListener(v -> {
            startGame();
        });
    }

    // El jugador selecciona el símbolo X.
    private void selectSymbolX() {
        this.buttonX.setAlpha(1f);
        this.buttonO.setAlpha(0.5f);
        this.playerSymbol = 1;
    }

    // El jugador selecciona el símbolo O.
    private void selectSymbolO() {
        this.buttonO.setAlpha(1f);
        this.buttonX.setAlpha(0.5f);
        this.playerSymbol = -1;
    }

    // Inicia el juego
    private void startGame() {
        // El jugador No seleccionó ningún símbolo para jugar.
        if (playerSymbol == 0) {
            return;
        }

        // Envía el símbolo seleccionado por el jugador e inicia la actividad del tablero (BoardActivity).
        Intent intent = new Intent(SymbolSelectionActivity.this, BoardActivity.class);
        intent.putExtra("playerSymbol", playerSymbol);
        startActivity(intent);
    }
}