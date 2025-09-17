package com.flyco.roundview

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout

open class RoundLinearLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : LinearLayout(context, attrs) {
    val delegate = RoundViewDelegate(this, context, attrs)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (delegate.isWidthHeightEqual) {
            val (success, newMeasureSpec) = MeasureUtil.onMeasureWidthHeightEqual(widthMeasureSpec, heightMeasureSpec)
            if (success) {
                super.onMeasure(newMeasureSpec, newMeasureSpec)
                return
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (delegate.isRadiusHalfHeight) delegate.cornerRadius = height / 2 else delegate.setBgSelector()
    }
}
