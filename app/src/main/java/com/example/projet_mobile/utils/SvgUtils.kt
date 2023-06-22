package com.example.projet_mobile.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.PictureDrawable
import android.net.Uri
import com.caverock.androidsvg.SVG
import com.caverock.androidsvg.SVGParseException

object SvgUtils {

    private fun PictureDrawable.toDrawable(): Drawable {
        return this
    }

    fun getSvgDrawable(context: Context, svgResourceId: Int): Drawable? {
        return try {
            val svgUri = Uri.parse("android.resource://${context.packageName}/$svgResourceId")
            val svg = SVG.getFromInputStream(context.contentResolver.openInputStream(svgUri))
            val picture = svg.renderToPicture()
            PictureDrawable(picture).toDrawable()
        } catch (e: SVGParseException) {
            e.printStackTrace()
            null
        }
    }
}

