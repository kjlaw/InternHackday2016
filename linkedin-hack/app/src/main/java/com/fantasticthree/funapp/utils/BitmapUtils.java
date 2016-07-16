package com.fantasticthree.funapp.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class BitmapUtils {

    /**
     * Loads a bitmap given a filePath.
     *
     * @param filePath  where the bitmap is located in the file system
     * @param minWidth  the minimum pixel width of the bitmap
     * @param minHeight the minimum pixel height of the bitmap
     */
    public static Bitmap loadBitmapFromFile(String filePath, int minWidth, int minHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize which is
        // how much we need to shrink the image and still satisfy minWidth and minHeight
        options.inSampleSize = calculateInSampleSize(options.outWidth, options.outHeight, minWidth, minHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * Calculates a factor (always a power of 2) that we need to shrink the image by and still satisfy minWidth and minHeight
     *
     * @param originalWidth  the original pixel width for the bitmap in the file system
     * @param originalHeight the original pixel height for the bitmap in the file system
     * @param minWidth       the minimum pixel width of the bitmap
     * @param minHeight      the minimum pixel height of the bitmap
     */
    private static int calculateInSampleSize(int originalWidth, int originalHeight, int minWidth, int minHeight) {
        int inSampleSize = 1;

        if (originalHeight > minHeight || originalWidth > minWidth) {

            final int halfHeight = originalHeight / 2;
            final int halfWidth = originalWidth / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= minHeight
                    && (halfWidth / inSampleSize) >= minWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap scaleDown(Bitmap realImage, float maxImageSize,
                                   boolean filter) {
        float ratio = Math.min(
                (float) maxImageSize / realImage.getWidth(),
                (float) maxImageSize / realImage.getHeight());
        int width = Math.round((float) ratio * realImage.getWidth());
        int height = Math.round((float) ratio * realImage.getHeight());

        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width,
                height, filter);
        return newBitmap;
    }

    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality)
    {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }
}
