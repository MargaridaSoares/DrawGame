package com.example.drawgame

import android.graphics.Path

data class Point(val x: Float, val y:Float)
data class Line(val listPoints: List<Point>)
data class PlayModel(var lines: List<Line>)