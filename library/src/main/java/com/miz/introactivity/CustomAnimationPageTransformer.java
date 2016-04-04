package com.miz.introactivity;

import android.support.v4.view.ViewPager;
import android.view.View;

public class CustomAnimationPageTransformer implements ViewPager.PageTransformer {

    @Override
    public void transformPage(View page, float position) {
        if (page.getTag() instanceof CustomAnimationPageTransformerDelegate) {
            CustomAnimationPageTransformerDelegate delegate =
                    (CustomAnimationPageTransformerDelegate) page.getTag();

            if (position == 0.0f) {
                // Page is selected
                delegate.onPageSelected();
            } else if (position <= -1.0f || position >= 1.0f) {
                // Page not visible to the user
                delegate.onPageInvisible(position);
            } else {
                // Page is being scrolled
                delegate.onPageScrolled(page, position);
            }
        }
    }

}
