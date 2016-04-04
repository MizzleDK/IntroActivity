package com.miz.introactivity.demo;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.miz.introactivity.BaseIntroFragment;
import com.miz.introactivity.CustomAnimationPageTransformerDelegate;

/**
 * Created by miche on 04-04-2016.
 */
public class AnimationIntroFragment extends BaseIntroFragment implements CustomAnimationPageTransformerDelegate {

    private View mAirplane;

    @Override
    protected String getTitle() {
        return "Custom animation";
    }

    @Override
    protected int getTitleColor() {
        return Color.WHITE;
    }

    @Override
    protected String getDescription() {
        return "This is pretty cool!";
    }

    @Override
    protected int getDescriptionColor() {
        return Color.WHITE;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.airplane_layout;
    }

    @Override
    protected int getDrawableId() {
        return 0;
    }

    @Override
    protected int getResourceType() {
        return RESOURCE_TYPE_LAYOUT;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // We MUST call the super method in order for
        // the library to set up the intro page
        super.onViewCreated(view, savedInstanceState);

        mAirplane = view.findViewById(R.id.airplane);
    }

    @Override
    public void onPageSelected() {
        // Animate a jump
        ObjectAnimator animation = ObjectAnimator.ofFloat(mAirplane, "translationY",
                -20f, 20f, -20f, 20f, -20f, 20f, 0f);
        animation.setDuration(1000);
        animation.start();
    }

    @Override
    public void onPageScrolled(View page, float position) {
        int pageWidth = page.getWidth();
        float absPosition = Math.abs(position);
        float pageWidthTimesPosition = pageWidth * absPosition;

        mAirplane.setTranslationX(pageWidthTimesPosition / 4f);
        mAirplane.setTranslationY(-pageWidthTimesPosition / 2f);
        mAirplane.setRotation(-35 * absPosition);
        mAirplane.setAlpha(1f - absPosition);
    }

    @Override
    public void onPageInvisible(float position) {}
}
