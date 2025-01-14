package com.xadrez.app

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class GameActivity : AppCompatActivity() {
    private lateinit var gameBoard: Array<Array<ImageView>>
    private lateinit var statusTextView: TextView
    private lateinit var game: ChessGame
    private var selectedPosition: Pair<Int, Int>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val player1Name = intent.getStringExtra("PLAYER1_NAME") ?: "Jogador 1"
        val player2Name = intent.getStringExtra("PLAYER2_NAME") ?: "Jogador 2"
        val vsComputer = intent.getBooleanExtra("VS_COMPUTER", false)

        initializeViews()
        initializeGame(player1Name, player2Name, vsComputer)
    }

    private fun initializeViews() {
        statusTextView = findViewById(R.id.statusTextView)
        gameBoard = Array(8) { row ->
            Array(8) { col ->
                findViewById<ImageView>(
                    resources.getIdentifier(
                        "square_${row}_${col}",
                        "id",
                        packageName
                    )
                ).apply {
                    setOnClickListener { onSquareClick(row, col) }
                }
            }
        }
    }

    private fun initializeGame(player1: String, player2: String, vsComputer: Boolean) {
        game = ChessGame(player1, player2, vsComputer)
        updateBoardUI()
        updateStatusText()
    }

    private fun onSquareClick(row: Int, col: Int) {
        if (selectedPosition == null) {
            if (game.canSelectPiece(row, col)) {
                selectedPosition = row to col
                highlightPossibleMoves(row, col)
            }
        } else {
            val (selectedRow, selectedCol) = selectedPosition!!
            if (game.makeMove(selectedRow, selectedCol, row, col)) {
                updateBoardUI()
                updateStatusText()
                checkGameEnd()
            }
            clearHighlights()
            selectedPosition = null
        }
    }

    private fun updateBoardUI() {
        for (row in 0..7) {
            for (col in 0..7) {
                val piece = game.getPieceAt(row, col)
                val imageView = gameBoard[row][col]
                if (piece != null) {
                    val resourceId = resources.getIdentifier(
                        "piece_${piece.type.toLowerCase()}_${piece.color.toLowerCase()}",
                        "drawable",
                        packageName
                    )
                    imageView.setImageResource(resourceId)
                } else {
                    imageView.setImageDrawable(null)
                }
            }
        }
    }

    private fun highlightPossibleMoves(row: Int, col: Int) {
        val possibleMoves = game.getPossibleMoves(row, col)
        possibleMoves.forEach { (moveRow, moveCol) ->
            gameBoard[moveRow][moveCol].setBackgroundColor(
                ContextCompat.getColor(this, R.color.possible_move)
            )
        }
        gameBoard[row][col].setBackgroundColor(
            ContextCompat.getColor(this, R.color.selected_square)
        )
    }

    private fun clearHighlights() {
        for (row in 0..7) {
            for (col in 0..7) {
                gameBoard[row][col].setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        if ((row + col) % 2 == 0) R.color.square_light else R.color.square_dark
                    )
                )
            }
        }
    }

    private fun updateStatusText() {
        statusTextView.text = "Vez de ${game.getCurrentPlayerName()}"
    }

    private fun checkGameEnd() {
        if (game.isGameOver()) {
            MaterialAlertDialogBuilder(this)
                .setTitle("Fim de Jogo")
                .setMessage("${game.getWinnerName()} venceu!")
                .setPositiveButton("Novo Jogo") { _, _ ->
                    finish()
                }
                .setNegativeButton("Sair") { _, _ ->
                    finish()
                }
                .setCancelable(false)
                .show()
        }
    }
}
