package com.solt9029.editmasterandroid.util;

public final class SafeUnboxUtil {
    public static int safeUnbox(java.lang.Integer boxed) {
        return boxed == null ? 0 : boxed;
    }

    public static long safeUnbox(java.lang.Long boxed) {
        return boxed == null ? 0L : boxed;
    }

    public static short safeUnbox(java.lang.Short boxed) {
        return boxed == null ? 0 : (short) boxed;
    }

    public static byte safeUnbox(java.lang.Byte boxed) {
        return boxed == null ? 0 : (byte) boxed;
    }

    public static char safeUnbox(java.lang.Character boxed) {
        return boxed == null ? '\u0000' : boxed;
    }

    public static double safeUnbox(java.lang.Double boxed) {
        return boxed == null ? 0.0 : boxed;
    }

    public static float safeUnbox(java.lang.Float boxed) {
        return boxed == null ? 0f : boxed;
    }

    public static boolean safeUnbox(java.lang.Boolean boxed) {
        return boxed != null && boxed;
    }
}
