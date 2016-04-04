package com.miz.introactivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Base class for the intro screen fragment.
 */
public abstract class BaseIntroFragment extends Fragment {

    public static final int RESOURCE_TYPE_LAYOUT = 0, RESOURCE_TYPE_DRAWABLE = 1;

    /**
     * Get the title for the intro screen.
     * @return Title for the intro screen.
     */
    protected abstract String getTitle();

    /**
     * Get the text color of the intro screen title.
     * @return Text color of the title.
     */
    protected abstract int getTitleColor();

    /**
     * Get the description for the intro screen.
     * @return Description for the intro screen.
     */
    protected abstract String getDescription();

    /**
     * Get the text color of the intro screen description.
     * @return Text color of the description.
     */
    protected abstract int getDescriptionColor();

    /**
     * Get the layout ID of the layout to inflate.
     * @return Layout ID to inflate.
     */
    protected abstract int getLayoutId();

    /**
     * Get the drawable ID of the drawable to show.
     * @return ID of the drawable to show.
     */
    protected abstract int getDrawableId();

    /**
     * Get the type of resource ID. Can be either RESOURCE_TYPE_LAYOUT
     * or RESOURCE_TYPE_DRAWABLE.
     * @return Resource ID type.
     */
    protected abstract int getResourceType();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.intro_page, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Set the View layer to a hardware rendering layer
        view.setLayerType(View.LAYER_TYPE_HARDWARE, null);

        // Set a tag on the View so we can use it for custom animations
        view.setTag(this);

        // Title
        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText(getTitle());
        title.setTextColor(getTitleColor());

        // Description
        TextView description = (TextView) view.findViewById(R.id.description);
        description.setText(getDescription());
        description.setTextColor(getDescriptionColor());

        // ViewStub
        ViewStub viewStub = (ViewStub) view.findViewById(R.id.viewstub);
        switch (getResourceType()) {
            // If the resource ID represents a layout ID,
            // inflate the layout based on the ID
            case RESOURCE_TYPE_LAYOUT:
                if (getLayoutId() != 0) {
                    viewStub.setLayoutResource(getLayoutId());
                    viewStub.inflate();
                }
                break;

            // If the resource ID represents a drawable ID,
            // inflate the drawable layout and set the image resource
            case RESOURCE_TYPE_DRAWABLE:
                viewStub.setLayoutResource(R.layout.drawable_layout);
                viewStub.inflate();

                ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
                imageView.setImageResource(getDrawableId());
                break;
        }
    }

}
