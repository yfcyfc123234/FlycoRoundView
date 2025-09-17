package com.flyco.roundview

import android.view.View.MeasureSpec

internal object MeasureUtil {
    fun onMeasureWidthHeightEqual(widthMeasureSpec: Int, heightMeasureSpec: Int): Pair<Boolean, Int> {
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)

        val targetSize = when {
            widthMode == MeasureSpec.EXACTLY && heightMode == MeasureSpec.EXACTLY -> minOf(widthSize, heightSize)
            widthMode == MeasureSpec.EXACTLY -> widthSize
            heightMode == MeasureSpec.EXACTLY -> heightSize
            else -> return false to minOf(widthSize, heightSize)
        }

        return true to MeasureSpec.makeMeasureSpec(targetSize, MeasureSpec.EXACTLY)
    }
}