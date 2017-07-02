package com.example.owner.myliteraryapplication;

import android.app.Fragment;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

// Gets the second fragment and updates it in the frame
public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Check if phone is in landscape mode
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            finish();
            return;
        }
        if (savedInstanceState == null) {
            DetailsFragment details = new DetailsFragment();
            details.setArguments(getIntent().getExtras());
            getFragmentManager().beginTransaction()
                    .add(android.R.id.content, details).commit();
        }
    }

    // Second fragment - shows the details of the Poems
    public static class DetailsFragment extends Fragment {

        public static DetailsFragment newInstance(int index) {
            DetailsFragment f = new DetailsFragment();
            Bundle args = new Bundle();
            args.putInt("index", index);
            f.setArguments(args);
            return f;
        }

        public int getShownIndex() {
            return getArguments().getInt("index", 0);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            if (container == null) {
                return null;
            }
            Resources res = getResources();
            String[] POEMS = res.getStringArray(R.array.POEMS);

            View view = inflater.inflate(R.layout.display_view,
                    container, false);
            TextView displayText = (TextView) view.findViewById(R.id.textDisplay);
            // Did this to experiment with changing things programatically
            // Realise that I could do it in display_view layout file
            // but wanted to play around with features and possibilities
            int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    4, getActivity().getResources().getDisplayMetrics());
            displayText.setPadding(padding, padding, padding, padding);
            displayText.setTextColor(Color.RED);
            displayText.setTextSize(16);
            displayText.setText(POEMS[getShownIndex()]);
            return view;
        }
    }
}
