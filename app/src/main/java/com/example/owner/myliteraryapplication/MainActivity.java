package com.example.owner.myliteraryapplication;


import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Home button so user can go back to list of poems after reading display
        // Only really needed in portrait mode.
        ActionBar ab = getSupportActionBar();
        ab.setHomeButtonEnabled(true);
        setContentView(R.layout.activity_fragment_layout);
       }

    // This is from Android Developers example code;
    // an activity using two fragments to create a two-pane layout
    // TitlesFragment lists the Poem titles
    public static class TitlesFragment extends ListFragment {
        boolean mDualPane;
        int mCurCheckPosition = 0;

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

            Resources res = getResources();
          //  Convert TypedArray to int[]
            TypedArray ar = res.obtainTypedArray(R.array.ICONS);
            int len = ar.length();
            int[] ICONS = new int[len];
            for (int i = 0; i < len; i++)
                ICONS[i] = ar.getResourceId(i, 0);
            ar.recycle();

            String[] TITLES = res.getStringArray(R.array.POETS);

            // Used a customer adapter to get icons and titles, and it worked well
            setListAdapter(new CustomAdapter(getActivity(), TITLES, ICONS));

           // See if there is a Frame visible that can hold details fragment
            View detailsFrame = getActivity().findViewById(R.id.details);
            mDualPane = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;

            // See if the last save state can be leaded
            if (savedInstanceState != null) {
               mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);
            }
            // Check if we can use dual pane mode
            // Get the list view and show the details
            if (mDualPane) {
                getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                showDetails(mCurCheckPosition);
            }
        }

        @Override
        public void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);
            outState.putInt("curChoice", mCurCheckPosition);
        }

        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
            showDetails(position);
        }

        // Function to either display details in frame fragment or start a new intent
        void showDetails(int index) {
            mCurCheckPosition = index;
            // Landscape mode so can display both
            if (mDualPane) {
                //Get the List view
                getListView().setItemChecked(index, true);
                // Get the correct details fragment
                DetailsActivity.DetailsFragment details = (DetailsActivity.DetailsFragment)
                    getFragmentManager().findFragmentById(R.id.details);
                if (details == null || details.getShownIndex() != index) {
                    details = DetailsActivity.DetailsFragment.newInstance(index);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.details, details);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.commit();
                }

            } else {
                //Portrait mode so call a new intent and Details Activity class
                Intent intent = new Intent();
                intent.setClass(getActivity(), DetailsActivity.class);
                intent.putExtra("index", index);
                startActivity(intent);
            }
        }

    }
}
