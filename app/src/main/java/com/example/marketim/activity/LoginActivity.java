package com.example.marketim.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.marketim.R;
import com.example.marketim.utils.Const;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.button)
    FrameLayout button;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.reveal)
    View reveal;
    @BindView(R.id.etUserName)
    EditText userName;
    @BindView(R.id.etPassword)
    EditText password;
    @BindView(R.id.mSwitch)
    Switch mSwitch;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        preferences = getSharedPreferences(Const.PREFERENCES_NAME, MODE_PRIVATE);
        if (preferences.contains(Const.PREFS_NAME) &&
                preferences.contains(Const.PREFS_PASSWORD) &&
                preferences.contains(Const.PREFS_BOOLEAN)) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void load(View view) {
        String user = userName.getText().toString();
        String pass = password.getText().toString();
        animateButtonWidth();
        fadeOutTextAndShowProgressDialog();
        showProgressDialog();
        if ("kariyer".equals(user) && "2019ADev".equals(pass)) {
            nextAction();
        } else {
            cancelAnimations();
            showAlert();
        }
    }

    private void fadeOutTextAndShowProgressDialog() {
        text.animate().alpha(0f)
                .setDuration(250)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        showProgressDialog();
                    }
                })
                .start();
    }

    private void animateButtonWidth() {
        ValueAnimator anim = ValueAnimator.ofInt(button.getMeasuredWidth(), getFabWidth());

        anim.addUpdateListener(valueAnimator -> {
            int val = (Integer) valueAnimator.getAnimatedValue();
            ViewGroup.LayoutParams layoutParams = button.getLayoutParams();
            layoutParams.width = val;
            button.requestLayout();
        });
        anim.setDuration(250);
        anim.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void cancelAnimations() {
        ValueAnimator anim = ValueAnimator.ofInt(button.getMeasuredWidth(), getFabWidth());
        anim.addUpdateListener(valueAnimator -> {
            ViewGroup.LayoutParams layoutParams = button.getLayoutParams();
            layoutParams.width = (int) (getResources().getDisplayMetrics().density * 300);
            button.requestLayout();
            button.setElevation(4f);
            progressBar.setVisibility(INVISIBLE);
            text.setVisibility(VISIBLE);
            text.setAlpha(1f);
        });
        anim.setDuration(250);
        anim.start();
    }

    private void showProgressDialog() {
        progressBar.setAlpha(1f);
        progressBar
                .getIndeterminateDrawable()
                .setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_IN);
        progressBar.setVisibility(VISIBLE);
    }

    private void nextAction() {
        new Handler().postDelayed(() -> {
            revealButton();

            fadeOutProgressDialog();

            delayedStartNextActivity();
        }, 2000);
    }

    private void revealButton() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            button.setElevation(0f);
        }

        reveal.setVisibility(VISIBLE);

        int cx = reveal.getWidth();
        int cy = reveal.getHeight();

        int x = (int) (getFabWidth() / 2 + button.getX());
        int y = (int) (getFabWidth() / 2 + button.getY());

        float finalRadius = Math.max(cx, cy) * 1.2f;

        Animator animator = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            animator = ViewAnimationUtils
                    .createCircularReveal(reveal, x, y, getFabWidth(), finalRadius);
        }
        Objects.requireNonNull(animator).setDuration(350);
        animator.addListener(new AnimatorListenerAdapter() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onAnimationEnd(Animator animation) {
                reset(animation);
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            private void reset(Animator animation) {
                super.onAnimationEnd(animation);
                cancelAnimations();
            }
        });

        animator.start();
    }

    private void fadeOutProgressDialog() {
        progressBar.animate().alpha(0f).setDuration(200).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

            }
        }).start();
    }

    private void delayedStartNextActivity() {
        new Handler().postDelayed(() -> {
            Intent intent;
            if (mSwitch.isChecked()) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(Const.PREFS_NAME, userName.getText().toString());
                editor.putString(Const.PREFS_PASSWORD, password.getText().toString());
                editor.putBoolean(Const.PREFS_BOOLEAN, mSwitch.isChecked());
                editor.apply();
            } else {
                preferences.edit().clear().apply();
            }
            intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }, 100);
    }

    private int getFabWidth() {
        return (int) getResources().getDimension(R.dimen.fab_size);
    }

    private void showAlert() {
        SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE);
        pDialog.setTitleText(getString(R.string.alert));
        pDialog.setContentText(getString(R.string.passwordAlertText));
        pDialog.setConfirmText("Tamam");
        pDialog.show();
    }
}
