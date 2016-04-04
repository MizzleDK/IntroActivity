package com.miz.introactivity;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.TypedValue;

/**
 * Simple utility class.
 */
public class Utils {

    private Utils() {}

    /**
     * Converts dp units to pixel units.
     * @param context Context
     * @param dp Units in dp to convert to pixels.
     * @return dp unit converted to pixels.
     */
    public static int convertDpToPixel(Context context, int dp) {
        Resources resources = context.getResources();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                resources.getDisplayMetrics());
    }

    /**
     * Determine if the device has Lollipop (Android 5.0) or greater.
     * @return True if Android 5.0+, false otherwise.
     */
    public static boolean hasLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

}
