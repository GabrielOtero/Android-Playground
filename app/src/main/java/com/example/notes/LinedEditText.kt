package com.example.notes

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatEditText

class LinedEditText(context: Context, attrs: AttributeSet) : AppCompatEditText(context, attrs) {

    val rect: Rect = Rect()
    val paint: Paint = Paint()

    init {
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 2F
        paint.color = 0xFFFFD966.toInt()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val numberOfLines = (parent as View).height / lineHeight
        val r = rect
        val p = paint
        var baseLine = getLineBounds(0, r)
        for (i in 0..numberOfLines) {
            canvas?.drawLine(
                r.left.toFloat(), (baseLine + 1).toFloat(), r.right.toFloat(),
                (baseLine + 1).toFloat(), p
            )
            baseLine += lineHeight
        }
    }
}
