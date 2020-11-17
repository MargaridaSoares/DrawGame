package com.example.drawgame

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_show_players.*


class ShowPlayersActivity : AppCompatActivity()  {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_players)

        var numberOfRounds = intent.extras?.get("Rounds").toString().toInt()
        var numberOfActualRound =  intent.extras?.get("ActualRound").toString().toInt()
        var numberOfPlayers = intent.extras?.get("Players").toString().toInt()
        var arrayList = arrayOf(intent.extras?.get("WordsList"))
        //var drawList = arrayOf(intent.extras?.get("DrawList"))

        val arrayPlayers = ArrayList<String>()
        for(player in 1..numberOfPlayers){
            arrayPlayers.add("Player $player")
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayPlayers)
        playerList.adapter = adapter

        /*playerList.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->

            val intent = Intent(this, ShowResults::class.java);

            intent.putExtra("Id", position)

            startActivity(intent)
        }*/

        Log.d("NUMBERACTUALROUNDS", "actualRound $numberOfActualRound numberOfRounds $numberOfRounds")
        //there is still rounds to do
        if(numberOfActualRound != numberOfRounds){
            buttonNextRound.setOnClickListener {
                if(wordNextRound.text.isEmpty()){
                    Toast.makeText(this, "Please write word for next game", Toast.LENGTH_SHORT).show()
                }
                else{
                    numberOfActualRound++
                    val intent = Intent(this, DrawActivity::class.java)
                    intent.putExtra("ActualRound", numberOfActualRound.toString())
                    intent.putExtra("Rounds", numberOfRounds.toString())
                    intent.putExtra("Players", numberOfPlayers.toString())
                    intent.putExtra("Word", wordNextRound.text)
                    startActivity(intent)
                }
            }
        }
        else{
            buttonNextRound.text = "New Game"
            wordNextRound.isVisible = false
            buttonNextRound.setOnClickListener{
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }

    }
}