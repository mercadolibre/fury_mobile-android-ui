package com.mercadolibre.android.ui.font;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.widget.TextView;

import static android.support.v4.provider.FontsContractCompat.FontRequestCallback.FAIL_REASON_FONT_NOT_FOUND;

/**
 * This class is used as a wrapper for our custom font.
 * If you code create a View that supports typeface you should call one of this methods.
 */
public final class TypefaceHelper {

    @NonNull
    private static TypefaceSetter typefaceSetter;

    static {
        typefaceSetter = new CalligraphyTypefaceSetter();
    }

    private TypefaceHelper() {
        //Do nothing, this class should not be instantiated
    }

    /**
     * Attach a typeface setter to this helper class
     * @param typefaceSetter field
     */
    public static void attachTypefaceSetter(@NonNull final TypefaceSetter typefaceSetter) {
        TypefaceHelper.typefaceSetter = typefaceSetter;
    }

    /**
     * Sets the typeface to the given {@link T}
     * @param <T>   A generic for the textview
     * @param view  The view to which apply the font
     * @param font  The {@link Font} the text should have
     */
    public static <T extends TextView> void setTypeface(@NonNull final T view, @NonNull final Font font) {
        typefaceSetter.setTypeface(view, font);
    }

    /**
     * Sets the typeface to the given {@link Paint}
     *
     * @param context A context to obtain the font
     * @param paint   The paint to which apply the font
     * @param font    The {@link Font} the text should have
     */
    public static void setTypeface(@NonNull final Context context, @NonNull final Paint paint, @NonNull final Font font) {
        typefaceSetter.setTypeface(context, paint, font);
    }

    /**
     * Get a typeface associated to the font passed. The typeface will be sent through the
     * font callback passed as param
     *
     * @param context to use
     * @param font to retrieve its typeface
     * @param fontCallback to call when the typeface is retrieved
     *
     * @deprecated use TypefaceHelper{@link #getFontTypeface(Context, Font)} instead
     */
    @SuppressWarnings("PMD.LinguisticNaming")
    @Deprecated
    public static void getTypeface(@NonNull final Context context, @NonNull final Font font, @NonNull final ResourcesCompat.FontCallback fontCallback) {
        Typeface typeface = TypefaceHelper.getFontTypeface(context, font);
        if (typeface == null) {
            fontCallback.onFontRetrievalFailed(FAIL_REASON_FONT_NOT_FOUND);
        } else {
            fontCallback.onFontRetrieved(typeface);
        }
    }

    /**
     * Get a typeface associated to the font passed.

     * @param context to use
     * @param font to use
     * @return associated typeface
     */
    @Nullable
    public static Typeface getFontTypeface(@NonNull final Context context, @NonNull Font font) {
        return typefaceSetter.getTypeface(context, font);
    }

    /**
     * Setter for typeface
     */
    public interface TypefaceSetter {

        /**
         * Set a typeface to a view
         * @param view to set the typeface to
         * @param font to set
         * @param <T> extends TextView
         */
        <T extends TextView> void setTypeface(@NonNull final T view, @NonNull final Font font);

        /**
         * Set a typeface to the paint
         * @param context to use
         * @param paint to set the typeface
         * @param font to set
         */
        void setTypeface(@NonNull final Context context, @NonNull final Paint paint, @NonNull final Font font);

        /**
         * Return the typeface associated with the font
         * @param context to use
         * @param font to find the typeface
         * @return typeface associated
         */
        @Nullable
        Typeface getTypeface(@NonNull Context context, @NonNull final Font font);
    }
}
