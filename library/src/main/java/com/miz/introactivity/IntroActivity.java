package com.miz.introactivity;

import android.animation.ArgbEvaluator;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Base activity that apps should extend
 * to make use of the intro screen.
 */
public abstract class IntroActivity extends AppCompatActivity {

    private final ArgbEvaluator mArgbEvaluator = new ArgbEvaluator();

    private ViewPager mViewPager;
    private Button mSkipButton;
    private NextDoneButton mNextButton;
    private IntroScreenPagerAdapter mPagerAdapter;
    private LinearLayout mProgressLayout;
    private boolean mShowSkipButton, mShowNextButton;
    private int mProgressCircleColor;

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme(R.style.IntroActivity_Theme);

        // Set the content view
        setContentView(R.layout.intro);

        // Set up the progress LinearLayout
        mProgressLayout = (LinearLayout) findViewById(R.id.progress_layout);

        // Set up the back button
        mSkipButton = (Button) findViewById(R.id.skip_button);
        mSkipButton.setOnClickListener(mOnSkipClickListener);

        // Set up the next button
        mNextButton = (NextDoneButton) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(mOnNextClickListener);

        // Set up the ViewPager
        mViewPager = (ViewPager) findViewById(R.id.view_pager);

        // Set up the PagerAdapter
        mPagerAdapter = new IntroScreenPagerAdapter(getSupportFragmentManager());

        // Add the OnPageChangeListener
        mViewPager.addOnPageChangeListener(mOnPageChangeListener);

        // Set a custom animation PageTransformer
        mViewPager.setPageTransformer(false, new CustomAnimationPageTransformer());

        // Call the initialize method to add intro screens
        // and set up the selected styles
        initialize();

        // Set up the progress layout with circles
        setupProgressLayout();

        // Set the ViewPager adapter
        mViewPager.setAdapter(mPagerAdapter);

        // Required in order to get a callback in onPageSelected
        mViewPager.setCurrentItem(0);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // Remove the OnPageChangeListener when the Activity is destroyed
        mViewPager.removeOnPageChangeListener(mOnPageChangeListener);
    }

    private void setupProgressLayout() {
        int circleSize = Utils.convertDpToPixel(this, 8);
        int circleMargin = Utils.convertDpToPixel(this, 4);

        for (int i = 0; i < mPagerAdapter.getCount(); i++) {
            // Create a new ImageView with a circle background
            ImageView circle = new ImageView(this);
            circle.setBackgroundResource(R.drawable.progress_circle);

            // Set up LayoutParams for the circle
            LinearLayout.LayoutParams pm = new LinearLayout.LayoutParams(circleSize, circleSize);
            pm.setMargins(circleMargin, 0, circleMargin, 0);
            circle.setLayoutParams(pm);

            // Add the circle to the progress layout
            mProgressLayout.addView(circle);
        }

        // Select the first item
        setProgressSelection(0);
    }

    /**
     * OnClickListener for the "Skip" button.
     */
    private final View.OnClickListener mOnSkipClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // "Skip" button pressed callback
            onSkipPressed();
        }
    };

    /**
     * OnClickListener for the "Next" / "Done" button.
     */
    private final View.OnClickListener mOnNextClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int currentItem = mViewPager.getCurrentItem();

            // Navigate to the next page in the ViewPager if we're not at the end already
            if (currentItem < mPagerAdapter.getCount() - 1) {
                // Navigate to the next page
                mViewPager.setCurrentItem(currentItem + 1, true);

                // "Next" button pressed callback
                onNextPressed(mViewPager.getCurrentItem());
            } else {
                // "Done" button pressed callback
                onDonePressed();
            }
        }
    };

    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (position < mPagerAdapter.getCount() - 1) {
                // Set the background color of the ViewPager based on the scroll offset
                // in respect to the background color of the pages being scrolled
                mViewPager.setBackgroundColor((Integer) mArgbEvaluator.evaluate(positionOffset,
                        mPagerAdapter.getBackgroundColorForPage(position),
                        mPagerAdapter.getBackgroundColorForPage(position + 1)));
            }
        }

        @Override
        public void onPageSelected(int position) {
            // Check if the current ViewPager position is at the end
            if (position == mPagerAdapter.getCount() - 1) {
                // Show the "Done" button
                mNextButton.showDoneButton(true);

                // Hide the "Skip" button
                mSkipButton.setVisibility(View.INVISIBLE);
            } else {
                // Check if the "Done" button is shown - if so, we should change to
                // "Next" as we're no longer on the last page in the ViewPager
                if (mNextButton.getButtonStyle() == NextDoneButton.STYLE_DONE) {
                    mNextButton.showNextButton(true);
                }

                // Check if the "Skip" button isn't visible when it should be
                if (mShowSkipButton && mSkipButton.getVisibility() != View.VISIBLE) {
                    mSkipButton.setVisibility(View.VISIBLE);
                }
            }

            setProgressSelection(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {}
    };

    private void setProgressSelection(int position) {
        for (int i = 0; i < mProgressLayout.getChildCount(); i++) {
            mProgressLayout.getChildAt(i).setBackgroundResource(i == position ?
                    R.drawable.progress_circle_selected :
                    R.drawable.progress_circle);

            if (mProgressCircleColor == 0) {
                mProgressCircleColor = ContextCompat.getColor(this, R.color.progress_circle_color);
            }

            mProgressLayout.getChildAt(i).getBackground()
                    .setColorFilter(mProgressCircleColor, PorterDuff.Mode.SRC_IN);
        }
    }

    /**
     * Set the color of the progress circles at the bottom
     * of the intro screen.
     * @param color Progress circle color to set.
     */
    protected void setProgressCircleColor(int color) {
        mProgressCircleColor = color;
    }

    /**
     * Use this method to initialize your intro screens
     * and set up various styling options.
     */
    protected abstract void initialize();

    /**
     * Add an intro screen (Fragment) to the ViewPager.
     * @param introScreen Fragment to add.
     */
    protected void addIntroScreen(BaseIntroFragment introScreen, int backgroundColor) {
        mPagerAdapter.addFragment(introScreen, backgroundColor);
    }

    /**
     * Determine if the "Next" button should be shown. Default is true.
     * @param showNextButton True if visible, false otherwise.
     */
    protected void setShowNextButton(boolean showNextButton) {
        mShowNextButton = showNextButton;
        mNextButton.setVisibility(mShowNextButton ? View.VISIBLE : View.GONE);
    }

    /**
     * Determine if the "Skip" button should be shown. Default is true.
     * @param showSkipButton True if visible, false otherwise.
     */
    protected void setShowSkipButton(boolean showSkipButton) {
        mShowSkipButton = showSkipButton;
        mSkipButton.setVisibility(mShowSkipButton ? View.VISIBLE : View.GONE);
    }

    /**
     * Set the text color of the Skip button. Default color is white (#F0F0F0).
     * @param color Text color to set.
     */
    protected void setSkipButtonTextColor(int color) {
        mSkipButton.setTextColor(color);
    }

    /**
     * Set the background color of the Next / Done button. The selected color
     * will be 25 percent opaque. Default color is white (#FFFFFF).
     * @param color Background color for the Next / Done button.
     */
    protected void setNextButtonBackgroundColor(int color) {
        mNextButton.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_IN);
    }

    /**
     * Set the color of the arrow / done icon for the Next / Done button.
     * Default color is white (#F0F0F0).
     * @param color
     */
    protected void setNextButtonIconColor(int color) {
        mNextButton.setColor(color);
    }

    /**
     * Callback when the "Skip" button is pressed.
     */
    protected abstract void onSkipPressed();

    /**
     * Callback when the "Next" button is pressed.
     * @param pagePosition Zero-based index of the current page position.
     */
    protected abstract void onNextPressed(int pagePosition);

    /**
     * Callback when the "Done" button is pressed.
     */
    protected abstract void onDonePressed();

}
