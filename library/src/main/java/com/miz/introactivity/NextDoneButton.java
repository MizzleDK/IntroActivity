package com.miz.introactivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.AttributeSet;
import android.widget.ImageButton;

/**
 * Simple ImageButton implementation to show a circle button
 * with a "Next" or "Done" icon.
 */
public class NextDoneButton extends ImageButton {

    public static final int STYLE_NEXT = 0, STYLE_DONE = 1;

    private int mColor = 0, mButtonStyle;
    private Drawable mNextDrawable, mDoneDrawable;

    public NextDoneButton(Context context) {
        super(context);
        initialize(context);
        showNextButton(true);
    }

    public NextDoneButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
        showNextButton(true);
    }

    @SuppressLint("NewApi")
    private void initialize(Context context) {
        // Set the background resource
        setBackgroundResource(R.drawable.next_done_button_selector);

        mNextDrawable = ContextCompat.getDrawable(context, getDoneDrawable());
        mDoneDrawable = ContextCompat.getDrawable(context, getNextDrawable());

        if (mColor == 0) {
            mColor = ContextCompat.getColor(context, R.color.next_done_icon_color);
        }

        if (Utils.hasLollipop()) {
            mNextDrawable.setTint(mColor);
            mDoneDrawable.setTint(mColor);
        } else {
            mNextDrawable = DrawableCompat.wrap(mNextDrawable);
            mDoneDrawable = DrawableCompat.wrap(mDoneDrawable);

            DrawableCompat.setTint(mNextDrawable, mColor);
            DrawableCompat.setTint(mDoneDrawable, mColor);
        }

        setImageDrawable(mButtonStyle == STYLE_NEXT ? mNextDrawable : mDoneDrawable);
    }

    private int getDoneDrawable() {
        return Utils.hasLollipop() ?
                R.drawable.done_to_next : R.drawable.next;
    }

    private int getNextDrawable() {
        return Utils.hasLollipop() ?
                R.drawable.next_to_done: R.drawable.done;
    }

    /**
     * Set the color of the button icon.
     * @param color Color for the button icon.
     */
    public void setColor(int color) {
        mColor = color;
        tintDrawables();
    }

    private void tintDrawables() {
        if (Utils.hasLollipop()) {
            mNextDrawable.setTint(mColor);
            mDoneDrawable.setTint(mColor);
        } else {
            mNextDrawable = DrawableCompat.wrap(mNextDrawable);
            mDoneDrawable = DrawableCompat.wrap(mDoneDrawable);

            DrawableCompat.setTint(mNextDrawable, mColor);
            DrawableCompat.setTint(mDoneDrawable, mColor);
        }
    }

    /**
     * Toggle the current button style, changing the icon
     * and animating the change if the device supports it.
     */
    public void toggle() {
        // Toggle the button style
        mButtonStyle = (mButtonStyle == STYLE_NEXT) ? STYLE_DONE : STYLE_NEXT;

        // Set image drawable depending on the button style
        setImageDrawable(mButtonStyle == STYLE_NEXT ? mNextDrawable : mDoneDrawable);

        // Attempt to animate the button if we're on Lollipop or above
        if (Utils.hasLollipop()) {
            Drawable drawable = getDrawable();
            if (drawable instanceof Animatable) {
                Animatable animatable = (Animatable) drawable;
                if (animatable.isRunning()) {
                    animatable.stop();
                }
                animatable.start();
            }
        }
    }

    /**
     * Get the current button style.
     * @return STYLE_NEXT if the Next button is currently shown, STYLE_DONE
     * if the Done button is currently shown.
     */
    public int getButtonStyle() {
        return mButtonStyle;
    }

    /**
     * Show the Next button.
     * @param animate True if the icon change should be animated, false otherwise.
     */
    public void showNextButton(boolean animate) {
        if (animate) {
            mButtonStyle = STYLE_DONE;
            toggle();
        } else {
            mButtonStyle = STYLE_NEXT;
            setImageDrawable(mNextDrawable);
        }
    }

    /**
     * Show the Done button.
     * @param animate True if the icon change should be animated, false otherwise.
     */
    public void showDoneButton(boolean animate) {
        if (animate) {
            mButtonStyle = STYLE_NEXT;
            toggle();
        } else {
            mButtonStyle = STYLE_DONE;
            setImageDrawable(mDoneDrawable);
        }
    }
}
