package com.napchatalarms.napchatalarmsandroid.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.napchatalarms.napchatalarmsandroid.R;
import com.napchatalarms.napchatalarmsandroid.abstractions.IFactFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by bbest on 18/03/18.
 */

public class SuggestFactFragment extends FactFragment implements IFactFragment {
    private static final String ARG_PAGE = "submitFact";
    private Button submitBtn;
    private EditText descriptionField;
    private EditText link;
    private View view;
    private int pageNumber;

    public SuggestFactFragment() {

    }

    /**
     * Factory method for this fragment class. Constructs a new fragment for the given page number.
     */
    public static SuggestFactFragment create(int pageNumber) {
        SuggestFactFragment fragment = new SuggestFactFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = getArguments().getInt(ARG_PAGE);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_suggest_fact, container, false);
        this.view = view;

        submitBtn = (Button) view.findViewById(R.id.submit_fact_btn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String recepient = getString(R.string.support_email);
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY hh:mm aa");
                String timestamp = dateFormat.format(System.currentTimeMillis());
                String subject = "Napchat Fact Submission " + getString(R.string.version_number) + " " + timestamp;
                String body =
                        "DESCRIPTION: \n" + descriptionField.getText() + " \n" +
                                "\n LINK: " + link.getText();

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:" + recepient));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
                emailIntent.putExtra(Intent.EXTRA_TEXT, body);

                try {
                    startActivity(Intent.createChooser(emailIntent, "Send email using..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getActivity(), "No email clients installed.", Toast.LENGTH_SHORT).show();
                }
                descriptionField.setText("");
                link.setText("");
            }
        });
        // Set the view to be disabled so they aren't clickable before the fragment is visible.
        submitBtn.setEnabled(false);
        descriptionField = (EditText) view.findViewById(R.id.submit_fact_descrip_edittext);
        link = (EditText) view.findViewById(R.id.link_src_edittext);
        link.setEnabled(false);
        descriptionField.setEnabled(false);

        return view;

    }


    @Override
    public int getPageNumber() {
        return pageNumber;
    }

    @Override
    public void onBecameVisible() {
        if (getActivity() != null && !getActivity().isFinishing()) {
            // do something with your Activity -which should also implement an interface!-
            // link.setEnabled(true);
            // descriptionField.setEnabled(true);
        }
    }
}
