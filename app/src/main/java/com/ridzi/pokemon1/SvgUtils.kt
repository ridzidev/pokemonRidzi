package com.ridzi.pokemon1

import android.graphics.Bitmap
import android.graphics.Canvas
import com.caverock.androidsvg.SVG

object SvgUtils {
    fun svgToBitmap(svg: SVG, width: Int, height: Int): Bitmap {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        svg.renderToCanvas(canvas)
        return bitmap
    }
}
