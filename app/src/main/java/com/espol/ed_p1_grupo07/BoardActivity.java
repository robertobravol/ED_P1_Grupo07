package com.espol.ed_p1_grupo07;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.os.Handler;
import android.view.View;
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
    private ImageButton[][] cellButtons;

    private Board board;
    private Player player;
    private Computer computer;

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

        this.buttonCloseGame = findViewById(R.id.buttonCloseGame);
        this.buttonCloseGame.setOnClickListener(v -> {
            closeGame();
        });

        // Configuracion de botones para marcar cada celda
        ImageButton button00 = findViewById(R.id.cell0);
        ImageButton button01 = findViewById(R.id.cell1);
        ImageButton button02 = findViewById(R.id.cell2);

        ImageButton button10 = findViewById(R.id.cell3);
        ImageButton button11 = findViewById(R.id.cell4);
        ImageButton button12 = findViewById(R.id.cell5);

        ImageButton button20 = findViewById(R.id.cell6);
        ImageButton button21 = findViewById(R.id.cell7);
        ImageButton button22 = findViewById(R.id.cell8);

        cellButtons = new ImageButton[][] {
                {button00, button01, button02},
                {button10, button11, button12},
                {button20, button21, button22}
        };

        button00.setOnClickListener(v -> selectCell(0, 0, player.getSymbol()));
        button01.setOnClickListener(v -> selectCell(0, 1, player.getSymbol()));
        button02.setOnClickListener(v -> selectCell(0, 2, player.getSymbol()));

        button10.setOnClickListener(v -> selectCell(1, 0, player.getSymbol()));
        button11.setOnClickListener(v -> selectCell(1, 1, player.getSymbol()));
        button12.setOnClickListener(v -> selectCell(1, 2, player.getSymbol()));

        button20.setOnClickListener(v -> selectCell(2, 0, player.getSymbol()));
        button21.setOnClickListener(v -> selectCell(2, 1, player.getSymbol()));
        button22.setOnClickListener(v -> selectCell(2, 2, player.getSymbol()));

        // Símbolo seleccionado por el jugador
        int playerSymbol = getIntent().getIntExtra("playerSymbol", Board.X);

        this.player = new Player("Jugador", playerSymbol);
        this.computer = new Computer(-playerSymbol); // Recibe el símbolo opuesto del jugador.
        this.board = new Board(); // Tablero Vacío
    }

    // Finaliza el juego sin terminar la partida y lo envía al menú principal (MainActivity).
    private void closeGame() {
        Intent intent = new Intent(BoardActivity.this, MainActivity.class);
        startActivity(intent);
    }

    // Selecciona la celda y lo marca en la celda correspondiente en la UI.
    private void selectCell(int row, int column, int playerSymbol) {
        if(!board.markCell(row, column, playerSymbol)) {
            return; // La celda no fue marcada
        }

        if (playerSymbol == Board.CIRCLE) {
            cellButtons[row][column].setImageResource(R.drawable.circle);
        } else {
            cellButtons[row][column].setImageResource(R.drawable.x);
        }

        // Verifica si alguien ganó la partida.
        if (board.checkWinner() != 0) {
            showResult();
            return;
        }

        // Si el tablero está lleno, se limpia el tablero.
        if (board.isFull()) {
            cleanBoardLayout();
            return;
        }

        // Llama a la computadora si acaba de jugar el humano
        if (playerSymbol == player.getSymbol()) {
            makeComputerMove();
        }
    }

    private void makeComputerMove() {
        int[] cellSelectedByComputer = computer.obtenerMejorMovimiento(board);
        selectCell(cellSelectedByComputer[0], cellSelectedByComputer[1], computer.getsymbol());
    }

    private void setBoardEnabled(boolean enabled) {
        for (int i = 0; i <= 2; i++) {
            for (int j = 0; j <= 2; j++) {
                cellButtons[i][j].setEnabled(enabled);
            }
        }
    }

    // Muestra el resultado de la partida.
    private void showResult() {
        setBoardEnabled(false);
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            View resultGame = findViewById(R.id.resultContainer);
            resultGame.setVisibility(View.VISIBLE);

            setBoardEnabled(true);
        }, 1000);
    }

    // Limpia el tablero internamente y en la UI
    private void cleanBoardLayout() {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            for (int i = 0; i<=2; i++) {
                for (int j = 0; j<=2; j++) {
                    cellButtons[i][j].setImageDrawable(null);
                }
            }
            board.cleanBoard();
        }, 1000);
    }
}