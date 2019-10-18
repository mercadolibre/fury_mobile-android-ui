package com.mercadolibre.android.ui.font;

import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.content.res.ResourcesCompat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static android.support.v4.provider.FontsContractCompat.FontRequestCallback.FAIL_REASON_FONT_NOT_FOUND;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.LOLLIPOP)
public class TypefaceHelperTest {

    private TypefaceHelper.TypefaceSetter typefaceSetter = mock(TypefaceHelper.TypefaceSetter.class);
    private Typeface expectedTypeface = mock(Typeface.class);
    private ResourcesCompat.FontCallback fontCallback = mock(ResourcesCompat.FontCallback.class);
    private Typeface typeface;

    @Test
    public void getFontTypeface() {
        givenATypeface(Font.LIGHT);
        givenACustomTypefaceSetter();

        whenGettingTypeface(Font.LIGHT);

        thenTypefaceIsReturned();
    }

    @Test
    public void getTypefaceAsync() {
        givenATypeface(Font.BLACK);
        givenACustomTypefaceSetter();

        whenGettingTypefaceAsync(Font.BLACK);

        thenTypefaceIsReturnedAsync();
    }

    @Test
    public void getTypefaceAsyncNotFound() {
        givenACustomTypefaceSetter();

        whenGettingTypefaceAsync(Font.BLACK);

        thenCallbackFailWithFontNotFoundReason();

    }

    private void givenATypeface(Font font) {
        when(typefaceSetter.getTypeface(RuntimeEnvironment.application, font)).thenReturn(expectedTypeface);
    }


    private void givenACustomTypefaceSetter() {
        TypefaceHelper.attachTypefaceSetter(typefaceSetter);
    }

    private void whenGettingTypefaceAsync(Font font) {
        TypefaceHelper.getTypeface(RuntimeEnvironment.application, font, fontCallback);
    }

    private void whenGettingTypeface(Font font) {
        typeface = TypefaceHelper.getFontTypeface(RuntimeEnvironment.application, font);
    }

    private void thenTypefaceIsReturnedAsync() {
        verify(fontCallback, only()).onFontRetrieved(expectedTypeface);
    }

    private void thenTypefaceIsReturned() {
        assertEquals(expectedTypeface, typeface);
    }

    private void thenCallbackFailWithFontNotFoundReason() {
        verify(fontCallback, only()).onFontRetrievalFailed(FAIL_REASON_FONT_NOT_FOUND);
    }
}
