package com.example.pictionary

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import java.util.jar.Attributes


class DrawView(context: Context, attributes: AttributeSet): View(context,attributes) {
    //drawing path
    private var drawPath: Path = Path()

    //drawing and canvas paint
    private var drawPaint: Paint = Paint() //drawing and canvas paint
    private var canvasPaint: Paint

    //initial color
    private var paintColor = "#FFFF6666"

    //canvas
    private lateinit var drawCanvas: Canvas

    //canvas bitmap
    private lateinit var canvasBitmap: Bitmap

    init{
        drawPaint.color = Color.parseColor("#FFFF6666")
        //Now set the initial path properties:

        drawPaint.isAntiAlias = true;
        drawPaint.strokeWidth = 20F;
        drawPaint.style = Paint.Style.STROKE;
        drawPaint.strokeJoin = Paint.Join.ROUND;
        drawPaint.strokeCap = Paint.Cap.ROUND;
        //We will alter part of this code in the next tutorial when we implement the ability to
        // choose brush sizes, for now we set an arbitrary brush size. Setting the anti-alias,
        // stroke join and cap styles will make the user's drawings appear smoother.
        canvasPaint = Paint(Paint.DITHER_FLAG);
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        drawCanvas = Canvas(canvasBitmap);
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawBitmap(canvasBitmap, 0F, 0F, canvasPaint)
        canvas?.drawPath(drawPath, drawPaint);
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        val touchX = event!!.x
        val touchY = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> drawPath.moveTo(touchX, touchY)
            MotionEvent.ACTION_MOVE -> drawPath.lineTo(touchX, touchY)
            MotionEvent.ACTION_UP -> {
                drawCanvas.drawPath(drawPath, drawPaint)
                drawPath.reset()
            }
            else -> return false
        }

        invalidate();
        return true;
    }

    fun setColor(newColor: String = paintColor) {
        invalidate()
        drawPaint.color = Color.parseColor(newColor)
    }



}