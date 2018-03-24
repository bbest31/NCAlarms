package com.napchatalarms.napchatalarmsandroid.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.napchatalarms.napchatalarmsandroid.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by bbest on 24/03/18.
 */

public class SuggestFactLastFragment extends FactFragment {
    private Button submitBtn;
    private EditText descriptionField;
    private EditText link;
    private View view;
    private int pageNumber;
    private static final String ARG_PAGE = "submitFact";

    public SuggestFactLastFragment(){

    }

    /**
     * Factory method for this fragment class. Constructs a new fragment for the given page number.
     */
    public static SuggestFactLastFragment create(int pageNumber) {
        SuggestFactLastFragment fragment = new SuggestFactLastFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_suggest_fact_last, container, false);
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

        descriptionField = (EditText) view.findViewById(R.id.submit_fact_descrip_edittext);
        link = (EditText) view.findViewById(R.id.link_src_edittext);

        return view;

    }

    @Override
    public int getPageNumber() {
        return pageNumber;
    }
}
