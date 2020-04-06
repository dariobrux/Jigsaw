package com.example.jigsaw.widgets

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.View
import com.example.jigsaw.R
import com.example.jigsaw.enums.CapMode
import com.example.jigsaw.managers.PaintManager
import com.example.jigsaw.managers.RectManager
import com.example.jigsaw.models.Tile


/**
 * Created by Dario Bruzzese
 * on 4/5/2020
 */
class TileView(context: Context, attributeSet: AttributeSet?) : View(context, attributeSet) {

    var tile = Tile()

    constructor(context: Context) : this(context, null)

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

        val pathDrawer = Path()
        val pathEraser = Path()
        pathDrawer.addRect(RectManager.rectF, Path.Direction.CCW)

        // Draw the left cap
        when (tile.capLeft) {
            CapMode.NONE -> {
                // Do Nothing
            }
            CapMode.EMPTY -> {
                pathEraser.addCircle(getCapRadiusToShow(), (height / 2f), DEFAULT_CAP_RADIUS, Path.Direction.CCW)
            }
            CapMode.FULL -> {
                pathDrawer.addCircle(-DEFAULT_CAP_RADIUS + getCapRadiusToShow(), (height / 2f), DEFAULT_CAP_RADIUS, Path.Direction.CCW)
            }
        }

        // Draw the top cap
        when (tile.capTop) {
            CapMode.NONE -> {
                // Do Nothing
            }
            CapMode.EMPTY -> {
                pathEraser.addCircle(width / 2f, getCapRadiusToShow(), DEFAULT_CAP_RADIUS, Path.Direction.CCW)
            }
            CapMode.FULL -> {
                pathDrawer.addCircle(width / 2f, -DEFAULT_CAP_RADIUS + getCapRadiusToShow(), DEFAULT_CAP_RADIUS, Path.Direction.CCW)
            }
        }

        // Draw the right cap
        when (tile.capRight) {
            CapMode.NONE -> {
                // Do Nothing
            }
            CapMode.EMPTY -> {
                pathEraser.addCircle(width.toFloat() - getCapRadiusToShow(), (height / 2f), DEFAULT_CAP_RADIUS, Path.Direction.CCW)
            }
            CapMode.FULL -> {
                pathDrawer.addCircle(width.toFloat() + getCapRadiusToShow(), (height / 2f), DEFAULT_CAP_RADIUS, Path.Direction.CCW)
            }
        }

        // Draw the bottom cap
        when (tile.capBottom) {
            CapMode.NONE -> {
                // Do Nothing
            }
            CapMode.EMPTY -> {
                pathEraser.addCircle(width / 2f, height.toFloat() - getCapRadiusToShow(), DEFAULT_CAP_RADIUS, Path.Direction.CCW)
            }
            CapMode.FULL -> {
                pathDrawer.addCircle(width / 2f, height.toFloat() + getCapRadiusToShow(), DEFAULT_CAP_RADIUS, Path.Direction.CCW)
            }
        }
        val paint = Paint()
//        paint.style = Paint.Style.FILL_AND_STROKE
//        paint.color = Color.RED
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.ADD)

//        canvas.drawBitmap(cropBitmap1(), -(DEFAULT_CAP_RADIUS + getCapRadiusToShow()), 0f, paint)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            canvas.clipOutPath(pathEraser)
        } else {
            canvas.clipPath(pathEraser, Region.Op.DIFFERENCE)
        }

        canvas.drawPath(pathDrawer, PaintManager.paint)

        canvas.drawBitmap(cropBitmap1(), -0f, 0f, paint)

//        val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.genova)
//        val croppedBitmap: Bitmap = Bitmap.createBitmap(bitmap, 0, 0, 100, 100)
//
    }

    private fun cropBitmap1(): Bitmap {
        val bitmap = BitmapFactory.decodeResource(this.resources, R.drawable.genova)
        val iIndex = 0 + (DEFAULT_TILE_SIZE * tile.index.col)
        val jIndex = 0 + (DEFAULT_TILE_SIZE * tile.index.row)
        val bmOverlay: Bitmap = Bitmap.createBitmap(bitmap, iIndex, jIndex, (DEFAULT_TILE_SIZE + DEFAULT_CAP_RADIUS + getCapRadiusToShow()).toInt(), (DEFAULT_TILE_SIZE + DEFAULT_CAP_RADIUS + getCapRadiusToShow()).toInt())
//        val bmOverlay = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
        val paint = Paint()
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.ADD)
        val canvas = Canvas(bmOverlay)
//        canvas.drawBitmap(bitmap, 0f, 0f, null)
//        canvas.drawRect(0f, 0f, 100f, 100f, paint)
        return bmOverlay
    }

    private fun getCapRadiusToShow() = DEFAULT_CAP_RADIUS / 2

    companion object {
        const val DEFAULT_TILE_SIZE = 100
        const val DEFAULT_CAP_RADIUS = 15f
    }
}