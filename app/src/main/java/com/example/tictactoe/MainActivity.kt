package com.example.tictactoe

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var playerOneTurn = true
    private var playerOneMoves = mutableListOf<Int>()
    private var playerTwoMoves = mutableListOf<Int>()

    private val possibleWins : Array<List<Int>> = arrayOf(
        listOf(1,2,3),
        listOf(4,5,6),
        listOf(7,8,9),

        listOf(1,4,7),
        listOf(2,5,8),
        listOf(3,6,9),

        listOf(1,5,9),
        listOf(3,5,7)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpBoard()
    }

    private fun setUpBoard() {
        var counter = 1

        val params1 = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            0
        )

        val params2 = LinearLayout.LayoutParams(
            0,
            LinearLayout.LayoutParams.MATCH_PARENT
        )

        for (i in 1..3) {
            val linearLayout = LinearLayout(this)
            linearLayout.orientation = LinearLayout.HORIZONTAL
            linearLayout.layoutParams = params1
            params1.weight = 1.0F

            for (j in 1..3) {
                val button = Button(this)
                button.id = counter
                button.textSize = 42.0F
                button.setTextColor(ContextCompat.getColor(this, R.color.teal_200))
                button.layoutParams = params2
                params2.weight = 1.0F

                button.setOnClickListener {
                    recordMove(it)
                }
                linearLayout.addView(button)
                counter++
            }
            Board.addView(linearLayout)
        }
    }

    private fun recordMove(view: View) {
        val button = view as Button
        val id = button.id

        if (playerOneTurn) {
            playerOneMoves.add(id)

            button.text = "O"
            button.isEnabled = false
            if (checkWin(playerOneMoves)) {
                showWinMessage(player_one_label)
            }
            else {
                playerOneTurn = false
                togglePlayerTurn(Player2, Player1)
            }
        }
        else {
            playerTwoMoves.add(id)

            button.text = "X"
            button.isEnabled = false
            if (checkWin(playerTwoMoves)) {
                showWinMessage(player_two_label)
            }
            else {
                playerOneTurn = true
                togglePlayerTurn(Player1, Player2)
            }
        }
    }

    private fun checkWin(moves: List<Int>): Boolean {
        var won = false
        if (moves.size >= 3) {
            run loop@{
                possibleWins.forEach {
                    if (moves.containsAll(it)) {
                        won = true
                        return@loop
                    }
                }
            }
        }
        return won
    }

    private fun togglePlayerTurn(playerOn: TextView, playerOff: TextView) {
        playerOff.setTextColor(ContextCompat.getColor(this, R.color.white))
        playerOff.setTypeface(null, Typeface.NORMAL)

        playerOn.setTextColor(ContextCompat.getColor(this, R.color.teal_200))
        playerOn.setTypeface(null, Typeface.BOLD)
    }

    private fun showWinMessage(player: EditText) {
        var playerName = player.text.toString()
        if (playerName.isBlank()) {
            playerName = player.hint.toString()
        }
        Toast.makeText(this, "Congratulations!!! $playerName You WON", Toast.LENGTH_SHORT).show()
    }
}