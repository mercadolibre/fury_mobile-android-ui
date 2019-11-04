package com.mercadolibre.android.ui.widgets;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import com.mercadolibre.android.ui.R;

/**
 *
 */
public final class MeliProgressBar extends FrameLayout {

    private static final String PROGRESS_PROPERTY = "progress";

    private static final int MAX_LOADING_TIME_DEFAULT = 10_000;
    private static final int FINISH_ANIM_TIME_DEFAULT = 500;
    private static final int INITIAL_PROGRESS = 0;

    private ProgressBar progressBar;
    private int maxLoadingTime;
    private int finishAnimTime;
    private ObjectAnimator animator;

    public MeliProgressBar(final Context context) {
        super(context);
        init(context, null, 0);
    }

    public MeliProgressBar(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public MeliProgressBar(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MeliProgressBar(final Context context, final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr);
    }

    private void init(@NonNull final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr) {
        LayoutInflater.from(context).inflate(R.layout.ui_layout_progress_bar, this);
        progressBar = findViewById(R.id.ui_progressBar);

        final TypedArray a =
            context.obtainStyledAttributes(attrs, R.styleable.MeliProgressBar, defStyleAttr, 0);

        maxLoadingTime = a.getInt(R.styleable.MeliProgressBar_maxLoadingTime, MAX_LOADING_TIME_DEFAULT);
        finishAnimTime = a.getInt(R.styleable.MeliProgressBar_finishAnimTime, FINISH_ANIM_TIME_DEFAULT);

        progressBar.setProgress(INITIAL_PROGRESS);
        progressBar.setMax(maxLoadingTime);
    }

    /**
     *
     * @param animatorListener
     */
    public void start(@Nullable final Animator.AnimatorListener animatorListener) {
        if (animator != null) {
            animator.cancel();
        }
        progressBar.setProgress(INITIAL_PROGRESS);
        animator = ObjectAnimator.ofInt(progressBar, PROGRESS_PROPERTY, 0, maxLoadingTime);
        animator.setInterpolator(new LinearInterpolator());
        if (animatorListener != null) {
            animator.addListener(animatorListener);
        }
        animator.setDuration(maxLoadingTime);
        animator.start();
    }

    /**
     *
     * @param animatorListener
     */
    public void finish(@Nullable final Animator.AnimatorListener animatorListener) {
        final int progress = progressBar.getProgress();
        if (animator != null) {
            animator.cancel();
        }
        if (progress > 0) {
            animator = ObjectAnimator.ofInt(progressBar, PROGRESS_PROPERTY, progress, maxLoadingTime);
            animator.setInterpolator(new AccelerateDecelerateInterpolator());
            animator.setDuration(finishAnimTime);
            if (animatorListener != null) {
                animator.addListener(animatorListener);
            }
            animator.start();
        }
    }

    /**
     *
     */
    public void restart() {
        if (animator != null) {
            animator.cancel();
        }
        progressBar.setProgress(INITIAL_PROGRESS);
    }

    /**
     *
     * @return
     */
    public int getProgress() {
        return progressBar == null ? INITIAL_PROGRESS : progressBar.getProgress();
    }

    @Override
    @NonNull
    public String toString() {
        return "MeliProgressBar{" +
            "progressBar=" + progressBar +
            ", maxLoadingTime=" + maxLoadingTime +
            ", finishAnimTime=" + finishAnimTime +
            ", animator=" + animator +
            '}';
    }
}
