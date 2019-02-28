package kumarworld.rahul.kumarbioseeds.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import kumarworld.rahul.kumarbioseeds.R;
import kumarworld.rahul.kumarbioseeds.model.CommonUI;

public class Activity_ProductDetails extends AppCompatActivity {

    private ImageView mImgBack,cropImages;
    private Button mBtnManual,mBtnSuccess,mBtnEnquiry;
    private String mVarName,mVarImg,mVarDetails,mVarCultivation;
    private TextView txt_SignUpHeader;
    private SharedPreferences sharedPreferences;
    private TextView mTxtDesc;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        sharedPreferences = getSharedPreferences(CommonUI.mypreference,
                Context.MODE_PRIVATE);
        mImgBack=findViewById(R.id.img_arrowback);
        mBtnManual=findViewById(R.id.btnManual);
        txt_SignUpHeader=findViewById(R.id.txt_SignUpHeader);
        mBtnSuccess=findViewById(R.id.btnSuccess);
        mBtnEnquiry=findViewById(R.id.btnProEnq);
        mVarName=getIntent().getStringExtra("VarName");
        mVarCultivation=getIntent().getStringExtra("VarCul");
        mVarDetails=getIntent().getStringExtra("VarDetail");
        mVarImg=getIntent().getStringExtra("VarImg");
        mTxtDesc=findViewById(R.id.headingsymptoms);
        mTxtDesc.setText(Html.fromHtml(mVarDetails));


        cropImages=findViewById(R.id.cropImages);
        Picasso.with(this).load(mVarImg).into(cropImages);
        txt_SignUpHeader.setText(mVarName);


        mImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mBtnManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharedPreferences.getString(CommonUI.Sstatus, "").equalsIgnoreCase("1")){
                    Intent intent=new Intent(Activity_ProductDetails.this,Activity_CultivationManual.class);
                    intent.putExtra("manual",mVarCultivation);
                    startActivity(intent);

                }else {
                    final Dialog dialog = new Dialog(Activity_ProductDetails.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.setContentView(R.layout.layout_custom_alert_withoption);
                    TextView txtContent=dialog.findViewById(R.id.tvCustomAlertContent);
                    TextView txtTitle=dialog.findViewById(R.id.tvCustomAlertTitle);
                    txtTitle.setText("Kumar BioSeeds");
                    txtContent.setText("For Cultivation Manual First You have to Register");

                    Button no =  dialog.findViewById(R.id.btnYes);
                    Button yes=dialog.findViewById(R.id.btnNo);

                    no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                            //  onBackPressed();
                            // finish();
                        }
                    });
                    yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent1=new Intent(Activity_ProductDetails.this,Register_Activity.class);
                            intent1.putExtra("flag","product");
                            startActivity(intent1);
                            dialog.dismiss();


                        }
                    });

                    dialog.show();



                }







            }
        });

        mBtnSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Activity_ProductDetails.this,Activity_SuccessStories.class);
                intent.putExtra("cropID","16");
                startActivity(intent);

            }
        });


        mBtnEnquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharedPreferences.getString(CommonUI.Sstatus, "").equalsIgnoreCase("1")){
                    Intent intentEnq = new Intent(Activity_ProductDetails.this, Activity_Enquiry.class);
                    startActivity(intentEnq);
                    finish();

                }else {
                    final Dialog dialog = new Dialog(Activity_ProductDetails.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.setContentView(R.layout.layout_custom_alert_withoption);
                    TextView txtContent = dialog.findViewById(R.id.tvCustomAlertContent);
                    TextView txtTitle = dialog.findViewById(R.id.tvCustomAlertTitle);
                    txtTitle.setText("Kumar BioSeeds");
                    txtContent.setText("For Enquiry  First You have to Register");

                    Button no = dialog.findViewById(R.id.btnYes);
                    Button yes = dialog.findViewById(R.id.btnNo);

                    no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                            //  onBackPressed();
                            // finish();
                        }
                    });
                    yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent11 = new Intent(Activity_ProductDetails.this, Register_Activity.class);
                            intent11.putExtra("flag","product");
                            startActivity(intent11);
                            dialog.dismiss();
                        }
                    });

                    dialog.show();
                }
            }
        });

    }
}
