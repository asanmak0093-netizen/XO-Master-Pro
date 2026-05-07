package com.xomaster.pro

class GameLogic {

    private val board = Array(3) { IntArray(3) { -1 } }
    private var currentPlayer = 0

    fun getCurrentPlayer(): Int = currentPlayer

    fun makeMove(row: Int, col: Int): Boolean {
        if (board[row][col] != -1) return false
        board[row][col] = currentPlayer
        return true
    }

    fun checkWinner(): Int {
        for (i in 0..2) {
            if (board[i][0] != -1 && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                return board[i][0]
            }
            if (board[0][i] != -1 && board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
                return board[0][i]
            }
        }
        if (board[0][0] != -1 && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            return board[0][0]
        }
        if (board[0][2] != -1 && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            return board[0][2]
        }
        return -1
    }

    fun isBoardFull(): Boolean {
        for (row in board) {
            for (cell in row) {
                if (cell == -1) return false
            }
        }
        return true
    }

    fun switchPlayer() {
        currentPlayer = if (currentPlayer == 0) 1 else 0
    }

    fun resetGame() {
        for (i in 0..2) {
            for (j in 0..2) {
                board[i][j] = -1
            }
        }
        currentPlayer = 0
    }
}
