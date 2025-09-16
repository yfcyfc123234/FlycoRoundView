package com.flyco.roundview

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.graphics.drawable.StateListDrawable
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.withStyledAttributes

class RoundViewDelegate(
    private val view: View,
    private val context: Context,
    attrs: AttributeSet?,
) {
    private val gdBackground = GradientDrawable()
    private val gdBackgroundPress = GradientDrawable()

    private var backgroundColor = 0
    private var backgroundPressColor = 0
    private var cornerRadius = 0
    private var cornerRadiusTL = 0
    private var cornerRadiusTR = 0
    private var cornerRadiusBL = 0
    private var cornerRadiusBR = 0
    private var strokeWidth = 0
    private var strokeColor = 0
    private var strokePressColor = 0
    private var textPressColor = 0
    private var isRadiusHalfHeight = false
    private var isWidthHeightEqual = false
    private var isRippleEnable = false
    private val radiusArr = FloatArray(8)

    init {
        obtainAttributes(context, attrs)
    }

    private fun obtainAttributes(context: Context, attrs: AttributeSet?) {
        context.withStyledAttributes(attrs, R.styleable.RoundTextView) {
            backgroundColor = getColor(R.styleable.RoundTextView_rv_backgroundColor, Color.TRANSPARENT)
            backgroundPressColor = getColor(R.styleable.RoundTextView_rv_backgroundPressColor, Int.MAX_VALUE)
            cornerRadius = getDimensionPixelSize(R.styleable.RoundTextView_rv_cornerRadius, 0)
            strokeWidth = getDimensionPixelSize(R.styleable.RoundTextView_rv_strokeWidth, 0)
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

    fun setBackgroundColor(backgroundColor: Int) {
        this.backgroundColor = backgroundColor
        setBgSelector()
    }

    fun setBackgroundPressColor(backgroundPressColor: Int) {
        this.backgroundPressColor = backgroundPressColor
        setBgSelector()
    }

    fun setCornerRadius(cornerRadius: Int) {
        this.cornerRadius = dp2px(cornerRadius.toFloat())
        setBgSelector()
    }

    fun setStrokeWidth(strokeWidth: Int) {
        this.strokeWidth = dp2px(strokeWidth.toFloat())
        setBgSelector()
    }

    fun setStrokeColor(strokeColor: Int) {
        this.strokeColor = strokeColor
        setBgSelector()
    }

    fun setStrokePressColor(strokePressColor: Int) {
        this.strokePressColor = strokePressColor
        setBgSelector()
    }

    fun setTextPressColor(textPressColor: Int) {
        this.textPressColor = textPressColor
        setBgSelector()
    }

    fun setIsRadiusHalfHeight(isRadiusHalfHeight: Boolean) {
        this.isRadiusHalfHeight = isRadiusHalfHeight
        setBgSelector()
    }

    fun setIsWidthHeightEqual(isWidthHeightEqual: Boolean) {
        this.isWidthHeightEqual = isWidthHeightEqual
        setBgSelector()
    }

    fun setCornerRadius_TL(cornerRadius_TL: Int) {
        this.cornerRadiusTL = cornerRadius_TL
        setBgSelector()
    }

    fun setCornerRadius_TR(cornerRadius_TR: Int) {
        this.cornerRadiusTR = cornerRadius_TR
        setBgSelector()
    }

    fun setCornerRadius_BL(cornerRadius_BL: Int) {
        this.cornerRadiusBL = cornerRadius_BL
        setBgSelector()
    }

    fun setCornerRadius_BR(cornerRadius_BR: Int) {
        this.cornerRadiusBR = cornerRadius_BR
        setBgSelector()
    }

    fun getBackgroundColor(): Int {
        return backgroundColor
    }

    fun getBackgroundPressColor(): Int {
        return backgroundPressColor
    }

    fun getCornerRadius(): Int {
        return cornerRadius
    }

    fun getStrokeWidth(): Int {
        return strokeWidth
    }

    fun getStrokeColor(): Int {
        return strokeColor
    }

    fun getStrokePressColor(): Int {
        return strokePressColor
    }

    fun getTextPressColor(): Int {
        return textPressColor
    }

    fun isRadiusHalfHeight(): Boolean {
        return isRadiusHalfHeight
    }

    fun isWidthHeightEqual(): Boolean {
        return isWidthHeightEqual
    }

    fun getCornerRadius_TL(): Int {
        return cornerRadiusTL
    }

    fun getCornerRadius_TR(): Int {
        return cornerRadiusTR
    }

    fun getCornerRadius_BL(): Int {
        return cornerRadiusBL
    }

    fun getCornerRadius_BR(): Int {
        return cornerRadiusBR
    }

    private fun dp2px(dp: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }

    private fun sp2px(sp: Float): Int {
        val scale = this.context.resources.displayMetrics.scaledDensity
        return (sp * scale + 0.5f).toInt()
    }

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
        val bg = StateListDrawable()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && isRippleEnable) {
            setDrawable(gdBackground, backgroundColor, strokeColor)
            val rippleDrawable = RippleDrawable(
                getPressedColorSelector(backgroundColor, backgroundPressColor),
                gdBackground,
                null,
            )
            view.background = rippleDrawable
        } else {
            setDrawable(gdBackground, backgroundColor, strokeColor)
            bg.addState(intArrayOf(-android.R.attr.state_pressed), gdBackground)
            if (backgroundPressColor != Int.MAX_VALUE || strokePressColor != Int.MAX_VALUE) {
                setDrawable(
                    gdBackgroundPress, if (backgroundPressColor == Int.MAX_VALUE) backgroundColor else backgroundPressColor,
                    if (strokePressColor == Int.MAX_VALUE) strokeColor else strokePressColor,
                )
                bg.addState(intArrayOf(android.R.attr.state_pressed), gdBackgroundPress)
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                view.background = bg
            } else {
                view.setBackgroundDrawable(bg)
            }
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

    @RequiresApi(Build.VERSION_CODES.HONEYCOMB)
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
