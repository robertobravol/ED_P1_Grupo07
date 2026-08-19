package com.espol.ed_p1_grupo07;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.espol.ed_p1_grupo07.model.Board;

public class GameResultActivity extends AppCompatActivity {

    private ImageView imageWinner;
    private Button buttonRestartGame;
    private Button buttonRegresarHome;
    private int modoJuego = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game_result);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        this.imageWinner = findViewById(R.id.imageWinner);

        int winnerSymbol = getIntent().getIntExtra("winnerSymbol", 0);

        modoJuego = getIntent().getIntExtra("modoJuego", 0);

        if (winnerSymbol == Board.CIRCLE) {
            imageWinner.setImageResource(R.drawable.circle_selector);
        } else if (winnerSymbol == Board.X) {
            imageWinner.setImageResource(R.drawable.x_selector);
        }

        buttonRestartGame = findViewById(R.id.buttonRegresar);
        buttonRestartGame.setOnClickListener(v -> {
            restartGame();
        });

        buttonRegresarHome = findViewById(R.id.buttonRegresarHome);
        buttonRegresarHome.setOnClickListener(v -> {
            regresarHome();
        });

    }

    private void restartGame() {

        Intent intent = new Intent(GameResultActivity.this, SymbolSelectionActivity.class);
        intent.putExtra("modoJuego", modoJuego);
        startActivity(intent);

    }

    private void regresarHome() {

        Intent intent = new Intent(GameResultActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

    }

}