package com.miz.introactivity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;

/**
 * Simple implementation of the BaseIntroFragment for
 * easy-to-use intro screens.
 */
public class IntroFragment extends BaseIntroFragment {

    private static final String TITLE =             "title";
    private static final String TITLE_COLOR =       "titleColor";
    private static final String DESCRIPTION =       "description";
    private static final String DESCRIPTION_COLOR = "descriptionColor";
    private static final String LAYOUT_ID =         "layoutId";
    private static final String DRAWABLE_ID =       "drawableId";
    private static final String RESOURCE_ID_TYPE =  "resourceIdType";

    /**
     * Get an IntroFragment set up with title and description.
     * @param title Title for the intro screen.
     * @param description Description for the intro screen.
     * @return IntroFragment with title and description.
     */
    public static IntroFragment newInstance(String title, String description) {
        Bundle args = new Bundle();
        args.putString(TITLE, title);
        args.putString(DESCRIPTION, description);

        IntroFragment fragment = new IntroFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Get an IntroFragment set up with title, description and a drawable or custom layout.
     * @param title Title for the intro screen.
     * @param description Description for the intro screen.
     * @param resourceId ID of the layout file or drawable to use for the intro screen.
     * @param resourceIdType Type for the reference ID
     * @return IntroFragment with title, description and drawable / custom layout.
     */
    public static IntroFragment newInstance(String title, String description, int resourceId, int resourceIdType) {
        Bundle args = new Bundle();
        args.putString(TITLE, title);
        args.putString(DESCRIPTION, description);
        args.putInt(RESOURCE_ID_TYPE, resourceIdType);
        args.putInt(resourceIdType == RESOURCE_TYPE_LAYOUT ? LAYOUT_ID : DRAWABLE_ID, resourceId);

        IntroFragment fragment = new IntroFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Get an IntroFragment set up with title, description and custom colors for both.
     * @param title Title for the intro screen.
     * @param titleColor Text color for the title.
     * @param description Description for the intro screen.
     * @param descriptionColor Text color for the description.
     * @return IntroFragment with title, description and custom colors.
     */
    public static IntroFragment newInstance(String title, int titleColor, String description,
                                            int descriptionColor) {
        Bundle args = new Bundle();
        args.putString(TITLE, title);
        args.putInt(TITLE_COLOR, titleColor);
        args.putString(DESCRIPTION, description);
        args.putInt(DESCRIPTION_COLOR, descriptionColor);

        IntroFragment fragment = new IntroFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Get an IntroFragment set up with title, description, custom colors for both
     * and a drawable or custom layout.
     * @param title Title for the intro screen.
     * @param titleColor Text color for the title.
     * @param description Description for the intro screen.
     * @param descriptionColor Text color for the description.
     * @param resourceId ID of the layout file or drawable to use for the intro screen.
     * @param resourceIdType Type for the reference ID
     * @return
     */
    public static IntroFragment newInstance(String title, int titleColor, String description,
                                            int descriptionColor, int resourceId, int resourceIdType) {
        Bundle args = new Bundle();
        args.putString(TITLE, title);
        args.putInt(TITLE_COLOR, titleColor);
        args.putString(DESCRIPTION, description);
        args.putInt(DESCRIPTION_COLOR, descriptionColor);
        args.putInt(RESOURCE_ID_TYPE, resourceIdType);
        args.putInt(resourceIdType == RESOURCE_TYPE_LAYOUT ? LAYOUT_ID : DRAWABLE_ID, resourceId);

        IntroFragment fragment = new IntroFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected String getTitle() {
        return getArguments().getString(TITLE);
    }

    @Override
    protected int getTitleColor() {
        if (getArguments().containsKey(TITLE_COLOR)) {
            return getArguments().getInt(TITLE_COLOR);
        }
        return ContextCompat.getColor(getContext(), R.color.title_color);
    }

    @Override
    protected String getDescription() {
        return getArguments().getString(DESCRIPTION);
    }

    @Override
    protected int getDescriptionColor() {
        if (getArguments().containsKey(DESCRIPTION_COLOR)) {
            return getArguments().getInt(DESCRIPTION_COLOR);
        }
        return ContextCompat.getColor(getContext(), R.color.description_color);
    }

    @Override
    protected int getLayoutId() {
        if (getArguments().containsKey(LAYOUT_ID)) {
            return getArguments().getInt(LAYOUT_ID);
        }
        return 0;
    }

    @Override
    protected int getDrawableId() {
        if (getArguments().containsKey(DRAWABLE_ID)) {
            return getArguments().getInt(DRAWABLE_ID);
        }
        return 0;
    }

    @Override
    protected int getResourceType() {
        if (getArguments().containsKey(RESOURCE_ID_TYPE)) {
            return getArguments().getInt(RESOURCE_ID_TYPE);
        }
        return 0;
    }
}
