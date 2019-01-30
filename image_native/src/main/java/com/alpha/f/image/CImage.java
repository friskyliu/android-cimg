package com.alpha.f.image;

import java.io.IOException;

public final class CImage {
    static {
        System.loadLibrary("image-c");
    }

    @SuppressWarnings("JniMissingFunction")
    native public static boolean cropCImg(String inputPath, String outputPath,
                                          int left, int top, int width, int height,
                                          float angle, float resizeScale,
                                          int format, int quality,
                                          int exifDegrees, int exifTranslation) throws IOException, OutOfMemoryError;
}
