package com.xadrez.app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : AppCompatActivity() {
    private lateinit var player1EditText: EditText
    private lateinit var player2EditText: EditText
    private lateinit var startGameButton: Button
    private lateinit var vsComputerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeViews()
        setupListeners()
    }

    private fun initializeViews() {
        player1EditText = findViewById(R.id.player1EditText)
        player2EditText = findViewById(R.id.player2EditText)
        startGameButton = findViewById(R.id.startGameButton)
        vsComputerButton = findViewById(R.id.vsComputerButton)
    }

    private fun setupListeners() {
        startGameButton.setOnClickListener {
            validateAndStartGame(false)
        }

        vsComputerButton.setOnClickListener {
            validateAndStartGame(true)
        }
    }

    private fun validateAndStartGame(vsComputer: Boolean) {
        val player1Name = player1EditText.text.toString().trim()
        val player2Name = if (vsComputer) "Computador" else player2EditText.text.toString().trim()

        if (player1Name.isEmpty() || (!vsComputer && player2Name.isEmpty())) {
            showError("Por favor, preencha os nomes dos jogadores")
            return
        }

        startGame(player1Name, player2Name, vsComputer)
    }

    private fun showError(message: String) {
        MaterialAlertDialogBuilder(this)
            .setTitle("Erro")
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }

    private fun startGame(player1: String, player2: String, vsComputer: Boolean) {
        val intent = Intent(this, GameActivity::class.java).apply {
            putExtra("PLAYER1_NAME", player1)
            putExtra("PLAYER2_NAME", player2)
            putExtra("VS_COMPUTER", vsComputer)
        }
        startActivity(intent)
    }
}
