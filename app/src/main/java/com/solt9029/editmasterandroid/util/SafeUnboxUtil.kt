package com.solt9029.editmasterandroid.util

object SafeUnboxUtil {
    @JvmStatic fun safeUnbox(boxed: Int?): Int = boxed ?: 0
    @JvmStatic fun safeUnbox(boxed: Long?): Long = boxed ?: 0L
    @JvmStatic fun safeUnbox(boxed: Short?): Short = boxed ?: 0
    @JvmStatic fun safeUnbox(boxed: Byte?): Byte = boxed ?: 0
    @JvmStatic fun safeUnbox(boxed: Char?): Char = boxed ?: '\u0000'
    @JvmStatic fun safeUnbox(boxed: Double?): Double = boxed ?: 0.0
    @JvmStatic fun safeUnbox(boxed: Float?): Float = boxed ?: 0f
    @JvmStatic fun safeUnbox(boxed: Boolean?): Boolean = boxed != null && boxed
}
