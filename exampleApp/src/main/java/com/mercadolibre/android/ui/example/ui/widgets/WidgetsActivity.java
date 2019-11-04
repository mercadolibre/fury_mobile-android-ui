package com.mercadolibre.android.ui.example.ui.widgets;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mercadolibre.android.ui.example.BaseActivity;
import com.mercadolibre.android.ui.example.R;
import com.mercadolibre.android.ui.example.ui.widgets.contextual_menu.ContextualMenuActivity;
import com.mercadolibre.android.ui.example.ui.widgets.dialog.DialogActivity;
import com.mercadolibre.android.ui.example.ui.widgets.spinner.SpinnerActivity;
import com.mercadolibre.android.ui.example.ui.widgets.textfield.TextFieldActivity;

public class WidgetsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widgets2);
    }

    public void openButtonsActivity(View view) {
        startActivity(new Intent(this, ButtonsActivity.class));
    }

    public void openMLButtonsActivity(View view) {
        startActivity(new Intent(this, MLButtonsActivity.class));
    }

    public void openSnackbarsActivity(View view) {
        startActivity(new Intent(this, SnackbarActivity.class));
    }

    public void openDialogActivity(View view) {
        startActivity(new Intent(this, DialogActivity.class));
    }

    public void openSpinnerActivity(View view) {
        startActivity(new Intent(this, SpinnerActivity.class));
    }

    public void openOtherWidgetsActivity(View view) {
        startActivity(new Intent(this, OtherWidgetsActivity.class));
    }

    public void openContextualMenuActivity(View view) {
        startActivity(new Intent(this, ContextualMenuActivity.class));
    }

    public void openTextfieldWithLabelActivity(View view) {
        startActivity(new Intent(this, TextFieldActivity.class));
    }

    public void openProgressBarActivity(View view) {
        startActivity(new Intent(this, ProgressBarActivity.class));
    }
}
