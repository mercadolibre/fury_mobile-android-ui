package com.mercadolibre.android.ui.font;

import android.graphics.Typeface;
import android.os.Build;
import android.text.style.TypefaceSpan;

public class TypefaceSpanFactory {
    public TypefaceSpan create(Typeface typeface) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            return new TypefaceSpan(typeface);
        }
        return new TypefaceSpanCompat(typeface);
    }
}
