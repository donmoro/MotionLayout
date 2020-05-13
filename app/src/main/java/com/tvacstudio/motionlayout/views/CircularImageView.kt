package com.tvacstudio.motionlayout.views

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import kotlin.math.roundToInt

class CircularImageView : AppCompatImageView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )

    override fun onDraw(canvas: Canvas) {
        drawCircularImage(canvas)
        drawStroke(canvas)
    }

    private fun drawStroke(canvas: Canvas) {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.style = Paint.Style.STROKE
        canvas.drawCircle(width / 2f, width / 2f, 0F, paint)
    }

    private fun drawCircularImage(canvas: Canvas) {
        val b: Bitmap = (drawable as BitmapDrawable).bitmap
        val bitmap = b.copy(Bitmap.Config.ARGB_8888, true)
        val ratio: Float = bitmap.width.toFloat() / bitmap.height.toFloat()
        val height: Int = (width / ratio).roundToInt()
        val scaledBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false)
        val shader = BitmapShader(scaledBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        val rect = RectF()
        rect.set(0f, 0f, width.toFloat(), height.toFloat())
        val imagePaint = Paint().apply {
            isAntiAlias = true
            setShader(shader)
        }
        canvas.drawRoundRect(rect, width.toFloat(), height.toFloat(), imagePaint)
    }

}