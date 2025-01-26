package com.example.woltapplication.utils

import android.graphics.Bitmap

fun decodeBlurHash(blurHash: String): Bitmap? {
    return BlurHashDecoder.decode(blurHash, 96, 96)
}