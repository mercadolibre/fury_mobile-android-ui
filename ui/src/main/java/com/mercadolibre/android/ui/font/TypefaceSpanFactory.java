package com.mercadolibre.android.ui.font;

import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.style.TypefaceSpan;

/**
 * This class wrap the TypefaceSpan creation to split according to the Android API version
 */
@SuppressWarnings("unused")
public class TypefaceSpanFactory {
    @NonNull
    public TypefaceSpan create(@NonNull Typeface typeface) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            return new TypefaceSpan(typeface);
        }
        return new TypefaceSpanCompat(typeface);
    }
}
