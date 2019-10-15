package com.mercadolibre.android.ui.font;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.text.style.TypefaceSpan;

/**
 * This class replace the <a href="https://github.com/chrisjenx/Calligraphy/blob/master/calligraphy/src/main/java/uk/co/chrisjenx/calligraphy/CalligraphyTypefaceSpan.java">CalligraphyTypefaceSpan</a> from <a href="https://github.com/chrisjenx/Calligraphy">Calligraphy</a>
 * The motivation is to no depend on a library implementation
 */
public class TypefaceSpanCompat extends TypefaceSpan {

    private final static float SKEW_X = -0.25f;

    @NonNull
    private final Typeface typeface;

    /**
     * Default constructor
     * @param typeface typeface
     */
    /* default */ TypefaceSpanCompat(@NonNull final Typeface typeface) {
        super("");
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

    private void apply(@NonNull final Paint paint) {
        final Typeface oldTypeface = paint.getTypeface();
        final int oldStyle = getOldStyle(oldTypeface);
        final int fakeStyle = oldStyle & ~typeface.getStyle();

        if ((fakeStyle & Typeface.BOLD) != 0) {
            paint.setFakeBoldText(true);
        }

        if ((fakeStyle & Typeface.ITALIC) != 0) {
            paint.setTextSkewX(SKEW_X);
        }

        paint.setTypeface(typeface);
    }

    private int getOldStyle(@Nullable Typeface oldTypeface) {
        if (oldTypeface == null) {
            return 0;
        }
        return oldTypeface.getStyle();
    }
}