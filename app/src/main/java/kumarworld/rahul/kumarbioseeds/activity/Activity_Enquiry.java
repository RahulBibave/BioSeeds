package kumarworld.rahul.kumarbioseeds.activity;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import kumarworld.rahul.kumarbioseeds.R;
import kumarworld.rahul.kumarbioseeds.fragment.fragment_Ask_Quation;
import kumarworld.rahul.kumarbioseeds.fragment.fragment_Enquiry;

public class Activity_Enquiry extends AppCompatActivity {
    RadioGroup radioGroup;
    RadioButton radioEnquiry,radioAsk;
    FragmentManager fragmentManager;
    FragmentTransaction transaction;
    private ImageView mImgBack;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enquiry);
        radioGroup=findViewById(R.id.radioGroup1);
        radioEnquiry=findViewById(R.id.radioMap);
        radioAsk=findViewById(R.id.radioList);
        mImgBack=findViewById(R.id.img_arrowback);
        replaceFragment(new fragment_Enquiry());

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId){
                    case R.id.radioMap:
                        replaceFragment(new fragment_Enquiry());
                        break;

                    case R.id.radioList:
                        replaceFragment(new fragment_Ask_Quation());
                        break;

                }

            }
        });
        mImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void replaceFragment(Fragment fragment) {
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.Container, fragment, fragment.getTag());
        transaction.commit();
    }

}
