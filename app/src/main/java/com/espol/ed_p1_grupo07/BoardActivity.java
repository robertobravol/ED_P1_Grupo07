package com.espol.ed_p1_grupo07;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.espol.ed_p1_grupo07.model.Board;
import com.espol.ed_p1_grupo07.model.Computer;
import com.espol.ed_p1_grupo07.model.Player;

public class BoardActivity extends AppCompatActivity {

    private ImageButton buttonCloseGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_board);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Recibe el símbolo seleccionado por el jugador
        int playerSymbol = getIntent().getIntExtra("playerSymbol", 0);

        this.buttonCloseGame = findViewById(R.id.buttonCloseGame);
        this.buttonCloseGame.setOnClickListener(v -> {
            closeGame();
        });

        Player player = new Player(playerSymbol);
        Computer computer = new Computer(-playerSymbol);
        Board board = new Board();
    }

    // Finaliza el juego sin terminar la partida y lo envía al menú principal (MainActivity).
    private void closeGame() {
        Intent intent = new Intent(BoardActivity.this, MainActivity.class);
        startActivity(intent);
    }
}