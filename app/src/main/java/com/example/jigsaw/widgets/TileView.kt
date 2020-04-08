package com.example.jigsaw.widgets

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.View
import com.example.jigsaw.Constants.DEFAULT_CAP_RADIUS
import com.example.jigsaw.Constants.DEFAULT_TILE_SIZE
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

    private val pathDrawer = Path()
    private val pathEraser = Path()
    private val bitmap = BitmapFactory.decodeResource(this.resources, R.drawable.genova)

    constructor(context: Context) : this(context, null)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val desiredWidth = (DEFAULT_TILE_SIZE + paddingLeft + paddingRight)
        val desiredHeight = (DEFAULT_TILE_SIZE + paddingTop + paddingBottom)

        setMeasuredDimension(desiredWidth, desiredHeight)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val newRect: Rect = canvas.clipBounds
        newRect.inset((-DEFAULT_CAP_RADIUS * 2).toInt(), (-DEFAULT_CAP_RADIUS * 2).toInt())  //make the rect larger
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // TODO check if this case works.
            canvas.clipOutRect(newRect)
        } else {
            canvas.clipRect(newRect, Region.Op.REPLACE)
        }

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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            canvas.clipOutPath(pathEraser)
        } else {
            canvas.clipPath(pathEraser, Region.Op.DIFFERENCE)
        }


        val iIndex = 0 + (DEFAULT_TILE_SIZE * tile.index.col)
        val jIndex = 0 + (DEFAULT_TILE_SIZE * tile.index.row)

        val bitmapSize = DEFAULT_TILE_SIZE + 2 * (DEFAULT_CAP_RADIUS + getCapRadiusToShow())

        val bmOverlay: Bitmap = Bitmap.createBitmap(
            bitmap,
            iIndex,
            jIndex,
            bitmapSize.toInt(),
            bitmapSize.toInt()
        )
        canvas.clipPath(pathDrawer)
        canvas.drawBitmap(bmOverlay, -(DEFAULT_CAP_RADIUS + getCapRadiusToShow()), -(DEFAULT_CAP_RADIUS + getCapRadiusToShow()), PaintManager.bitmapPaint);
    }

    private fun getCapRadiusToShow() = DEFAULT_CAP_RADIUS / 2
}