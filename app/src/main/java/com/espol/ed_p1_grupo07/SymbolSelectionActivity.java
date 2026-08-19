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

public class SymbolSelectionActivity extends AppCompatActivity {

    private ImageButton buttonX;
    private ImageButton buttonO;
    private Button buttonStartGame;

    private Button buttonRegresar;

    // Símbolo seleccionado por el jugador.
    private int playerSymbol = 0;

    // Almacena el modo de juego elegido en MainActivity
    private int modoJuego = 0;

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

        // Recibir el modo de juego del Intent
        modoJuego = getIntent().getIntExtra("modoJuego", 0);

        this.buttonX = findViewById(R.id.imageWinner); // Manteniendo tu ID original
        this.buttonX.setOnClickListener(v -> selectSymbolX());

        this.buttonO = findViewById(R.id.buttonO); // Manteniendo tu ID original
        this.buttonO.setOnClickListener(v -> selectSymbolO());

        this.buttonStartGame = findViewById(R.id.buttonRestartGame); // Manteniendo tu ID original
        this.buttonStartGame.setOnClickListener(v -> startGame());

        this.buttonRegresar = findViewById(R.id.buttonRegresar);
        this.buttonRegresar.setOnClickListener(v -> regresar());
    }

    // El jugador selecciona el símbolo X.
    private void selectSymbolX() {
        this.buttonX.setAlpha(1f);
        this.buttonO.setAlpha(0.5f);
        this.playerSymbol = Board.X;
    }

    // El jugador selecciona el símbolo O.
    private void selectSymbolO() {
        this.buttonO.setAlpha(1f);
        this.buttonX.setAlpha(0.5f);
        this.playerSymbol = Board.CIRCLE;
    }

    // Inicia el juego
    private void startGame() {
        // El jugador no seleccionó ningún símbolo para jugar.
        if (modoJuego != 2 && playerSymbol == 0) {
            return;
        }

        // Si es PC vs PC (modo 2) y no eligió nada, asignamos X por defecto para evitar errores.
        if (modoJuego == 2 && playerSymbol == 0) {
            playerSymbol = Board.X;
        }

        // Envía el símbolo y el modo al tablero
        Intent intent = new Intent(SymbolSelectionActivity.this, BoardActivity.class);
        intent.putExtra("playerSymbol", playerSymbol);
        intent.putExtra("modoJuego", modoJuego);
        startActivity(intent);;
    }

    private void regresar() {
        Intent intent = new Intent(SymbolSelectionActivity.this, MainActivity.class);
        startActivity(intent);

        finish();
    }
}