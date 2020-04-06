package com.example.jigsaw

import android.util.Log
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sqrt

/**
 * Created by Dario Bruzzese
 * on 4/5/2020
 */
class Matrix(private val items: Int, private val rows: Int, private val cols: Int) {

    enum class Position {
        CENTER,
        LEFT,
        LEFT_TOP,
        LEFT_BOTTOM,
        TOP,
        RIGHT,
        RIGHT_TOP,
        RIGHT_BOTTOM,
        BOTTOM
    }

    val matrix = arrayListOf<ArrayList<Tile>>()
    var result = arrayListOf<Tile>()

    init {

//        val rows: Int
//        val cols : Int
//        if (isPerfectSquare(items)) {
//            rows = getRows()
//            cols = getCols()
//        } else {
//            val x = items / 2 // 3
//            val y = items / x // 2
//            rows = min(x, y)
//            cols = max(x, y)
//        }

        Log.d("Matrix", "$items items is -> ${rows}x$cols")

        var currentRightCap: CapMode = getRandomCapMode()
        val currentBottomCapList = mutableListOf<CapMode>()

        repeat(rows) { i ->
            repeat(cols) { j ->
                val position = getPosition(i, j)
                Log.d("item", "$i, $j -> $position ")
                val tile = Tile().apply {
                    when (position) {
                        Position.LEFT_TOP -> {
                            this.capLeft = CapMode.NONE
                            this.capTop = CapMode.NONE
                            this.capRight = currentRightCap
                            this.capBottom = getRandomCapMode()
                        }
                        Position.TOP -> {
                            this.capLeft = currentRightCap.inverse()
                            this.capTop = CapMode.NONE
                            this.capRight = getRandomCapMode()
                            this.capBottom = getRandomCapMode()
                        }
                        Position.RIGHT_TOP -> {
                            this.capLeft = currentRightCap.inverse()
                            this.capTop = CapMode.NONE
                            this.capRight = CapMode.NONE
                            this.capBottom = getRandomCapMode()
                        }
                        Position.LEFT -> {
                            this.capLeft = CapMode.NONE
                            this.capTop = currentBottomCapList[j].inverse()
                            this.capRight = getRandomCapMode()
                            this.capBottom = getRandomCapMode()
                        }
                        Position.CENTER -> {
                            this.capLeft = currentRightCap.inverse()
                            this.capTop = currentBottomCapList[j].inverse()
                            this.capRight = getRandomCapMode()
                            this.capBottom = getRandomCapMode()
                        }
                        Position.RIGHT -> {
                            this.capLeft = currentRightCap.inverse()
                            this.capTop = currentBottomCapList[j].inverse()
                            this.capRight = CapMode.NONE
                            this.capBottom = getRandomCapMode()
                        }
                        Position.LEFT_BOTTOM -> {
                            this.capLeft = CapMode.NONE
                            this.capTop = currentBottomCapList[j].inverse()
                            this.capRight = getRandomCapMode()
                            this.capBottom = CapMode.NONE
                        }
                        Position.BOTTOM -> {
                            this.capLeft = currentRightCap.inverse()
                            this.capTop = currentBottomCapList[j].inverse()
                            this.capRight = getRandomCapMode()
                            this.capBottom = CapMode.NONE
                        }
                        Position.RIGHT_BOTTOM -> {
                            this.capLeft = currentRightCap.inverse()
                            this.capTop = currentBottomCapList[j].inverse()
                            this.capRight = CapMode.NONE
                            this.capBottom = CapMode.NONE
                        }
                    }
                }
                currentRightCap = tile.capRight
                currentBottomCapList.add(tile.capBottom)
                result.add(tile)
                Log.d("item", "$i, $j -> $currentBottomCapList ")
            }
            if (i != 0) {
                currentBottomCapList.subList(0, currentBottomCapList.size / 2).clear()
            }
        }
    }

    private fun getRandomCapMode(): CapMode {
        return listOf(CapMode.FULL, CapMode.EMPTY).shuffled().first()
    }


    private fun getPosition(row: Int, col: Int): Position {
        return if (!(row == 0 || col == 0 || row == rows - 1 || col == cols - 1)) {
            Position.CENTER
        } else {
            val isLeft = (col == 0)
            val isRight = (col == cols - 1)
            val isTop = (row == 0)
            val isBottom = (row == rows - 1)
            return when {
                isLeft && isTop -> Position.LEFT_TOP
                isLeft && isBottom -> Position.LEFT_BOTTOM
                isLeft -> Position.LEFT
                isRight && isTop -> Position.RIGHT_TOP
                isRight && isBottom -> Position.RIGHT_BOTTOM
                isRight -> Position.RIGHT
                isTop -> Position.TOP
                isBottom -> Position.BOTTOM
                else -> Position.CENTER
            }
        }
    }
}