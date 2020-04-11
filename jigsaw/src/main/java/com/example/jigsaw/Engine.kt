package com.example.jigsaw

import android.graphics.Bitmap
import android.util.Log
import com.example.jigsaw.enums.CapMode
import com.example.jigsaw.enums.TilePosition
import com.example.jigsaw.models.TileEmpty
import com.example.jigsaw.models.Index
import com.example.jigsaw.models.Tile
import com.example.jigsaw.models.TileFull

/**
 * Created by Dario Bruzzese
 * on 4/5/2020
 */
class Engine(bitmap: Bitmap, items: Int, private val rows: Int, private val cols: Int) {

    var tileFullList = arrayListOf<Tile>()
    var tileEmptyList = arrayListOf<Tile>()

    init {

        Log.d("Engine", "$items items is -> ${rows}x$cols")

        var currentRightCap: CapMode = getRandomCapMode()
        val currentBottomCapList = mutableListOf<CapMode>()

        var position = 0

        repeat(rows) { i ->
            repeat(cols) { j ->
                val tilePosition = getTilePosition(i, j)
                Log.d("Engine", "$i, $j -> $tilePosition ")
                val tile = TileFull().apply {
                    when (tilePosition) {
                        TilePosition.LEFT_TOP -> {
                            this.capLeft = CapMode.NONE
                            this.capTop = CapMode.NONE
                            this.capRight = currentRightCap
                            this.capBottom = getRandomCapMode()
                        }
                        TilePosition.TOP -> {
                            this.capLeft = currentRightCap.inverse()
                            this.capTop = CapMode.NONE
                            this.capRight = getRandomCapMode()
                            this.capBottom = getRandomCapMode()
                        }
                        TilePosition.RIGHT_TOP -> {
                            this.capLeft = currentRightCap.inverse()
                            this.capTop = CapMode.NONE
                            this.capRight = CapMode.NONE
                            this.capBottom = getRandomCapMode()
                        }
                        TilePosition.LEFT -> {
                            this.capLeft = CapMode.NONE
                            this.capTop = currentBottomCapList[j].inverse()
                            this.capRight = getRandomCapMode()
                            this.capBottom = getRandomCapMode()
                        }
                        TilePosition.CENTER -> {
                            this.capLeft = currentRightCap.inverse()
                            this.capTop = currentBottomCapList[j].inverse()
                            this.capRight = getRandomCapMode()
                            this.capBottom = getRandomCapMode()
                        }
                        TilePosition.RIGHT -> {
                            this.capLeft = currentRightCap.inverse()
                            this.capTop = currentBottomCapList[j].inverse()
                            this.capRight = CapMode.NONE
                            this.capBottom = getRandomCapMode()
                        }
                        TilePosition.LEFT_BOTTOM -> {
                            this.capLeft = CapMode.NONE
                            this.capTop = currentBottomCapList[j].inverse()
                            this.capRight = getRandomCapMode()
                            this.capBottom = CapMode.NONE
                        }
                        TilePosition.BOTTOM -> {
                            this.capLeft = currentRightCap.inverse()
                            this.capTop = currentBottomCapList[j].inverse()
                            this.capRight = getRandomCapMode()
                            this.capBottom = CapMode.NONE
                        }
                        TilePosition.RIGHT_BOTTOM -> {
                            this.capLeft = currentRightCap.inverse()
                            this.capTop = currentBottomCapList[j].inverse()
                            this.capRight = CapMode.NONE
                            this.capBottom = CapMode.NONE
                        }
                    }
                    this.index = Index(i, j)
                    this.position = position

                    val iIndex = 0 + (Constants.DEFAULT_TILE_SIZE * j)
                    val jIndex = 0 + (Constants.DEFAULT_TILE_SIZE * i)

                    val bitmapSize = Constants.DEFAULT_TILE_SIZE + 2 * (Constants.DEFAULT_CAP_RADIUS + getCapRadiusToShow())

                    this.bitmap = Bitmap.createBitmap(
                        bitmap,
                        iIndex,
                        jIndex,
                        bitmapSize.toInt(),
                        bitmapSize.toInt()
                    )

                    position++
                }
                currentRightCap = tile.capRight
                currentBottomCapList.add(tile.capBottom)
                tileFullList.add(tile)
                tileEmptyList.add(TileEmpty())
                Log.d("Engine", "$i, $j -> $currentBottomCapList ")
            }
            if (i != 0) {
                currentBottomCapList.subList(0, currentBottomCapList.size / 2).clear()
            }
        }
    }

    private fun getRandomCapMode(): CapMode {
        return listOf(CapMode.FULL, CapMode.EMPTY).shuffled().first()
    }

    private fun getCapRadiusToShow() = Constants.DEFAULT_CAP_RADIUS / 2

    private fun getTilePosition(row: Int, col: Int): TilePosition {
        return if (!(row == 0 || col == 0 || row == rows - 1 || col == cols - 1)) {
            TilePosition.CENTER
        } else {
            val isLeft = (col == 0)
            val isRight = (col == cols - 1)
            val isTop = (row == 0)
            val isBottom = (row == rows - 1)
            return when {
                isLeft && isTop -> TilePosition.LEFT_TOP
                isLeft && isBottom -> TilePosition.LEFT_BOTTOM
                isLeft -> TilePosition.LEFT
                isRight && isTop -> TilePosition.RIGHT_TOP
                isRight && isBottom -> TilePosition.RIGHT_BOTTOM
                isRight -> TilePosition.RIGHT
                isTop -> TilePosition.TOP
                isBottom -> TilePosition.BOTTOM
                else -> TilePosition.CENTER
            }
        }
    }
}