package com.mercadolibre.android.ui.example.ui.widgets;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;
import com.mercadolibre.android.ui.example.BaseActivity;
import com.mercadolibre.android.ui.example.R;
import com.mercadolibre.android.ui.widgets.MeliProgressBar;

/**
 * Created by mmarengo on 2019-11-04.
 */
public class ProgressBarActivity extends BaseActivity {

    private MeliProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View parentView = LayoutInflater.from(this).inflate(R.layout.activity_progress_bar, null);
        setContentView(parentView);
        progressBar = findViewById(R.id.ui_meli_progress_bar);
    }

    public void startProgress(final View v) {
        progressBar.start(progressAnimationListener);
    }

    public void finishProgress(final View v) {
        progressBar.finish(progressAnimationListener);
    }

    public void restartProgress(final View v) {
        progressBar.restart();
    }

    private final AnimatorListenerAdapter progressAnimationListener = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(final Animator animation) {
            super.onAnimationEnd(animation);
            Toast.makeText(ProgressBarActivity.this, "Progress ended", Toast.LENGTH_SHORT).show();
        }
    };
}
