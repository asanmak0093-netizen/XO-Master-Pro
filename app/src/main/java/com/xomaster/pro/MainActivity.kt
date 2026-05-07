package com.xomaster.pro

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.xomaster.pro.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val gameLogic = GameLogic()
    private val buttons = mutableListOf<Button>()
    private var scoreX = 0
    private var scoreO = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        buttons.addAll(
            listOf(
                binding.btn00, binding.btn01, binding.btn02,
                binding.btn10, binding.btn11, binding.btn12,
                binding.btn20, binding.btn21, binding.btn22
            )
        )

        for (i in buttons.indices) {
            buttons[i].setOnClickListener { onButtonClick(i) }
        }

        binding.btnReset.setOnClickListener { resetGame() }
        updateScoreDisplay()
        updateTurnIndicator()
    }

    private fun onButtonClick(index: Int) {
        val row = index / 3
        val col = index % 3

        if (!gameLogic.makeMove(row, col)) return

        val player = gameLogic.getCurrentPlayer()
        val symbol = if (player == 0) "X" else "O"
        val color = if (player == 0) Color.parseColor("#FF1744") else Color.parseColor("#2979FF")

        buttons[index].apply {
            text = symbol
            setTextColor(color)
            textSize = 48f
            isEnabled = false
        }

        val winner = gameLogic.checkWinner()
        if (winner != -1) {
            val winnerSymbol = if (winner == 0) "X" else "O"
            if (winner == 0) scoreX++ else scoreO++
            updateScoreDisplay()
            showResultDialog("اللاعب $winnerSymbol يفوز!")
            return
        }

        if (gameLogic.isBoardFull()) {
            showResultDialog("تعادل!")
            return
        }

        gameLogic.switchPlayer()
        updateTurnIndicator()
    }

    private fun updateScoreDisplay() {
        binding.tvScoreX.text = "X: $scoreX"
        binding.tvScoreO.text = "O: $scoreO"
    }

    private fun updateTurnIndicator() {
        val player = gameLogic.getCurrentPlayer()
        val symbol = if (player == 0) "X" else "O"
        val color = if (player == 0) Color.parseColor("#FF1744") else Color.parseColor("#2979FF")
        binding.tvTurn.text = "الدور: $symbol"
        binding.tvTurn.setTextColor(color)
    }

    private fun showResultDialog(message: String) {
        AlertDialog.Builder(this)
            .setTitle("نتيجة اللعبة")
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton("لعبة جديدة") { _, _ -> resetGame() }
            .show()
    }

    private fun resetGame() {
        gameLogic.resetGame()
        for (button in buttons) {
            button.text = ""
            button.isEnabled = true
        }
        updateTurnIndicator()
    }
}
