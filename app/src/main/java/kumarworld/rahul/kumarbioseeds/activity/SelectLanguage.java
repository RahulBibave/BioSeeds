package kumarworld.rahul.kumarbioseeds.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import kumarworld.rahul.kumarbioseeds.R;
import kumarworld.rahul.kumarbioseeds.model.CommonUI;

public class SelectLanguage extends AppCompatActivity {

    private RelativeLayout englishView,hindiView,selectedViewhindi,selectedView_english;
    String lang;
    SharedPreferences sharedpreferences;
    private Button mBtnContinue;
    private ImageView mImgBack;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_select_languge);


        sharedpreferences = getSharedPreferences(CommonUI.mypreference,
                Context.MODE_PRIVATE);
        lang=sharedpreferences.getString("langKey","english");
        selectedView_english=findViewById(R.id.selectedView_english);
        selectedViewhindi=findViewById(R.id.selectedViewhindi);
        englishView=findViewById(R.id.englishView);
        hindiView=findViewById(R.id.hindiView);
        mBtnContinue=findViewById(R.id.submitLight);
        mImgBack=findViewById(R.id.img_arrowback);
        mImgBack.setVisibility(View.GONE);
        if (lang.equalsIgnoreCase("english")){
            selectedViewhindi.setVisibility(View.GONE);
            selectedView_english.setVisibility(View.VISIBLE);
        }else if (lang.equalsIgnoreCase("hindi")){
            selectedViewhindi.setVisibility(View.VISIBLE);
            selectedView_english.setVisibility(View.GONE);
        }

        englishView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedViewhindi.setVisibility(View.GONE);
                selectedView_english.setVisibility(View.VISIBLE);
                lang="english";

            }
        });

        hindiView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedViewhindi.setVisibility(View.VISIBLE);
                selectedView_english.setVisibility(View.GONE);
                lang="hindi";

            }
        });

        mBtnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(CommonUI.Slang, lang);
                editor.putString(CommonUI.Sstatus,"3");
                editor.commit();
                Intent intent=new Intent(SelectLanguage.this,Register_Activity.class);
                startActivity(intent);
                finish();
            }
        });




    }
}
