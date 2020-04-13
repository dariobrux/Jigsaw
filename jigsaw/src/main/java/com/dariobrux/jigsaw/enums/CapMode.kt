package com.dariobrux.jigsaw.enums

/**
 * Created by Dario Bruzzese
 * on 4/5/2020
 */

enum class CapMode {
    NONE {
        override fun inverse(): CapMode = NONE
    },
    EMPTY {
        override fun inverse(): CapMode = FULL
    },
    FULL {
        override fun inverse(): CapMode = EMPTY
    };

    abstract fun inverse(): CapMode
}