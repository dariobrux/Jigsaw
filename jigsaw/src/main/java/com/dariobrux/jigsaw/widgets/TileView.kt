package com.dariobrux.jigsaw.widgets

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.core.graphics.drawable.toBitmap
import com.dariobrux.jigsaw.Constants.DEFAULT_CAP_RADIUS
import com.dariobrux.jigsaw.Constants.DEFAULT_TILE_SIZE
import com.dariobrux.jigsaw.R
import com.dariobrux.jigsaw.enums.CapMode
import com.dariobrux.jigsaw.interfaces.OnJigsawListener
import com.dariobrux.jigsaw.managers.PaintManager
import com.dariobrux.jigsaw.models.TileFull


/**
 * Created by Dario Bruzzese
 * on 4/5/2020
 */
class TileView(context: Context, attributeSet: AttributeSet?) : FrameLayout(context, attributeSet) {

    var tile = TileFull()
        set(value) {
            field = value
            paintBorder.color = value.tileDecorator?.borderColor ?: paintBorder.color
            paintBorder.strokeWidth = value.tileDecorator?.borderWidth ?: paintBorder.strokeWidth
        }

    private val pathDrawer = Path()

    private val paintBorder = Paint().apply {
        isAntiAlias = true
        color = Color.TRANSPARENT
        style = Paint.Style.STROKE
        strokeWidth = 0f
    }

    private var onJigsawListener: OnJigsawListener? = null

    constructor(context: Context) : this(context, null)

    init {
        val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.TileView)
        tile.bitmap = typedArray.getDrawable(R.styleable.TileView_tv_bitmap)?.toBitmap()
        tile.capLeft = CapMode.values()[typedArray.getInt(R.styleable.TileView_tv_capLeft, tile.capLeft.ordinal)]
        tile.capTop = CapMode.values()[typedArray.getInt(R.styleable.TileView_tv_capTop, tile.capTop.ordinal)]
        tile.capRight = CapMode.values()[typedArray.getInt(R.styleable.TileView_tv_capRight, tile.capRight.ordinal)]
        tile.capBottom = CapMode.values()[typedArray.getInt(R.styleable.TileView_tv_capBottom, tile.capBottom.ordinal)]
        paintBorder.color = typedArray.getColor(R.styleable.TileView_tv_borderColor, paintBorder.color)
        paintBorder.strokeWidth = typedArray.getDimension(R.styleable.TileView_tv_borderWidth, paintBorder.strokeWidth)
        typedArray.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val desiredWidth = (DEFAULT_TILE_SIZE + paddingLeft + paddingRight)
        val desiredHeight = (DEFAULT_TILE_SIZE + paddingTop + paddingBottom)

        setMeasuredDimension(desiredWidth, desiredHeight)
    }

    fun setOnJigsawListener(onJigsawListener: OnJigsawListener?) {
        this.onJigsawListener = onJigsawListener
    }

    override fun dispatchDraw(canvas: Canvas) {
        super.dispatchDraw(canvas)

        if (isClearCanvasMode) {
            pathDrawer.reset()
            isClearCanvasMode = false
            return
        }

        pathDrawer.reset()


        // left
        pathDrawer.moveTo(0f, DEFAULT_TILE_SIZE.toFloat())

        val offsetLeft = 5f / 3f
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

        // top
        val offsetTop = 5f / 3f
        when (tile.capTop) {
            CapMode.NONE -> {
                // Do nothing
            }
            else -> {
                pathDrawer.lineTo(DEFAULT_TILE_SIZE / 2f - getCapRadiusToShow(), 0f)
                val offset = if (tile.capTop == CapMode.FULL) {
                    -offsetTop
                } else {
                    offsetTop
                }
                pathDrawer.cubicTo(
                    0f,
                    offset * DEFAULT_CAP_RADIUS,
                    DEFAULT_TILE_SIZE.toFloat(),
                    offset * DEFAULT_CAP_RADIUS,
                    DEFAULT_TILE_SIZE / 2f + getCapRadiusToShow(),
                    0f
                )
            }
        }

        pathDrawer.lineTo(DEFAULT_TILE_SIZE.toFloat(), 0f)

        // right
        val offsetRight = 5f / 3f
        when (tile.capRight) {
            CapMode.NONE -> {
                // Do nothing
            }
            else -> {
                pathDrawer.lineTo(DEFAULT_TILE_SIZE.toFloat(), DEFAULT_TILE_SIZE / 2f - getCapRadiusToShow())
                val offset = if (tile.capRight == CapMode.FULL) {
                    offsetRight
                } else {
                    -offsetRight
                }
                pathDrawer.cubicTo(
                    offset * DEFAULT_CAP_RADIUS + DEFAULT_TILE_SIZE,
                    0f,
                    offset * DEFAULT_CAP_RADIUS + DEFAULT_TILE_SIZE,
                    DEFAULT_TILE_SIZE.toFloat(),
                    DEFAULT_TILE_SIZE.toFloat(),
                    DEFAULT_TILE_SIZE / 2f + getCapRadiusToShow()
                )
            }
        }

        pathDrawer.lineTo(DEFAULT_TILE_SIZE.toFloat(), DEFAULT_TILE_SIZE.toFloat())

        // bottom
        val offsetBottom = 5f / 3f
        when (tile.capBottom) {
            CapMode.NONE -> {
                // Do nothing
            }
            else -> {
                pathDrawer.lineTo(DEFAULT_TILE_SIZE / 2f + getCapRadiusToShow(), DEFAULT_TILE_SIZE.toFloat())
                val offset = if (tile.capBottom == CapMode.FULL) {
                    offsetBottom
                } else {
                    -offsetBottom
                }
                pathDrawer.cubicTo(
                    DEFAULT_TILE_SIZE.toFloat(),
                    offset * DEFAULT_CAP_RADIUS + DEFAULT_TILE_SIZE,
                    0f,
                    offset * DEFAULT_CAP_RADIUS + DEFAULT_TILE_SIZE,
                    DEFAULT_TILE_SIZE / 2f - getCapRadiusToShow(),
                    DEFAULT_TILE_SIZE.toFloat()
                )
            }
        }

        pathDrawer.close()

        canvas.clipPath(pathDrawer)

        tile.bitmap?.let {
            canvas.drawBitmap(it, -(DEFAULT_CAP_RADIUS + getCapRadiusToShow()), -(DEFAULT_CAP_RADIUS + getCapRadiusToShow()), PaintManager.bitmap)

            if (paintBorder.strokeWidth > 0) {
                canvas.drawPath(pathDrawer, paintBorder)
            }
        }

        onJigsawListener?.onTileGenerated(this)
        onJigsawListener = null
    }

    private fun getCapRadiusToShow() = DEFAULT_CAP_RADIUS / 2


    private var isClearCanvasMode = false

    fun reset() {
        isClearCanvasMode = true
        invalidate()
    }
}