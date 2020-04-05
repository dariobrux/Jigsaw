package com.example.jigsaw

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.Rect
import android.graphics.Region
import android.os.Build
import android.util.AttributeSet
import android.view.View

/**
 * Created by Dario Bruzzese
 * on 4/5/2020
 */
class TileView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val size = (DEFAULT_TILE_SIZE + DEFAULT_CAP_RADIUS).toInt()
        val desiredWidth = (DEFAULT_TILE_SIZE + paddingLeft + paddingRight)
        val desiredHeight = (DEFAULT_TILE_SIZE + paddingTop + paddingBottom)

        setMeasuredDimension(
            measureDimension(desiredWidth, widthMeasureSpec),
            measureDimension(desiredHeight, heightMeasureSpec)
        )
    }

    private fun measureDimension(desiredSize: Int, measureSpec: Int): Int {
        var result: Int
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)
        when (specMode) {
            MeasureSpec.EXACTLY -> {
                result = specSize
            }
            else -> {
                result = desiredSize
                if (specMode == MeasureSpec.AT_MOST) {
                    result = result.coerceAtMost(specSize)
                }
            }
        }
        return result
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val newRect: Rect = canvas.clipBounds
        newRect.inset((-DEFAULT_CAP_RADIUS * 2).toInt(), (-DEFAULT_CAP_RADIUS * 2).toInt())  //make the rect larger
        canvas.clipRect(newRect, Region.Op.REPLACE)

        val path = Path()
        path.addRect(RectManager.rectF, Path.Direction.CCW)

        // Draw the right cap
        path.addCircle(width.toFloat() + getCapRadiusToShow(), (height / 2f), DEFAULT_CAP_RADIUS, Path.Direction.CCW)

        // Draw the bottom cap
        path.addCircle(width / 2f, height.toFloat() + getCapRadiusToShow(), DEFAULT_CAP_RADIUS, Path.Direction.CCW)

        // Draw the left cap
        path.addCircle(-DEFAULT_CAP_RADIUS + getCapRadiusToShow(), (height / 2f), DEFAULT_CAP_RADIUS, Path.Direction.CCW)

        // Draw the top cap
        path.addCircle(width / 2f, -DEFAULT_CAP_RADIUS + getCapRadiusToShow(), DEFAULT_CAP_RADIUS, Path.Direction.CCW)

//        val path2 = Path()
//        path2.addCircle(width / 2f, -DEFAULT_CAP_RADIUS + getCapRadiusToShow(), DEFAULT_CAP_RADIUS, Path.Direction.CCW)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            canvas.clipOutPath(path2)
//        } else {
//            canvas.clipPath(path2, Region.Op.DIFFERENCE)
//        }

        canvas.drawPath(path, PaintManager.paint)

//        canvas.drawRect(RectManager.rect, PaintManager.paint)
//
//        // Draw the right cap
//        canvas.drawCircle(width.toFloat() + getCapRadiusToShow(), (height / 2f), DEFAULT_CAP_RADIUS, PaintManager.paint)
//
//        // Draw the bottom cap
//        canvas.drawCircle(width / 2f, height.toFloat() + getCapRadiusToShow(), DEFAULT_CAP_RADIUS, PaintManager.paint)
//
//        // Draw the left cap
//        canvas.drawCircle(-DEFAULT_CAP_RADIUS + getCapRadiusToShow(), (height / 2f), DEFAULT_CAP_RADIUS, PaintManager.paint)

        // Draw the top cap
//        val paint2 = Paint().apply {
//            color = Color.TRANSPARENT
//            style = Paint.Style.FILL_AND_STROKE
//            xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
//        }
//        canvas.drawCircle(width / 2f, -DEFAULT_CAP_RADIUS + getCapRadiusToShow(), DEFAULT_CAP_RADIUS, paint2)
    }

    private fun getCapRadiusToShow() = DEFAULT_CAP_RADIUS / 2

    companion object {
        const val DEFAULT_TILE_SIZE = 100
        const val DEFAULT_CAP_RADIUS = 15f
    }
}