package com.example.drawgame
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class PlayView(ctx: Context, attrs: AttributeSet?) : View(ctx, attrs) {

    var model: PlayModel? = null
        set(value) {
            field = value
            invalidate()
        }

    private val brush: Paint = Paint().apply {
        color = Color.parseColor("#000000")
        strokeWidth = 10f
        style = Paint.Style.STROKE
    }

    override fun onDraw(canvas: Canvas?) {
        val localModel = model
        if (localModel != null) {
            for(line in localModel.lines)  {
                for (point in line.listPoints){
                    var secondPoint : Point
                    if(line.listPoints.indexOf(point) + 1 >= line.listPoints.size){
                        secondPoint = point
                    }
                    else{
                        secondPoint = line.listPoints[(line.listPoints.indexOf(point) + 1)]
                    }
                    canvas?.drawLine(point.x, point.y, secondPoint.x, secondPoint.y, brush)
                }
            }
        }
    }
}