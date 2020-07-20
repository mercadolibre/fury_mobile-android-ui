package com.mercadolibre.android.ui.widgets;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mercadolibre.android.ui.R;
import com.mercadolibre.android.ui.font.Font;
import com.mercadolibre.android.ui.font.TypefaceHelper;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.mercadolibre.android.ui.widgets.TextField.type.CENTER;
import static com.mercadolibre.android.ui.widgets.TextField.type.LEFT;
import static java.lang.Integer.MAX_VALUE;

/**
 * MercadoLibre's TextField class.
 */
@SuppressWarnings("PMD")
public final class TextField extends LinearLayout {

    public static final double WIDTH_TEXTFIELD_FIX = 0.8;

    /* default */ String helperText;
    /* default */ boolean isShowingError;
    /* default */ boolean isShowingHelper;
    /* default */ int textAlign;
    /**
     * Views
     */
    private TextInputLayout container;
    private TextInputEditText input;
    private TextView label;

    /**
     * Attributes
     */
    private int maxLines;
    private int maxCharacters;
    private boolean charactersCountVisible;
    private String hint;
    private String labelText;
    private boolean enabled = true;
    /* default */ boolean hasHelper;

    /**
     * TextField constructor for code usage
     *
     * @param context the context
     */
    public TextField(final Context context) {
        super(context);
        init(context, null, 0);
    }

    /**
     * TextField constructor for XML usage
     *
     * @param context the context
     * @param attrs   the attributes
     */
    public TextField(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    /**
     * TextField constructor for XML usage
     *
     * @param context      the context
     * @param attrs        the attributes
     * @param defStyleAttr the default attributes
     */
    public TextField(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    /**
     * TextField constructor for XML usage
     *
     * @param context      the context
     * @param attrs        the attributes
     * @param defStyleAttr the default attributes
     * @param defStyleRes  the default style resource
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TextField(final Context context, final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr);
    }

    /**
     * Inits all component variables
     *
     * @param context      The android context
     * @param attrs        The attributes setted by XML
     * @param defStyleAttr The default style
     */
    private void init(@NonNull final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr) {
        inflate(context, R.layout.ui_layout_textfield, this);
        setOrientation(VERTICAL);

        container = (TextInputLayout) findViewById(R.id.ui_text_field_input_container);
        input = (TextInputEditText) findViewById(R.id.ui_text_field_input);
        label = (TextView) findViewById(R.id.ui_text_field_label);

        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TextField, defStyleAttr, 0);
        textAlign = a.getInt(R.styleable.TextField_ui_textFieldAlign, LEFT);
        hint = a.getString(R.styleable.TextField_ui_textFieldHint);
        labelText = a.getString(R.styleable.TextField_ui_textFieldLabel);
        maxLines = a.getInt(R.styleable.TextField_ui_textFieldMaxLines, MAX_VALUE);
        maxCharacters = a.getInt(R.styleable.TextField_ui_textFieldMaxCharacters, MAX_VALUE);
        charactersCountVisible = a.getBoolean(R.styleable.TextField_ui_textFieldCharactersCountVisible, false);
        enabled = a.getBoolean(R.styleable.TextField_ui_textFieldEnabled, true);

        helperText = a.getString(R.styleable.TextField_ui_textFieldHelperText);

        hasHelper = !TextUtils.isEmpty(helperText);

        container.setErrorTextAppearance(R.style.MeliTextField_ErrorText);
        container.setHelperTextTextAppearance(R.style.MeliTextField_HelperText);
        container.setHintTextAppearance(R.style.MeliTextField_Label);

        init();
        initDefaults(context, a);

        a.recycle();
    }

    private void initDefaults(@NonNull final Context context, @NonNull final TypedArray a) {
        final Drawable passwordDrawable = a.getDrawable(R.styleable.TextField_ui_textFieldPasswordToggleDrawable);
        if (passwordDrawable == null) {
            setPasswordVisibilityToggleEnabled(a.getBoolean(R.styleable.TextField_ui_textFieldPasswordToggleEnabled, false));
        } else {
            setPasswordVisibilityToggleDrawable(passwordDrawable);
            setPasswordVisibilityToggleTint(a.getColorStateList(R.styleable.TextField_ui_textFieldPasswordToggleTint));
            setPasswordVisibilityToggleEnabled(a.getBoolean(R.styleable.TextField_ui_textFieldPasswordToggleEnabled, true));
        }

        final Drawable left = a.getDrawable(R.styleable.TextField_ui_textFieldDrawableLeft);
        final Drawable top = a.getDrawable(R.styleable.TextField_ui_textFieldDrawableTop);
        final Drawable right = a.getDrawable(R.styleable.TextField_ui_textFieldDrawableRight);
        final Drawable bottom = a.getDrawable(R.styleable.TextField_ui_textFieldDrawableBottom);

        // Configs
        setCompoundDrawables(left, top, right, bottom);
        setEllipsize(a.getInt(R.styleable.TextField_ui_textFieldEllipsize, 0));
        setInputType(a.getInt(R.styleable.TextField_ui_textFieldInputType, InputType.TYPE_CLASS_TEXT));
        setTextFont(Font.LIGHT);
        setHintAnimationEnabled(a.getBoolean(R.styleable.TextField_ui_textFieldHintAnimationEnabled, labelText == null));

        // Colors
        setTextColors(context, a);

        // Text sizes
        setTextSize(a.getDimensionPixelSize(R.styleable.TextField_ui_textFieldTextSize, context.getResources().getDimensionPixelSize(R.dimen.ui_fontsize_medium)));
        setLabelSize(a.getDimensionPixelSize(R.styleable.TextField_ui_textFieldLabelSize, context.getResources().getDimensionPixelSize(R.dimen.ui_fontsize_xsmall)));

        getEditText().setGravity(textAlign);

    }

    private void setTextColors(@NonNull final Context context, @NonNull final TypedArray a) {
        ColorStateList textColor = a.getColorStateList(R.styleable.TextField_ui_textFieldTextColor);
        if (textColor == null) {
            textColor = ContextCompat.getColorStateList(context, R.color.ui_textfield_text_color);
        }
        setTextColor(textColor);

        ColorStateList hintColor = a.getColorStateList(R.styleable.TextField_ui_textFieldHintColor);
        if (hintColor == null) {
            hintColor = ContextCompat.getColorStateList(context, R.color.ui_components_grey_color);
        }
        setHintColor(hintColor);

        ColorStateList labelColor = a.getColorStateList(R.styleable.TextField_ui_textFieldLabelColor);
        if (labelColor == null) {
            labelColor = ContextCompat.getColorStateList(context, R.color.ui_components_grey_color);
        }
        setLabelColor(labelColor);
    }

    /**
     * With all the variables initialized, init the component by setting them
     */
    private void init() {
        //Set the different components
        setLabel(labelText);
        setHint(hint);
        setMaxLines(maxLines);
        setMaxCharacters(maxCharacters);
        setHelper(helperText);
        setEnabled(enabled);
        setCharactersCountVisible(charactersCountVisible);

        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(final CharSequence s, final int start, final int count, final int after) {
                // Do nothing
            }

            @Override
            public void onTextChanged(final CharSequence s, final int start, final int before, final int count) {
                // Do nothing
            }

            @Override
            public void afterTextChanged(final Editable s) {
                if (s.length() > 0 && isShowingError) {
                    clearError();
                    setHelper(helperText);
                }
            }
        });
    }

    /**
     * Getter for the label
     *
     * @return the label
     */
    @Nullable
    public String getLabel() {
        return TextUtils.isEmpty(label.getText()) ? null : label.getText().toString();
    }

    /**
     * Setter for the label
     *
     * @param labelText the text for the label
     */
    public void setLabel(final String labelText) {
        this.labelText = labelText;
        final boolean textIsEmpty = TextUtils.isEmpty(labelText);
        if (textIsEmpty) {
            label.setVisibility(View.GONE);
        } else {
            label.setText(labelText);
            label.setVisibility(View.VISIBLE);
            setHintAnimationEnabled(false);
            // Set label to input in order to be recognized by Appium
            input.setContentDescription(labelText);
        }

        final LinearLayout.LayoutParams params =
                (LinearLayout.LayoutParams) label.getLayoutParams();
        params.gravity = textAlign;
        label.setLayoutParams(params);

    }

    /**
     * Getter for the hint animation
     *
     * @return the hint animation
     */
    public boolean isHintAnimationEnabled() {
        return container.isHintEnabled() && container.isHintAnimationEnabled();
    }

    /**
     * Setter for the hint animation
     *
     * @param isEnabled is the hint animation enabled or not
     */
    public void setHintAnimationEnabled(final boolean isEnabled) {
        container.setHintEnabled(isEnabled);
        container.setHintAnimationEnabled(isEnabled);
        // We have to re set the hint to the corresponding view
        setHint(hint);
    }

    /**
     * Setter for the hint
     *
     * @param hint the hint to set
     */
    public void setHint(final String hint) {
        this.hint = hint;
        if (container.isHintEnabled()) {
            container.setHint(hint);
        } else {
            input.setHint(hint);
        }
    }

    /**
     * Getter for the hint
     *
     * @return the hint
     */
    @Nullable
    public String getHint() {
        if (container.isHintEnabled()) {
            return TextUtils.isEmpty(container.getHint()) ? null : container.getHint().toString();
        } else {
            return TextUtils.isEmpty(input.getHint()) ? null : input.getHint().toString();
        }
    }

    /**
     * Setters for the hint
     *
     * @param hint the hint to set
     */
    public void setHint(@StringRes final int hint) {
        setHint(getResources().getString(hint));
    }


    /* default */ void clearError() {
        changeErrorVisibility(false);
    }

    /**
     * Setter for the helper
     *
     * @param helpText the text
     */
    public void setHelper(@Nullable final String helpText) {
        final boolean isEmpty = TextUtils.isEmpty(helpText);
        helperText = helpText;
        container.setHelperText(helperText);
        container.setHelperTextEnabled(true);
        setHelperGravity();
        hasHelper = !isEmpty;
        if (!enabled || isEmpty) {
            hideHelper();
            return;
        }
        changeErrorVisibility(false);
        isShowingHelper = true;
    }

    private void setHelperGravity() {
        final TextView helperView = container.findViewById(R.id.textinput_helper_text);
        if (textAlign == CENTER || textAlign == android.view.Gravity.CENTER_HORIZONTAL) {
            final FrameLayout helperViewParent = (FrameLayout) helperView.getParent();
            helperViewParent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            helperView.setGravity(textAlign);
        } else if (container.isCounterEnabled()) {
            setWidthToTextInputLayout(helperView, WIDTH_TEXTFIELD_FIX);
        }
    }

    private void changeErrorVisibility(final boolean isVisible) {
        isShowingError = isVisible;
        container.setErrorEnabled(isVisible);
    }

    /**
     * Getter for the helper
     *
     * @return the text
     */
    @Nullable
    public String getHelper() {
        return helperText;
    }

    /**
     * Setter for the helper
     *
     * @param helpText the text
     */
    public void setHelper(@StringRes final int helpText) {
        setHelper(getResources().getString(helpText));
    }

    /**
     * Setter for the error
     *
     * @param error the error
     */
    public void setError(final String error) {
        if (!enabled) {
            return;
        }
        boolean isEmpty = TextUtils.isEmpty(error);
        changeErrorVisibility(!isEmpty);
        if (isEmpty) {
            setHelper(helperText);
        } else {
            isShowingError = true;
            if (isShowingHelper) {
                hideHelper();
            }

            final TextView errorView = (TextView) container
                    .findViewById(android.support.design.R.id.textinput_error);
            TypefaceHelper.setTypeface(errorView, Font.SEMI_BOLD);

            if (textAlign == CENTER || textAlign == android.view.Gravity.CENTER_HORIZONTAL) {
                final FrameLayout.LayoutParams errorViewParams =
                        new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
                errorViewParams.gravity = textAlign;
                errorView.setGravity(textAlign);
                errorView.setLayoutParams(errorViewParams);
            } else if(container.isCounterEnabled()){
                setWidthToTextInputLayout(errorView, WIDTH_TEXTFIELD_FIX);
            }
        }

        container.setError(error);
        container.setErrorEnabled(!isEmpty);
    }

    /**
     * This is a fix to android helper and error, when counter is showing
     */
    private void setWidthToTextInputLayout(final TextView textView, final double width) {
        container.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                final int widthLayout = (int) (container.getWidth() * width);
                final FrameLayout.LayoutParams viewParams =
                        new FrameLayout.LayoutParams(widthLayout, FrameLayout.LayoutParams.WRAP_CONTENT);
                textView.setLayoutParams(viewParams);
                container.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    /**
     * Getter for the error
     *
     * @return the error
     */
    @Nullable
    public String getError() {
        return TextUtils.isEmpty(container.getError()) ? null : container.getError().toString();
    }

    /**
     * Setter for the error
     *
     * @param error the error
     */
    public void setError(@StringRes final int error) {
        setError(getResources().getString(error));
    }

    /**
     * Getter for the enabled state
     *
     * @return the enabled state
     */
    @Override
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Setter for the enabled state
     *
     * @param isEnabled the enabled state
     */
    public void setEnabled(final boolean isEnabled) {
        this.enabled = isEnabled;
        applyStatus();
    }

    /**
     * Setter for the text
     *
     * @param text the text
     */
    public void setText(@Nullable final String text) {
        input.setText(text);
    }

    /**
     * Getter for the text
     *
     * @return the text
     */
    public String getText() {
        return input.getText().toString();
    }

    /**
     * Setter for the text
     *
     * @param text the text
     */
    public void setText(@StringRes final int text) {
        setText(getResources().getString(text));
    }

    /**
     * Sets the maximum number of lines.
     *
     * @param maxLines the maximum number of lines.
     */
    public void setMaxLines(final int maxLines) {
        if (maxLines == 0) {
            this.maxLines = MAX_VALUE;
        } else {
            this.maxLines = maxLines;
        }
        input.setMaxLines(this.maxLines);
    }

    /**
     * Sets the maximum number of characters.
     *
     * @param maxChars the maximum number of characters.
     */
    public void setMaxCharacters(final int maxChars) {
        maxCharacters = maxChars;
        if (maxCharacters == MAX_VALUE) {
            setCharactersCountVisible(false);
        } else {
            final InputFilter[] filterArray = new InputFilter[1];
            filterArray[0] = new InputFilter.LengthFilter(maxCharacters);
            input.setFilters(filterArray);
            setCharactersCountVisible(charactersCountVisible);
            container.setCounterMaxLength(maxChars);
        }
    }

    /**
     * Sets the visibility of the character counter
     *
     * @param isVisible the new visibility
     */
    public void setCharactersCountVisible(final boolean isVisible) {
        this.charactersCountVisible = isVisible;
        container.setCounterEnabled(charactersCountVisible);
    }

    /**
     * Set the ellipsize for the input
     *
     * @param ellipsize the new ellipsize
     */
    public void setEllipsize(final TextUtils.TruncateAt ellipsize) {
        input.setEllipsize(ellipsize);
    }

    /**
     * Set the input type.
     *
     * @param type the new input type
     */
    public void setInputType(final int type) {
        input.setInputType(type);
    }

    /**
     * Adds a TextWatcher to the list of those whose methods are called
     * whenever this TextView's text changes.
     *
     * @param watcher the text watcher to add
     */
    public void addTextChangedListener(final TextWatcher watcher) {
        input.addTextChangedListener(watcher);
    }

    /**
     * Set an {@link OnClickListener} to the EditText.
     *
     * @param listener the on click listener to set
     */
    @Override
    public void setOnClickListener(final OnClickListener listener) {
        input.setOnClickListener(listener);
    }

    /**
     * Sets the {@link OnFocusChangeListener}
     *
     * @param listener the on focus change listener to set
     */
    public void setOnFocusChangeListener(final OnFocusChangeListener listener) {
        input.setOnFocusChangeListener(listener);
    }

    /**
     * Get the EditText component.
     *
     * @return the edit text
     */
    public EditText getEditText() {
        return input;
    }

    /**
     * Set drawables to the sides of the edit text
     *
     * @param left   left drawable or 0 to show nothing
     * @param top    top drawable or 0 to show nothing
     * @param right  right drawable or 0 to show nothing
     * @param bottom bottom drawable or 0 to show nothing
     */
    public void setCompoundDrawables(@DrawableRes final int left,
                                     @DrawableRes final int top,
                                     @DrawableRes final int right,
                                     @DrawableRes final int bottom) {
        input.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
    }

    /**
     * Set drawables to the sides of the edit text
     *
     * @param left   left drawable or null to show nothing
     * @param top    top drawable or null to show nothing
     * @param right  right drawable or null to show nothing
     * @param bottom bottom drawable or null to show nothing
     */
    public void setCompoundDrawables(@Nullable final Drawable left,
                                     @Nullable final Drawable top,
                                     @Nullable final Drawable right,
                                     @Nullable final Drawable bottom) {
        input.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
    }

    /**
     * Set a drawable for the password visibility toggle
     *
     * @param toggleDrawable The drawable to set
     */
    public void setPasswordVisibilityToggleDrawable(final Drawable toggleDrawable) {
        container.setPasswordVisibilityToggleDrawable(toggleDrawable);
    }

    /**
     * Set a drawable for the password visibility toggle
     *
     * @param toggleDrawableRes The drawable to set
     */
    public void setPasswordVisibilityToggleDrawable(@DrawableRes final int toggleDrawableRes) {
        container.setPasswordVisibilityToggleDrawable(toggleDrawableRes);
    }

    /**
     * Set a tint for the toggle drawable
     *
     * @param toggleTint the color list to use with the drawable
     */
    public void setPasswordVisibilityToggleTint(final ColorStateList toggleTint) {
        container.setPasswordVisibilityToggleTintList(toggleTint);
    }

    /**
     * Set a tint for the toggle drawable
     *
     * @param toggleTint the color list to use with the drawable
     */
    public void setPasswordVisibilityToggleTint(@ColorRes final int toggleTint) {
        setPasswordVisibilityToggleTint(ContextCompat.getColorStateList(getContext(), toggleTint));
    }

    /**
     * Set if the password toggle is enabled or not
     *
     * @param enabled is enabled or not
     */
    public void setPasswordVisibilityToggleEnabled(final boolean enabled) {
        container.setPasswordVisibilityToggleEnabled(enabled);
    }

    /**
     * Set the color for the text, this can be either a color parsed with {@link Color#parseColor(String)}
     * or a resolved resource {@link ContextCompat#getColor(Context, int)}
     *
     * @param color the color to set
     */
    public void setTextColor(int color) {
        input.setTextColor(color);
    }

    /**
     * Set the color list for the text, this should be a resolved resource {@link ContextCompat#getColorStateList(Context, int)}
     *
     * @param color the colors to set
     */
    public void setTextColor(ColorStateList color) {
        input.setTextColor(color);
    }

    /**
     * Set the color for the hint, this can be either a color parsed with {@link Color#parseColor(String)}
     * or a resolved resource {@link ContextCompat#getColor(Context, int)}
     *
     * @param color the color to set
     */
    public void setHintColor(int color) {
        input.setHintTextColor(color);
    }

    /**
     * Set the color list for the hint, this should be a resolved resource {@link ContextCompat#getColorStateList(Context, int)}
     *
     * @param color the colors to set
     */
    public void setHintColor(ColorStateList color) {
        input.setHintTextColor(color);
    }

    /**
     * Set the color for the label, this can be either a color parsed with {@link Color#parseColor(String)}
     * or a resolved resource {@link ContextCompat#getColor(Context, int)}
     *
     * @param color the color to set
     */
    public void setLabelColor(int color) {
        label.setTextColor(color);
    }

    /**
     * Set the color list for the label, this should be a resolved resource {@link ContextCompat#getColorStateList(Context, int)}
     *
     * @param color the colors to set
     */
    public void setLabelColor(ColorStateList color) {
        label.setTextColor(color);
    }

    /**
     * Set the size for the text, this should be a number in PX
     *
     * @param size the size to set
     */
    public void setTextSize(float size) {
        input.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
    }

    /**
     * Set the size for the label, this should be a number in PX
     *
     * @param size the size to set
     */
    public void setLabelSize(float size) {
        label.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
    }

    /**
     * Set the font for the text, this can be any of {@link Font}
     *
     * @param font the font to set
     */
    public void setTextFont(Font font) {
        TypefaceHelper.setTypeface(input, font);
    }

    /**
     * Set the font for the label, this can be any of {@link Font}
     *
     * @param font the font to set
     */
    public void setLabelFont(Font font) {
        TypefaceHelper.setTypeface(label, font);
    }

    /**
     * Set the gravity for the text inside the input, this can be any of {@link Gravity}
     *
     * @param gravity the gravity to set
     */
    public void setTextGravity(int gravity) {
        input.setGravity(gravity);
    }

    /**
     * Set ellipsize.
     */
    @SuppressWarnings("checkstyle:magicnumber")
    private void setEllipsize(final int ellipsize) {
        switch (ellipsize) {
            case 1:
                setEllipsize(TextUtils.TruncateAt.START);
                break;
            case 2:
                setEllipsize(TextUtils.TruncateAt.MIDDLE);
                break;
            case 3:
                setEllipsize(TextUtils.TruncateAt.END);
                break;
        }
    }

    private void applyStatus() {
        if (enabled) {
            input.setFocusableInTouchMode(true);
            container.setEnabled(true);
            setHelper(helperText);
        } else {
            input.setFocusableInTouchMode(false);
            container.setEnabled(false);
            clearError();
            hideHelper();
        }
    }

    private void hideHelper() {
        container.setHelperTextEnabled(false);
        isShowingHelper = false;
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        final Parcelable superState = super.onSaveInstanceState();
        return new SavedState(superState, getText(), getLabel(), getError(), maxLines, maxCharacters,
                charactersCountVisible, getHint(), enabled);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onRestoreInstanceState(final Parcelable state) {

        final SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());

        labelText = savedState.labelText;
        maxLines = savedState.linesNumber;
        maxCharacters = savedState.charactersNumber;
        charactersCountVisible = savedState.charactersVisible;
        hint = savedState.hint;
        enabled = savedState.enabled;
        init();
        setText(savedState.inputText);
        if (!TextUtils.isEmpty(savedState.errorText) && isEnabled()) {
            setError(savedState.errorText);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void dispatchSaveInstanceState(@NonNull final SparseArray<Parcelable> container) {
        // As we save our own instance state, ensure our children don't save and restore their state as well.
        super.dispatchFreezeSelfOnly(container);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void dispatchRestoreInstanceState(@NonNull final SparseArray<Parcelable> container) {
        /** See comment in {@link #dispatchSaveInstanceState(android.util.SparseArray)}  */
        super.dispatchThawSelfOnly(container);
    }

    @Override
    public String toString() {
        return "TextField{"
                + "label=" + label
                + ", maxLines=" + maxLines
                + ", maxCharacters=" + maxCharacters
                + ", charactersCountVisible=" + charactersCountVisible
                + ", hint='" + hint + '\''
                + ", labelText='" + labelText + '\''
                + ", enabled=" + enabled
                + '}';
    }

    /**
     * Possible alignment
     */
    @SuppressWarnings({"PMD.RedundantFieldInitializer", "PMD.CommentDefaultAccessModifier"})
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({LEFT, CENTER})
    public @interface type {
        int LEFT = Gravity.START;
        int CENTER = Gravity.CENTER;
    }

    /**
     * The state of the custom view.
     */
    protected static class SavedState extends BaseSavedState {

        public static final Parcelable.Creator<SavedState> CREATOR = new Creator<SavedState>() {

            @Override
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };

        /* default */ final boolean enabled;
        /* default */ final String inputText;
        /* default */ final String hint;
        /* default */ final String labelText;
        /* default */ final String errorText;
        /* default */ final int linesNumber;
        /* default */ final int charactersNumber;
        /* default */ final boolean charactersVisible;

        /**
         * Creates a new state.
         *
         * @param superState the super state.
         * @param inputText  the title.
         */
        private SavedState(final Parcelable superState, final String inputText, final String labelText,
                           final String errorText, final
                           int linesNumber, final int charactersNumber, final boolean charactersVisible, final String hint,
                           final boolean enabled) {
            super(superState);
            this.inputText = inputText;
            this.labelText = labelText;
            this.errorText = errorText;
            this.linesNumber = linesNumber;
            this.charactersNumber = charactersNumber;
            this.charactersVisible = charactersVisible;
            this.hint = hint;
            this.enabled = enabled;
        }

        /**
         * Called when creating the state from a parcel.
         *
         * @param source the parcel.
         */
        public SavedState(final Parcel source) {
            super(source);
            this.inputText = source.readString();
            this.labelText = source.readString();
            this.errorText = source.readString();
            this.linesNumber = source.readInt();
            this.charactersNumber = source.readInt();
            this.charactersVisible = source.readInt() == 1;
            this.hint = source.readString();
            this.enabled = source.readInt() == 1;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void writeToParcel(@NonNull final Parcel destination, final int flags) {
            super.writeToParcel(destination, flags);
            destination.writeString(inputText);
            destination.writeString(labelText);
            destination.writeString(errorText);
            destination.writeInt(linesNumber);
            destination.writeInt(charactersNumber);
            destination.writeInt(charactersVisible ? 1 : 0);
            destination.writeString(hint);
            destination.writeInt(enabled ? 1 : 0);
        }

        @Override
        @SuppressWarnings("checkstyle:multiplestringliterals")
        public String toString() {
            return "SavedState{"
                    + "enabled=" + enabled
                    + ", inputText='" + inputText + '\''
                    + ", hint='" + hint + '\''
                    + ", labelText='" + labelText + '\''
                    + ", errorText='" + errorText + '\''
                    + ", linesNumber=" + linesNumber
                    + ", charactersNumber=" + charactersNumber
                    + ", charactersVisible=" + charactersVisible
                    + '}';
        }
    }
}
