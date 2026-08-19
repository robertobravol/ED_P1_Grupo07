package com.espol.ed_p1_grupo07;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
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

public class BoardActivity extends AppCompatActivity {

    private ImageButton buttonCloseGame;
    private ImageButton[][] cellButtons;
    private Button buttonSiguienteTurno;
    private Board board;
    private Player player;
    private Computer computer;

    private int modoJuego; // 0 = Humano vs PC, 1 = Humano vs Humano, 2 = PC vs PC
    private int turnoActual;
    private int playerSymbol;
    private Computer computer1;
    private Computer computer2;

    // NUEVO: Bandera de seguridad para evitar dobles turnos
    private boolean isCalculating = false;

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
        this.buttonCloseGame.setOnClickListener(v -> closeGame());

        this.buttonSiguienteTurno = findViewById(R.id.buttonSiguienteTurno);

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

        button00.setOnClickListener(v -> procesarClicHumano(0, 0));
        button01.setOnClickListener(v -> procesarClicHumano(0, 1));
        button02.setOnClickListener(v -> procesarClicHumano(0, 2));

        button10.setOnClickListener(v -> procesarClicHumano(1, 0));
        button11.setOnClickListener(v -> procesarClicHumano(1, 1));
        button12.setOnClickListener(v -> procesarClicHumano(1, 2));

        button20.setOnClickListener(v -> procesarClicHumano(2, 0));
        button21.setOnClickListener(v -> procesarClicHumano(2, 1));
        button22.setOnClickListener(v -> procesarClicHumano(2, 2));

        playerSymbol = getIntent().getIntExtra("playerSymbol", Board.X);
        modoJuego = getIntent().getIntExtra("modoJuego", 0);

        this.board = new Board();

        if (modoJuego == 1 || modoJuego == 2) {
            turnoActual = playerSymbol;
        } else {
            turnoActual = Board.X;
        }

        if (modoJuego == 2) {
            // A la PC 1 le damos tu símbolo elegido, a la PC 2 el contrario
            this.computer1 = new Computer(playerSymbol);
            this.computer2 = new Computer(-playerSymbol);

            buttonSiguienteTurno.setVisibility(View.VISIBLE);
            buttonSiguienteTurno.setOnClickListener(v -> jugarSiguienteTurnoPc());

        } else {
            buttonSiguienteTurno.setVisibility(View.GONE);

            this.player = new Player("Jugador 1", playerSymbol);
            this.computer = new Computer(-playerSymbol);

            if (modoJuego == 0 && computer.getsymbol() == Board.X) {
                makeComputerMoveWithDelay(computer);
            }
        }
    }

    private void closeGame() {
        Intent intent = new Intent(BoardActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void procesarClicHumano(int row, int column) {
        // La bandera isCalculating bloquea toques rebeldes
        if (modoJuego == 2 || isCalculating) return;
        if (modoJuego == 0 && turnoActual == computer.getsymbol()) return;

        selectCell(row, column, turnoActual);
    }

    private void jugarSiguienteTurnoPc() {
        if (modoJuego != 2 || isCalculating) return;

        Computer nextComputer = (turnoActual == computer1.getsymbol()) ? computer1 : computer2;
        makeComputerMoveWithDelay(nextComputer);
    }

    private void selectCell(int row, int column, int symbol) {
        if(!board.markCell(row, column, symbol)) {
            return;
        }

        if (symbol == Board.CIRCLE) {
            cellButtons[row][column].setImageResource(R.drawable.circle);
        } else {
            cellButtons[row][column].setImageResource(R.drawable.x);
        }

        int winner = board.checkWinner();
        if (winner != 0) {
            buttonSiguienteTurno.setEnabled(false);
            showResult(winner);
            return;
        }

        if (board.isFull()) {
            buttonSiguienteTurno.setEnabled(false);
            cleanBoardLayout();
            return;
        }

        turnoActual = (turnoActual == Board.X) ? Board.CIRCLE : Board.X;

        if (modoJuego == 0 && turnoActual == computer.getsymbol()) {
            makeComputerMoveWithDelay(computer);
        } else if (modoJuego == 2) {
            buttonSiguienteTurno.setEnabled(true);
        }
    }

    private void makeComputerMoveWithDelay(Computer pcActual) {
        // Levantamos la bandera de seguridad y bloqueamos botones
        isCalculating = true;
        setBoardEnabled(false);
        buttonSiguienteTurno.setEnabled(false);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            int[] cellSelected = pcActual.obtenerMejorMovimiento(board);
            if (cellSelected != null) {
                selectCell(cellSelected[0], cellSelected[1], pcActual.getsymbol());
            }

            // Bajamos la bandera una vez que todo el cálculo terminó
            isCalculating = false;

            if (modoJuego == 0 || modoJuego == 1) {
                setBoardEnabled(true);
            }
        }, 800);
    }

    private void setBoardEnabled(boolean enabled) {
        for (int i = 0; i <= 2; i++) {
            for (int j = 0; j <= 2; j++) {
                cellButtons[i][j].setEnabled(enabled);
            }
        }
    }

    private void showResult(int winnerSymbol) {

        setBoardEnabled(false);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {

            Intent intent = new Intent(BoardActivity.this, GameResultActivity.class);
            intent.putExtra("winnerSymbol", winnerSymbol);
            intent.putExtra("modoJuego", modoJuego);
            startActivity(intent);
            setBoardEnabled(true);

        }, 1000);

    }

    private void cleanBoardLayout() {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            for (int i = 0; i<=2; i++) {
                for (int j = 0; j<=2; j++) {
                    cellButtons[i][j].setImageDrawable(null);
                }
            }
            board.cleanBoard();

            if (modoJuego == 1 || modoJuego == 2) {
                turnoActual = playerSymbol;
            } else {
                turnoActual = Board.X;
            }

            if (modoJuego == 2) {
                buttonSiguienteTurno.setEnabled(true);
            } else if (modoJuego == 0 && computer.getsymbol() == Board.X) {
                makeComputerMoveWithDelay(computer);
            }

        }, 1000);
    }

}