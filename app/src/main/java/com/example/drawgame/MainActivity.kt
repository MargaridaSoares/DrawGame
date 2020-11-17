package com.example.drawgame


import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

@Suppress("UNSAFE_CALL_ON_PARTIALLY_DEFINED_RESOURCE")
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var word: String
        var player: String
        var round: String

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.activity_main)
            buttonStartLandscape.setOnClickListener {
                word = editWordLandscape.text.toString()
                player = EditPlayersLandScape.text.toString()
                round = EditRoundLandscape.text.toString()
                val intent = Intent(this, DrawActivity::class.java)

                verify(word, player, round, intent)

            }
        } else {
            setContentView(R.layout.activity_main)
            buttonStart.setOnClickListener {
                word = editWord.text.toString()
                player = EditPlayers.text.toString()
                round = EditRound.text.toString()
                val intent = Intent(this, DrawActivity::class.java)

                verify(word, player, round, intent)
            }
        }
    }

    fun verify(word: String, player: String, round: String, intent: Intent) {
        if (word.isEmpty() || player.isEmpty() || round.isEmpty()) {
            Toast.makeText(this, "Some parameters are empty", Toast.LENGTH_SHORT).show()
        } else if (player.toInt() < 5 || round.toInt() < 1) {
            Toast.makeText(this, "Number of players or rounds wrong", Toast.LENGTH_SHORT).show()
        } else {
            Log.d("WORDS", "Rounds $round , players $player , word $word")

            intent.putExtra("Rounds", round)
            intent.putExtra("Players", player)
            intent.putExtra("Word", word)
            startActivity(intent)
        }
    }
}