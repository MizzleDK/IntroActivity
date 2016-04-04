package com.miz.introactivity.demo;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.miz.introactivity.IntroActivity;
import com.miz.introactivity.IntroFragment;

public class DemoActivity extends IntroActivity {

    @Override
    protected void initialize() {
        String description = "This is a very long and detailed description about absolutely nothing in particular.";

        // Custom intro screen
        addIntroScreen(
                new AnimationIntroFragment(),
                ContextCompat.getColor(this, R.color.material_indigo)
        );

        // Intro screen with title and description
        addIntroScreen(
                IntroFragment.newInstance("Title 0", description),
                ContextCompat.getColor(this, R.color.material_blue)
        );

        // Intro screen with title, description and custom colors for both
        addIntroScreen(
                IntroFragment.newInstance("Title 1", Color.WHITE, description, Color.WHITE),
                ContextCompat.getColor(this, R.color.material_indigo)
        );

        // Intro screen with title, description, custom colors and a custom layout
        addIntroScreen(
                IntroFragment.newInstance("Title 2", Color.WHITE, description, Color.WHITE,
                        R.layout.viewstub_layout_1, IntroFragment.RESOURCE_TYPE_LAYOUT),
                ContextCompat.getColor(this, R.color.material_deep_purple)
        );

        // Intro screen with title, description and a custom layout
        addIntroScreen(
                IntroFragment.newInstance("Title 3", description, R.layout.viewstub_layout_1,
                        IntroFragment.RESOURCE_TYPE_LAYOUT),
                ContextCompat.getColor(this, R.color.material_orange)
        );

        // Intro screen with title, description and a drawable
        addIntroScreen(
                IntroFragment.newInstance("Title 4", description, R.drawable.ic_directions_bike_24dp,
                        IntroFragment.RESOURCE_TYPE_DRAWABLE),
                ContextCompat.getColor(this, R.color.material_green)
        );

        // Intro screen with title, description, custom colors and a drawable
        addIntroScreen(
                IntroFragment.newInstance("Title 5", Color.WHITE, description, Color.WHITE,
                        R.drawable.ic_directions_bike_24dp, IntroFragment.RESOURCE_TYPE_DRAWABLE),
                ContextCompat.getColor(this, R.color.material_blue_grey)
        );

        setShowSkipButton(true);
        setShowNextButton(true);
        setSkipButtonTextColor(Color.WHITE);
        setNextButtonBackgroundColor(Color.WHITE);
        setNextButtonIconColor(Color.WHITE);
        setProgressCircleColor(Color.WHITE);
    }

    @Override
    protected void onSkipPressed() {
        Toast.makeText(this, "onSkipPressed()", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onNextPressed(int pagePosition) {
        Toast.makeText(this, "onNextPressed() " + pagePosition, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDonePressed() {
        Toast.makeText(this, "onDonePressed()", Toast.LENGTH_SHORT).show();
    }

}
