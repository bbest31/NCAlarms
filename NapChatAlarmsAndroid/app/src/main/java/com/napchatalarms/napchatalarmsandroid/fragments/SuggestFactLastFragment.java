package com.napchatalarms.napchatalarmsandroid.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

/** Suggest Fact fragment without right chevron.
 * Created by bbest on 24/03/18.
 */
public class SuggestFactLastFragment extends FactFragment implements IFactFragment {
    private static final String ARG_PAGE = "submitFact";
    private Button submitBtn;
    private EditText descriptionField;
    private EditText link;
    private int pageNumber;

    /**
     * Instantiates a new Suggest fact last fragment.
     */
    public SuggestFactLastFragment() {

    }

    /**
     * Factory method for this fragment class. Constructs a new fragment for the given page number.
     *
     * @param pageNumber the page number
     * @return the suggest fact last fragment
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
        //noinspection ConstantConditions
        pageNumber = getArguments().getInt(ARG_PAGE);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_suggest_fact_last, container, false);

        submitBtn = view.findViewById(R.id.submit_fact_btn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String recipient = getString(R.string.support_email);
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY hh:mm aa");
                String timestamp = dateFormat.format(System.currentTimeMillis());
                String subject = "Napchat Fact Submission " + getString(R.string.version_number) + " " + timestamp;
                String body =
                        "DESCRIPTION: \n" + descriptionField.getText() + " \n" +
                                "\n LINK: " + link.getText();

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:" + recipient));
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


        // Set enabled to false so they aren't clickable through the previous fragment.
        submitBtn.setEnabled(false);
        descriptionField = view.findViewById(R.id.submit_fact_descrip_edittext);
        descriptionField.setEnabled(false);
        link = view.findViewById(R.id.link_src_edittext);
        link.setEnabled(false);


        return view;

    }

    @Override
    public int getPageNumber() {
        return pageNumber;
    }

    /**
     * When this fragment becomes the selected fragment in the ViewPager this method lets it know
     * to enable its views. This prevents them from being clickable from the view before.
     */
    @Override
    public void onBecameVisible() {
        if (getActivity() != null && !getActivity().isFinishing()) {
            submitBtn.setEnabled(true);
            link.setEnabled(true);
            descriptionField.setEnabled(true);
        }
    }

    /**
     * When this fragment becomes invisible then we disable the views again.
     */
    @Override
    public void onBecameInvisible() {
        link.setEnabled(false);
        descriptionField.setEnabled(false);
        submitBtn.setEnabled(false);
    }

}
