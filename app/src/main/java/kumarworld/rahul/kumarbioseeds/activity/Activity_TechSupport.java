package kumarworld.rahul.kumarbioseeds.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberUtils;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import kumarworld.rahul.kumarbioseeds.R;
import kumarworld.rahul.kumarbioseeds.model.CommonUI;

public class Activity_TechSupport extends AppCompatActivity implements View.OnClickListener {


    private LinearLayout sms,call,whatsapp;
    private ImageView imgback;
    private static final String[] INITIAL_PERMS={
            Manifest.permission.CALL_PHONE
    };
    private static final int INITIAL_REQUEST=1337;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_techsupport);
        sms=findViewById(R.id.lin_sms);
        call=findViewById(R.id.lin_call);
        whatsapp=findViewById(R.id.lin_whatsapp);
        imgback=findViewById(R.id.img_arrowback);

        sms.setOnClickListener(this);
        whatsapp.setOnClickListener(this);
        call.setOnClickListener(this);
        imgback.setOnClickListener(this);

    }

    @SuppressLint("MissingPermission")
    @Override
    public void onClick(View v) {
        switch (v.getId() /*to get clicked view id**/) {
            case R.id.lin_sms:
                sendSMS(CommonUI.MobileNo);
                //sendSMS();
                // do something when the corky is clicked

                break;
            case R.id.lin_call:
                int currentapiVersion = android.os.Build.VERSION.SDK_INT;
                if(currentapiVersion>= Build.VERSION_CODES.M) {
                    if (!canAccessLocation()) {
                        requestPermissions(INITIAL_PERMS, INITIAL_REQUEST);
                    }else {
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + CommonUI.MobileNo));
                        startActivity(intent);
                    }
                }
                // do something when the corky2 is clicked

                break;
            case R.id.lin_whatsapp:
                try{
                    String phoneNumber = CommonUI.WhatsappNo;
                    PackageManager pm = getPackageManager();
                    Intent sendIntent = new Intent("android.intent.action.MAIN");
                    sendIntent.setComponent(new ComponentName("com.whatsapp","com.whatsapp.Conversation"));
                    sendIntent.putExtra("jid",     PhoneNumberUtils.stripSeparators("91"+phoneNumber)+"@s.whatsapp.net");//phone number without "+" prefix

                    startActivity(sendIntent);
                }catch (ActivityNotFoundException e){
                    Toast.makeText(this, "WhatsApp not Installed", Toast.LENGTH_SHORT).show();
                }


                break;

            case R.id.img_arrowback:
                finish();
                break;
            default:
                break;
        }

    }


    public void sendSMS(String mblNumVar) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED)
        {
            try
            {
                Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
                smsIntent.addCategory(Intent.CATEGORY_DEFAULT);
                smsIntent.setType("vnd.android-dir/mms-sms");
                smsIntent.setData(Uri.parse("sms:" + CommonUI.MobileNo));
                startActivity(smsIntent);
            }
            catch (Exception ErrVar)
            {
                Toast.makeText(getApplicationContext(),ErrVar.getMessage().toString(),
                        Toast.LENGTH_LONG).show();
                ErrVar.printStackTrace();
            }
        }
        else
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            {
                requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 10);
            }
        }
    }


    private void sendSMS() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) // At least KitKat
        {
            String defaultSmsPackageName = Telephony.Sms.getDefaultSmsPackage(this); // Need to change the build to API 19

            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.setType("text/plain");
            sendIntent.putExtra(Intent.EXTRA_TEXT, "text");

            if (defaultSmsPackageName != null)// Can be null in case that there is no default, then the user would be able to choose
            // any app that support this intent.
            {
                sendIntent.setPackage(defaultSmsPackageName);
            }
            startActivity(sendIntent);

        }
        else // For early versions, do what worked for you before.
        {
            Intent smsIntent = new Intent(android.content.Intent.ACTION_VIEW);
            smsIntent.setType("vnd.android-dir/mms-sms");
            smsIntent.putExtra("address",CommonUI.MobileNo);
            smsIntent.putExtra("sms_body","message");
            startActivity(smsIntent);
        }
    }

    private boolean canAccessLocation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return(hasPermission(Manifest.permission.CALL_PHONE));
        }
        return false;
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean hasPermission(String perm) {
        return(PackageManager.PERMISSION_GRANTED==checkSelfPermission(perm));
    }
}
