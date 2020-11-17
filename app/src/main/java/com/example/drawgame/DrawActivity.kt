package com.example.drawgame

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.MotionEvent.ACTION_DOWN
import android.view.MotionEvent.ACTION_MOVE
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_draw.*

@Suppress("UNSAFE_CALL_ON_PARTIALLY_DEFINED_RESOURCE")
class DrawActivity  : AppCompatActivity(){
    var NUMBER_OF_ROUNDS = ""
    var NUMBER_OF_PLAYERS = ""
    var FIRST_WORD = ""
    val  TIME_GUESSING = 10000
    lateinit var count: CountDownTimer
    lateinit var line: Line
    var actualPlayer:Int = 1
    var actualRound: Int = 1
    val wordsList = mutableListOf<String>()
    lateinit var pointList: MutableList<Point>
    lateinit var lineList: MutableList<Line>
    //lateinit var drawList: MutableList<MutableList<Line>>

    //Permits the drawing, input is the view that actual draws with a brush
    @SuppressLint("ClickableViewAccessibility")
    fun draw(playView: PlayView){
        lineList = mutableListOf()
        playView.setOnTouchListener{ v, event  ->
            when(event.action){
                ACTION_DOWN -> {
                    var point = Point(event.x, event.y)
                    pointList = mutableListOf()
                    pointList.add(point)
                    line = Line(pointList)
                }
                ACTION_MOVE -> {
                    var pointTo = Point(event.x, event.y)
                    pointList.add(pointTo)
                    lineList.add(line)
                    playView.model = PlayModel(lineList)
                }
            }
            true
        }
    }

    //Update the timer, time is the time to draw and guess, counter is the text view to update
    @SuppressLint("SetTextI18n")
    fun timer(time: Long, counter: TextView){
        var timeRemain = time/ 1000
        if (time < 10000) {
            counter.text = "00:0${timeRemain.toInt()}"
        } else {
            counter.text = "00:${timeRemain.toInt()}"
        }
    }



    //Sees if the player have to draw or guess and also save the words and draws in two lists
    fun disableBasedOnPlayer(newWord: EditText, playView: PlayView){
        //player guess with a word
        if(actualPlayer % 2 == 0){
            newWord.text.clear()
            newWord.isEnabled = true
            playView.isEnabled = false

        }
        //player draws
        else{
            //adds the word to the list
            wordsList.add(newWord.text.toString())

            //set the newWord to the previous word written
            newWord.setText(wordsList[wordsList.size - 1])

            //adds the draw to the list
            //drawList.add(lineList)

            playView.isEnabled = true
            newWord.isEnabled = false

        }
    }

    private fun createObject(counter: TextView, newWord: EditText, playView: PlayView, countPlayers: TextView, changeRound: TextView){
        count = object : CountDownTimer(TIME_GUESSING.toLong(), 1000) {
            //update time
            override fun onTick(millisUntilFinished: Long) {
                timer(millisUntilFinished, counter)
            }
            @SuppressLint("SetTextI18n")
            override fun onFinish() {
                //if there is still players left to play
                if (actualPlayer.toString() != NUMBER_OF_PLAYERS) {
                    actualPlayer++
                    Toast.makeText(this@DrawActivity, "Next Player", Toast.LENGTH_SHORT).show()
                    count.start()
                    disableBasedOnPlayer(newWord, playView)
                    countPlayers.text = "$actualPlayer/$NUMBER_OF_PLAYERS"

                    //erases the draw
                    lineList = mutableListOf()

                    //when all the players played, but can still have a round to go
                } else {
                    counter.text = "00:00"

                    val intent = Intent(applicationContext, ShowPlayersActivity::class.java)

                    intent.putExtra("ActualRound", actualRound)
                    intent.putExtra("Rounds", NUMBER_OF_ROUNDS)
                    intent.putExtra("Players", NUMBER_OF_PLAYERS)
                    intent.putExtra("WordsList", arrayOf(wordsList))
                    //intent.putExtra("DrawList", arrayOf(drawList))

                    startActivity(intent)
                }
            }
        }
        count.start()
        //disableBasedOnPlayer()
    }

    @SuppressLint("SetTextI18n")
    fun landscape(){
        countPlayersLandscape.text = "$actualPlayer/$NUMBER_OF_PLAYERS"
        changeRoundLandscape.text = "$actualRound"
        newWordLandScape.setText(FIRST_WORD)

        newWordLandScape.isEnabled = false

        draw(playViewLandScape)
        timer(TIME_GUESSING.toLong(), counterLandscape)

        createObject(counterLandscape, newWordLandScape, playViewLandScape, countPlayersLandscape, changeRoundLandscape)
    }

    @SuppressLint("SetTextI18n")
    fun portrait(){
        //Set the number of rounds and list of players and the first word
        countPlayers.text = "$actualPlayer/$NUMBER_OF_PLAYERS"
        ChangeRound.text = "$actualRound"
        newWord.setText(FIRST_WORD)

        //TextEdit newWord cant be written
        newWord.isEnabled = false

        //activate the drawing
        draw(playView)
        //starting the countdown
        timer(TIME_GUESSING.toLong(), counter)

        createObject(counter, newWord, playView, countPlayers, ChangeRound)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_draw)

        //Get the number of rounds, players and first word
        NUMBER_OF_ROUNDS = intent.extras?.get("Rounds").toString()
        NUMBER_OF_PLAYERS = intent.extras?.get("Players").toString()
        FIRST_WORD = intent.extras?.get("Word").toString()
        var actualRoundString = intent.extras?.get("ActualRound").toString()

        Log.d("ROUND", "ActualRound: $actualRoundString")
        Log.d("PLAYER", "NUMBER_OF_PLAYER: $NUMBER_OF_PLAYERS")

        actualRound = if(!actualRoundString.equals("null"))
            actualRoundString.toInt()
        else 1
        //add to the list the first word
        wordsList.add(FIRST_WORD)

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            landscape()
        }
        else{
            portrait()
        }

    }

}