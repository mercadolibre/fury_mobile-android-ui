package com.mercadolibre.android.ui.font;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.text.TextPaint;
import android.text.style.TypefaceSpan;

/**
 * This class replace the CalligraphyTypefaceSpan from Calligraphy
 * The motivation is to no depend on a library implementation
 */
public class TypefaceSpanCompat extends TypefaceSpan {

    private final Typeface typeface;

    /**
     * Default constructor
     * @param typeface typeface
     */
    /* default */ TypefaceSpanCompat(final Typeface typeface) {
        super("");
        if (typeface == null) {
            throw new IllegalArgumentException("typeface is null");
        }
        this.typeface = typeface;
    }

    @Override
    public void updateDrawState(@NonNull final TextPaint drawState) {
        apply(drawState);
    }

    @Override
    public void updateMeasureState(@NonNull final TextPaint paint) {
        apply(paint);
    }

    private void apply(final Paint paint) {
        final Typeface oldTypeface = paint.getTypeface();
        final int oldStyle = getOldStyle(oldTypeface);
        final int fakeStyle = oldStyle & ~typeface.getStyle();

        if ((fakeStyle & Typeface.BOLD) != 0) {
            paint.setFakeBoldText(true);
        }

        if ((fakeStyle & Typeface.ITALIC) != 0) {
            paint.setTextSkewX(-0.25f);
        }

        paint.setTypeface(typeface);
    }

    private int getOldStyle(Typeface oldTypeface) {
        if (oldTypeface == null) {
            return 0;
        }
        return oldTypeface.getStyle();
    }
}