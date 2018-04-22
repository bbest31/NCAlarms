package com.napchatalarms.napchatalarmsandroid.activities;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.napchatalarms.napchatalarmsandroid.R;
import com.napchatalarms.napchatalarmsandroid.adapters.OnboardingPageAdapter;
import com.napchatalarms.napchatalarmsandroid.utility.DepthPageTransformer;

public class OnboardingActivity extends AppCompatActivity {

    private static int NUM_PAGES = 2;
    private ViewPager pager;
    private OnboardingPageAdapter pageAdapter;
    private LinearLayout DotsLayout;
    private ImageView[] dots;
    private Button endButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        endButton = findViewById(R.id.onboard_end_btn);


        endButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        pageAdapter = new OnboardingPageAdapter(getSupportFragmentManager(),NUM_PAGES);
        pager = findViewById(R.id.onboard_view_pager);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                initDots(position);
                if(position == NUM_PAGES-1){
                    endButton.setText(getString(R.string.okay_got_it));
                } else {
                    endButton.setText(getString(R.string.skip));
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        pager.setOffscreenPageLimit(2);
        //TODO change pagetransformer
        pager.setPageTransformer(true, new DepthPageTransformer());
        pager.setAdapter(pageAdapter);

        DotsLayout = findViewById(R.id.dots_layout);
        initDots(0);
    }

    /**
     * Changes the dot indicators in the onboarding screen to indicate position.
     * @param position
     */
    private void initDots(int position){
        if(DotsLayout != null)
            DotsLayout.removeAllViews();
        dots = new ImageView[NUM_PAGES];

        for(int i = 0; i < NUM_PAGES; i++){
            dots[i] = new ImageView(this);
            if(i == position){
                dots[i].setImageDrawable(getDrawable(R.drawable.dot_selected));
                dots[i].setPadding(2,0,2,0);
            } else{
                dots[i].setImageDrawable(getDrawable(R.drawable.dot_default));
                dots[i].setPadding(2,0,2,0);
            }

            DotsLayout.addView(dots[i]);
        }
    }
}
