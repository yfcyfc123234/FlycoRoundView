package com.flyco.roundview

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import kotlin.math.max

/** 用于需要圆角矩形框背景的LinearLayout的情况,减少直接使用LinearLayout时引入的shape资源文件  */
class RoundLinearLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : LinearLayout(context, attrs) {
    /** use delegate to set attr  */
    val delegate = RoundViewDelegate(this, context, attrs)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (delegate.isWidthHeightEqual && width > 0 && height > 0) {
            val max = max(width, height)
            val measureSpec = MeasureSpec.makeMeasureSpec(max, MeasureSpec.EXACTLY)
            super.onMeasure(measureSpec, measureSpec)
            return
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (delegate.isRadiusHalfHeight) delegate.cornerRadius = height / 2 else delegate.setBgSelector()
    }
}
