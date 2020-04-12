package com.example.jigsaw.widgets

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.jigsaw.Constants.DEFAULT_CAP_RADIUS
import com.example.jigsaw.Constants.DEFAULT_TILE_SIZE
import com.example.jigsaw.R
import com.example.jigsaw.enums.CapMode
import com.example.jigsaw.models.TileFull


/**
 * Created by Dario Bruzzese
 * on 4/5/2020
 */
class Tile2View(context: Context, attributeSet: AttributeSet?) : View(context, attributeSet) {

    var tile = TileFull()

    private val pathDrawer = Path()
    private val pathEraser = Path()

    constructor(context: Context) : this(context, null)

    init {
        val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.Tile2View)
        tile.capLeft = CapMode.values()[typedArray.getInt(R.styleable.Tile2View_tv_capLeft, tile.capLeft.ordinal)]
        tile.capTop = CapMode.values()[typedArray.getInt(R.styleable.Tile2View_tv_capTop, tile.capTop.ordinal)]
        tile.capRight = CapMode.values()[typedArray.getInt(R.styleable.Tile2View_tv_capRight, tile.capRight.ordinal)]
        tile.capBottom = CapMode.values()[typedArray.getInt(R.styleable.Tile2View_tv_capBottom, tile.capBottom.ordinal)]
        typedArray.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val desiredWidth = (DEFAULT_TILE_SIZE + paddingLeft + paddingRight)
        val desiredHeight = (DEFAULT_TILE_SIZE + paddingTop + paddingBottom)

        setMeasuredDimension(desiredWidth, desiredHeight)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (isClearCanvasMode) {
            pathDrawer.reset()
            pathEraser.reset()
            isClearCanvasMode = false
            return
        }

        pathDrawer.reset()
        pathEraser.reset()

//        val newRect: Rect = canvas.clipBounds
//        newRect.inset((-DEFAULT_CAP_RADIUS * 2).toInt(), (-DEFAULT_CAP_RADIUS * 2).toInt())  //make the rect larger
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            // TODO check if this case works.
//            canvas.clipOutRect(newRect)
//        } else {
//            canvas.clipRect(newRect, Region.Op.REPLACE)
//        }

        // left
        pathDrawer.moveTo(0f, DEFAULT_TILE_SIZE.toFloat())

        val offsetLeft = 3f / 2f
        when (tile.capLeft) {
            CapMode.NONE -> {
                // Do nothing
            }
            else -> {
                pathDrawer.lineTo(0f, DEFAULT_TILE_SIZE / 2f + getCapRadiusToShow())
                val offset = if (tile.capLeft == CapMode.FULL) {
                    -offsetLeft
                } else {
                    offsetLeft
                }
                pathDrawer.cubicTo(
                    offset * DEFAULT_CAP_RADIUS,
                    DEFAULT_TILE_SIZE.toFloat(),
                    offset * DEFAULT_CAP_RADIUS,
                    0f,
                    0f,
                    DEFAULT_TILE_SIZE / 2f - getCapRadiusToShow()
                )
            }
        }

        pathDrawer.lineTo(0f, 0f)


//        // top
//        pathDrawer.moveTo(0f, 0f)
//
//        pathDrawer.lineTo(DEFAULT_TILE_SIZE / 2f - getCapRadiusToShow(), 0f)
//
//        val offset = 3f / 2f
//        pathDrawer.cubicTo(
//            0f,
//            -offset * DEFAULT_CAP_RADIUS,
//            DEFAULT_TILE_SIZE.toFloat(),
//            -offset * DEFAULT_CAP_RADIUS,
//            DEFAULT_TILE_SIZE / 2f + getCapRadiusToShow(),
//            0f
//        )
//
//        pathDrawer.lineTo(DEFAULT_TILE_SIZE.toFloat(), 0f)
//        canvas.drawLine(0f, 10f, DEFAULT_TILE_SIZE / 2f, 10f, Paint().apply { color = Color.RED })

//        pathDrawer.lineTo(DEFAULT_TILE_SIZE.toFloat(), DEFAULT_TILE_SIZE.toFloat())
//
//        pathDrawer.close()

        val paint = Paint()
        paint.isAntiAlias = true
        paint.color = Color.BLACK
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 1f
        canvas.drawPath(pathDrawer, paint)

//        pathDrawer.addRect(RectManager.rectF, Path.Direction.CCW)
//
//        // Draw the left cap
//        when (tile.capLeft) {
//            CapMode.NONE -> {
//                // Do Nothing
//            }
//            CapMode.EMPTY -> {
//                pathEraser.addCircle(getCapRadiusToShow(), (height / 2f), DEFAULT_CAP_RADIUS, Path.Direction.CCW)
//            }
//            CapMode.FULL -> {
//                pathDrawer.addCircle(-DEFAULT_CAP_RADIUS + getCapRadiusToShow(), (height / 2f), DEFAULT_CAP_RADIUS, Path.Direction.CCW)
//            }
//        }
//
//        // Draw the top cap
//        when (tile.capTop) {
//            CapMode.NONE -> {
//                // Do Nothing
//            }
//            CapMode.EMPTY -> {
//                pathEraser.addCircle(width / 2f, getCapRadiusToShow(), DEFAULT_CAP_RADIUS, Path.Direction.CCW)
//            }
//            CapMode.FULL -> {
//                pathDrawer.addCircle(width / 2f, -DEFAULT_CAP_RADIUS + getCapRadiusToShow(), DEFAULT_CAP_RADIUS, Path.Direction.CCW)
//            }
//        }
//
//        // Draw the right cap
//        when (tile.capRight) {
//            CapMode.NONE -> {
//                // Do Nothing
//            }
//            CapMode.EMPTY -> {
//                pathEraser.addCircle(width.toFloat() - getCapRadiusToShow(), (height / 2f), DEFAULT_CAP_RADIUS, Path.Direction.CCW)
//            }
//            CapMode.FULL -> {
//                pathDrawer.addCircle(width.toFloat() + getCapRadiusToShow(), (height / 2f), DEFAULT_CAP_RADIUS, Path.Direction.CCW)
//            }
//        }
//
//        // Draw the bottom cap
//        when (tile.capBottom) {
//            CapMode.NONE -> {
//                // Do Nothing
//            }
//            CapMode.EMPTY -> {
//                pathEraser.addCircle(width / 2f, height.toFloat() - getCapRadiusToShow(), DEFAULT_CAP_RADIUS, Path.Direction.CCW)
//            }
//            CapMode.FULL -> {
//                pathDrawer.addCircle(width / 2f, height.toFloat() + getCapRadiusToShow(), DEFAULT_CAP_RADIUS, Path.Direction.CCW)
//            }
//        }
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            canvas.clipOutPath(pathEraser)
//        } else {
//            canvas.clipPath(pathEraser, Region.Op.DIFFERENCE)
//        }
//
//        canvas.clipPath(pathDrawer)
//
//        tile.bitmap?.let {
//            canvas.drawBitmap(it, -(DEFAULT_CAP_RADIUS + getCapRadiusToShow()), -(DEFAULT_CAP_RADIUS + getCapRadiusToShow()), PaintManager.bitmapPaint)
//        }
    }

    private fun getCapRadiusToShow() = DEFAULT_CAP_RADIUS / 2


    private var isClearCanvasMode = false

    fun reset() {
        isClearCanvasMode = true
        invalidate()
    }
}