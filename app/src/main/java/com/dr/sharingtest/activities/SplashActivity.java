package com.dr.sharingtest.activities;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import com.dr.sharingtest.R;

import com.dr.sharingtest.ui.font.MaterialDesignIconsTextView;
import com.dr.sharingtest.ui.kbv.KenBurnsView;

public class SplashActivity extends FragmentActivity {

    private KenBurnsView mKenBurns;
    private ImageView mLogo;

    Button btnFullScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //hide status bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);



        mKenBurns = (KenBurnsView) findViewById(R.id.ken_burns_images);
        mLogo = (ImageView) findViewById(R.id.logo);
        btnFullScreen = (Button) findViewById(R.id.btnFullScreen);
        btnFullScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SplashActivity.this, LeftMenusMediaActivity.class);
                startActivity(intent);
                SplashActivity.this.finish();
            }
        });
//        mKenBurns.setImageDrawable(getResources(R.drawable.backgound_splash));
//        mKenBurns.setImageResource(R.drawable.background_media);
        animation1();
    }

    private void animation1() {
        ObjectAnimator scaleXAnimation = ObjectAnimator.ofFloat(mLogo, "scaleX", 5.0F, 1.0F);
        scaleXAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleXAnimation.setDuration(1200);
        ObjectAnimator scaleYAnimation = ObjectAnimator.ofFloat(mLogo, "scaleY", 5.0F, 1.0F);
        scaleYAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleYAnimation.setDuration(1200);
        ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(mLogo, "alpha", 0.0F, 1.0F);
        alphaAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        alphaAnimation.setDuration(1200);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(scaleXAnimation).with(scaleYAnimation).with(alphaAnimation);
        animatorSet.setStartDelay(500);
        animatorSet.start();
    }
}
