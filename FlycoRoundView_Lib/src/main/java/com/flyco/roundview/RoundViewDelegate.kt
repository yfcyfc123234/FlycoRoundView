package com.flyco.roundview

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.graphics.drawable.StateListDrawable
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.core.content.withStyledAttributes

class RoundViewDelegate(val view: View, val context: Context, attrs: AttributeSet?) {
    private val gdBackground = GradientDrawable()
    private val gdBackgroundPress = GradientDrawable()
    private val radiusArr = FloatArray(8)
    private var obtainAttributesDone = false

    var backgroundColor = 0
        set(value) {
            field = value
            setBgSelector()
        }
    var backgroundPressColor = 0
        set(value) {
            field = value
            setBgSelector()
        }
    var cornerRadius
        get() = cornerRadiusPx
        set(value) {
            cornerRadiusPx = dp2px(value.toFloat())
        }
    var cornerRadiusPx = 0
        set(value) {
            field = value
            setBgSelector()
        }
    var cornerRadiusTL = 0
        set(value) {
            field = value
            setBgSelector()
        }
    var cornerRadiusTR = 0
        set(value) {
            field = value
            setBgSelector()
        }
    var cornerRadiusBL = 0
        set(value) {
            field = value
            setBgSelector()
        }
    var cornerRadiusBR = 0
        set(value) {
            field = value
            setBgSelector()
        }
    var strokeWidth
        get() = strokeWidthPx
        set(value) {
            strokeWidthPx = dp2px(value.toFloat())
        }
    var strokeWidthPx = 0
        set(value) {
            field = value
            setBgSelector()
        }
    var strokeColor = 0
        set(value) {
            field = value
            setBgSelector()
        }
    var strokePressColor = 0
        set(value) {
            field = value
            setBgSelector()
        }
    var textPressColor = 0
        set(value) {
            field = value
            setBgSelector()
        }
    var isRadiusHalfHeight = false
        set(value) {
            field = value
            setBgSelector()
        }
    var isWidthHeightEqual = false
        set(value) {
            field = value
            setBgSelector()
        }
    var isRippleEnable = false
        set(value) {
            field = value
            setBgSelector()
        }

    init {
        obtainAttributes(context, attrs)
        obtainAttributesDone = true
    }

    private fun obtainAttributes(context: Context, attrs: AttributeSet?) {
        context.withStyledAttributes(attrs, R.styleable.RoundTextView) {
            backgroundColor = getColor(R.styleable.RoundTextView_rv_backgroundColor, Color.TRANSPARENT)
            backgroundPressColor = getColor(R.styleable.RoundTextView_rv_backgroundPressColor, Int.MAX_VALUE)
            cornerRadiusPx = getDimensionPixelSize(R.styleable.RoundTextView_rv_cornerRadius, 0)
            strokeWidthPx = getDimensionPixelSize(R.styleable.RoundTextView_rv_strokeWidth, 0)
            strokeColor = getColor(R.styleable.RoundTextView_rv_strokeColor, Color.TRANSPARENT)
            strokePressColor = getColor(R.styleable.RoundTextView_rv_strokePressColor, Int.MAX_VALUE)
            textPressColor = getColor(R.styleable.RoundTextView_rv_textPressColor, Int.MAX_VALUE)
            isRadiusHalfHeight = getBoolean(R.styleable.RoundTextView_rv_isRadiusHalfHeight, false)
            isWidthHeightEqual = getBoolean(R.styleable.RoundTextView_rv_isWidthHeightEqual, false)
            cornerRadiusTL = getDimensionPixelSize(R.styleable.RoundTextView_rv_cornerRadius_TL, 0)
            cornerRadiusTR = getDimensionPixelSize(R.styleable.RoundTextView_rv_cornerRadius_TR, 0)
            cornerRadiusBL = getDimensionPixelSize(R.styleable.RoundTextView_rv_cornerRadius_BL, 0)
            cornerRadiusBR = getDimensionPixelSize(R.styleable.RoundTextView_rv_cornerRadius_BR, 0)
            isRippleEnable = getBoolean(R.styleable.RoundTextView_rv_isRippleEnable, true)
        }
    }

    private fun dp2px(dp: Float) = (dp * context.resources.displayMetrics.density + 0.5f).toInt()

    private fun setDrawable(gd: GradientDrawable, color: Int, strokeColor: Int) {
        gd.setColor(color)

        if (cornerRadiusTL > 0 || cornerRadiusTR > 0 || cornerRadiusBR > 0 || cornerRadiusBL > 0) {
            // The corners are ordered top-left, top-right, bottom-right, bottom-left
            radiusArr[0] = cornerRadiusTL.toFloat()
            radiusArr[1] = cornerRadiusTL.toFloat()
            radiusArr[2] = cornerRadiusTR.toFloat()
            radiusArr[3] = cornerRadiusTR.toFloat()
            radiusArr[4] = cornerRadiusBR.toFloat()
            radiusArr[5] = cornerRadiusBR.toFloat()
            radiusArr[6] = cornerRadiusBL.toFloat()
            radiusArr[7] = cornerRadiusBL.toFloat()
            gd.setCornerRadii(radiusArr)
        } else {
            gd.setCornerRadius(cornerRadius.toFloat())
        }

        gd.setStroke(strokeWidth, strokeColor)
    }

    fun setBgSelector() {
        if (!obtainAttributesDone) return

        val bg = StateListDrawable()

        setDrawable(gdBackground, backgroundColor, strokeColor)
        bg.addState(intArrayOf(-android.R.attr.state_pressed), gdBackground)
        if (backgroundPressColor != Int.MAX_VALUE || strokePressColor != Int.MAX_VALUE) {
            setDrawable(
                gdBackgroundPress, if (backgroundPressColor == Int.MAX_VALUE) backgroundColor else backgroundPressColor,
                if (strokePressColor == Int.MAX_VALUE) strokeColor else strokePressColor,
            )
            bg.addState(intArrayOf(android.R.attr.state_pressed), gdBackgroundPress)
        }

        view.background = if (isRippleEnable) {
            RippleDrawable(
                getPressedColorSelector(backgroundColor, backgroundPressColor),
                gdBackground,
                null,
            )
        } else {
            bg
        }

        if (view is TextView) {
            if (textPressColor != Int.MAX_VALUE) {
                val textColors = view.textColors
                val colorStateList = ColorStateList(
                    arrayOf(intArrayOf(-android.R.attr.state_pressed), intArrayOf(android.R.attr.state_pressed)),
                    intArrayOf(textColors.defaultColor, textPressColor),
                )
                view.setTextColor(colorStateList)
            }
        }
    }

    private fun getPressedColorSelector(normalColor: Int, pressedColor: Int): ColorStateList {
        return ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_pressed),
                intArrayOf(android.R.attr.state_focused),
                intArrayOf(android.R.attr.state_activated),
                intArrayOf(),
            ),
            intArrayOf(
                pressedColor,
                pressedColor,
                pressedColor,
                normalColor,
            )
        )
    }
}