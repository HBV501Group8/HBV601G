package com.example.hagspar.adapters_utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;


// Solution implemented using Jonik's answer to https://stackoverflow.com/questions/18021148/display-a-loading-overlay-on-android-screen
public class LoadingUtil {
    public static void animateView(final View view, final int toVisibility, float toAlpha, int duration) {
        boolean show = toVisibility == View.VISIBLE;
        if (show) {
            view.setAlpha(0);
        }
        view.setVisibility(View.VISIBLE);
        view.animate()
                .setDuration(duration)
                .alpha(show ? toAlpha : 0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        view.setVisibility(toVisibility);
                    }
                });
    }
}
