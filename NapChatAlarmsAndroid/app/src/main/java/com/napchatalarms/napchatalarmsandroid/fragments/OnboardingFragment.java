package com.napchatalarms.napchatalarmsandroid.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.napchatalarms.napchatalarmsandroid.R;

/**
 * Created by bbest on 07/04/18.
 */

public class OnboardingFragment extends Fragment {
    private static final String ARG_PAGE = "onboard";
    private int pageNumber;
    private TextView title;
    private TextView caption;



    public OnboardingFragment(){}

    public static OnboardingFragment create(int pageNumber) {
        OnboardingFragment fragment = new OnboardingFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //noinspection ConstantConditions
        pageNumber = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_onboarding, container, false);
        title = view.findViewById(R.id.onboard_title);
        caption = view.findViewById(R.id.caption_text);

        this.setViews();

        return view;

    }

    public int getPageNumber() {
        return pageNumber;
    }

    private void setViews(){
        String[] titles = getActivity().getResources().getStringArray(R.array.onboarding_titles);
        String[] captions = getActivity().getResources().getStringArray(R.array.onboarding_captions);

        title.setText(titles[getPageNumber()]);
        caption.setText(captions[getPageNumber()]);
    }
}
