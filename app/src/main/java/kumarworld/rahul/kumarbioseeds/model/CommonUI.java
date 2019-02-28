package kumarworld.rahul.kumarbioseeds.model;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import kumarworld.rahul.kumarbioseeds.R;


/**
 * Created by rahul on 23/3/18.
 */

public class CommonUI {

    public static final String mypreference = "mypref";
    public static final String SName = "nameKey";
    public static final String SEmail = "emailKey";
    public static final String Sstate = "stateKey";
    public static final String SCity = "cityKey";
    public static final String SMobile = "mobileKey";
    public static final String Sstatus = "statusKey";
    public static final String SuserID="userIDKey";
    public static final String Slang="langKey";


    public static  String WhatsappNo="";
    public static  String MobileNo="";


    public static void showAlert(Context context , String title, String msg){

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.layout_custom_alert_dialog);
        TextView txtContent=dialog.findViewById(R.id.tvCustomAlertContent);
        TextView txtTitle=dialog.findViewById(R.id.tvCustomAlertTitle);
        txtTitle.setText(title);
        txtContent.setText(msg);

        Button no =  dialog.findViewById(R.id.btnNo);

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                //  onBackPressed();
                // finish();
            }
        });

        dialog.show();




    }




  /*  public static boolean isNetworkAvailable(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected())
                return true;
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
        return false;
    }
*/



}
