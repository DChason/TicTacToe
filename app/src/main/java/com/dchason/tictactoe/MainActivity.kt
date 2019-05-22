package com.dchason.tictactoe

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val buttons = Array(3) { arrayOfNulls<Button>(3)}
    private var roundCount = 0
    private var player1Turn = true
    private var player1Points = 0
    private var player2Points = 0
    private lateinit var textViewPlayer1: TextView
    private lateinit var textViewPlayer2: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewPlayer1 = findViewById(R.id.text_view_player1)
        textViewPlayer1.text = getString(R.string.player1_string, player1Points)
        textViewPlayer2 = findViewById(R.id.text_view_player2)
        textViewPlayer2.text = getString(R.string.player2_string, player2Points)

        for (i in 0..2) {
            for (j in 0..2) {
                val buttonID = "button_$i$j"
                val resID = resources.getIdentifier(buttonID, "id", packageName)
                buttons[i][j] = findViewById(resID)
                buttons[i][j]?.setOnClickListener(this)
            }
        }

        val buttonReset = findViewById<Button>(R.id.button_reset)
        buttonReset.setOnClickListener{resetGame()}
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt("roundCount", roundCount)
        outState.putInt("player1Points", player1Points)
        outState.putInt("player2Points", player2Points)
        outState.putBoolean("player1Turn", player1Turn)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        roundCount = savedInstanceState.getInt("roundCount")
        player1Points = savedInstanceState.getInt("player1Points")
        player2Points = savedInstanceState.getInt("player2Points")
        player1Turn = savedInstanceState.getBoolean("player1Turn")
    }

    override fun onClick(v: View?) {
        if ((v as Button).text.toString() != "") {
            return
        }

        if (player1Turn) {
            (v).text = "X"
        } else {
            (v).text = "O"
        }

        roundCount++

        if (checkForWin()) {
            if (player1Turn) {
                player1Wins()
            } else {
                player2Wins()
            }
        } else if (roundCount == 9) {
            draw()
        } else {
            player1Turn = !player1Turn
        }
    }

    private fun checkForWin():Boolean {
        val field = Array(3) {arrayOfNulls<String>(3)}
        for (i in 0..2) {
            for (j in 0..2) {
                field[i][j] = buttons[i][j]?.text.toString()
            }
        }

        for (i in 0..2) {
            if ((field[i][0] == field[i][1] && field[i][0] == field[i][2] && field[i][0] != "")) {
                return true
            }
        }

        for (i in 0..2) {
            if ((field[0][i] == field[1][i] && field[0][i] == field[2][i] && field[0][i] != "")) {
                return true
            }
        }

        if ((field[0][0] == field[1][1] && field[0][0] == field[2][2] && field[0][0] != "")) {
            return true
        }

        if ((field[0][2] == field[1][1] && field[0][2] == field[2][0] && field[0][2] != "")) {
            return true
        }

        return false
    }

    private fun player1Wins() {
        player1Points++
        Toast.makeText(this, "Player 1 wins!", Toast.LENGTH_SHORT).show()
        updatePointsText()
        resetBoard()
    }

    private fun player2Wins() {
        player2Points++
        Toast.makeText(this, "Player 2 wins!", Toast.LENGTH_SHORT).show()
        updatePointsText()
        resetBoard()
    }

    private fun draw() {
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show()
        resetBoard()
    }

    private fun updatePointsText() {
        textViewPlayer1.text = getString(R.string.player1_string, player1Points)
        textViewPlayer2.text = getString(R.string.player2_string, player2Points)
    }

    private fun resetBoard() {
        for (i in 0..2) {
            for (j in 0..2) {
                buttons[i][j]?.text = ""
            }
        }
        roundCount = 0
        player1Turn = true
    }

    private fun resetGame() {
        player1Points = 0
        player2Points = 0
        updatePointsText()
        resetBoard()
    }
}