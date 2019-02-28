package kumarworld.rahul.kumarbioseeds.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.PatternsCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import kumarworld.rahul.kumarbioseeds.R;
import kumarworld.rahul.kumarbioseeds.model.CommonUI;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class Register_Activity extends AppCompatActivity implements View.OnClickListener {
    private EditText edtUserName;
    private EditText edtUserPhone;
    private EditText edtUserEmail;
    private EditText edtUserCity;
    private EditText edtUserState;
    private Button btnLogin;
    private TextView txtSkip;
    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private ArrayList<String> permissions = new ArrayList<>();
    private final static int ALL_PERMISSIONS_RESULT = 101;
    LocationTrack locationTrack;
    double longitude, latitude;
    private RequestQueue mRequestQueue;
    private String mUrl="http://app.kumarbioseeds.com/user-register";
    String flag="main";



    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViews();
        flag=getIntent().getStringExtra("flag");
        permissions.add(ACCESS_FINE_LOCATION);
        permissions.add(ACCESS_COARSE_LOCATION);
        permissionsToRequest = findUnAskedPermissions(permissions);
        sharedpreferences = getSharedPreferences(CommonUI.mypreference,
                Context.MODE_PRIVATE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (permissionsToRequest.size() > 0)
                requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }
        getLocation();


    }

    private void findViews() {
        edtUserName = findViewById( R.id.edtUserName );
        edtUserPhone = findViewById( R.id.edtUserPhone );
        edtUserEmail = findViewById( R.id.edtUserEmail );
        edtUserCity = findViewById( R.id.edtUserCity );
        edtUserState = findViewById( R.id.edtUserState );
        btnLogin = findViewById( R.id.btnLogin );
        txtSkip = findViewById( R.id.txtSkip );

        btnLogin.setOnClickListener( this );
        txtSkip.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if ( v == btnLogin ) {
            if (validation()){
                registerUser();

            }


        }else if (v==txtSkip){
            Intent intent=new Intent(Register_Activity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }


    private ArrayList<String> findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList<String> result = new ArrayList<String>();

        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    public void getLocation(){
        locationTrack = new LocationTrack(Register_Activity.this);
        if (locationTrack.canGetLocation()) {


            longitude = locationTrack.getLongitude();
            latitude = locationTrack.getLatitude();

            Log.e("current",""+latitude);

            try {

                Geocoder geo = new Geocoder(Register_Activity.this.getApplicationContext(), Locale.getDefault());
                List<Address> addresses = geo.getFromLocation(latitude, longitude, 1);
                Log.e("asasdadadadad",""+addresses.toString());

                if (addresses.isEmpty()) {
                    //  yourtextboxname.setText("Waiting for Location");
                }
                else {
                    if (addresses.size() > 0) {
                        //txtCurrent.setText(addresses.get(0).getFeatureName() + ", " + addresses.get(0).getLocality() +", " + addresses.get(0).getAdminArea() + ", " + addresses.get(0).getCountryName());
                        edtUserCity.setText(addresses.get(0).getLocality());
                        edtUserState.setText(addresses.get(0).getAdminArea());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {

            locationTrack.showSettingsAlert();
        }


    }
    private boolean validation() {


        String name=edtUserName.getText().toString().trim();
        String phone=edtUserPhone.getText().toString().trim();
        String email=edtUserEmail.getText().toString().trim();
        String city=edtUserCity.getText().toString().trim();
        String state=edtUserState.getText().toString();


        if (name.equalsIgnoreCase(""))
            CommonUI.showAlert(this, getResources().getString(R.string.app_name), "Please Enter valid User Name");
        else if (phone.equalsIgnoreCase(""))
            CommonUI.showAlert(this, getResources().getString(R.string.app_name), "Please Enter Mobile No.");
        else if (phone.length()<10)
            CommonUI.showAlert(this, getResources().getString(R.string.app_name), "Please Enter Valid Mobile No.");
        else if (!(PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()))
            CommonUI.showAlert(this, getResources().getString(R.string.app_name), "Please Enter Valid Email Address.");
        else if (city.equalsIgnoreCase(""))
            CommonUI.showAlert(this, getResources().getString(R.string.app_name), "Please Enter City.");
        else if (state.equalsIgnoreCase(""))
            CommonUI.showAlert(this, getResources().getString(R.string.app_name), "Please Enter State");
        else
            return true;

        return false;
    }



    private void registerUser(){
        final ProgressDialog loading = ProgressDialog.show(this,"Uploading...","Please wait...",false,false);
        mRequestQueue=Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, mUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                  //  String msg=jsonObject.getString("message");
                    if (status==200){
                         JSONObject object=jsonObject.getJSONObject("data");
                         String mName=object.getString("name");
                         String mEmail=object.getString("email");
                         String mMobile=object.getString("mobile_no");
                         String mPlace=object.getString("place");
                         String mState=object.getString("state");
                         String mStatus=object.getString("status");
                         String mUserID=object.getString("user_id");







                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString(CommonUI.SName, mName);
                        editor.putString(CommonUI.SEmail, mEmail);
                        editor.putString(CommonUI.SMobile, mMobile);
                        editor.putString(CommonUI.SCity, mPlace);
                        editor.putString(CommonUI.Sstate, mState);
                        editor.putString(CommonUI.Sstatus, mStatus);
                        editor.putString(CommonUI.SuserID,mUserID);
                        editor.commit();




                        try{
                            if (flag.equalsIgnoreCase("product")){
                                finish();

                            }
                        }catch (NullPointerException e){
                            e.printStackTrace();
                            Intent intent=new Intent(Register_Activity.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        }




                    }else {
                        String msg=jsonObject.getString("message");
                        CommonUI.showAlert(Register_Activity.this,"kumar Bio Seeds",msg);
                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // As of f605da3 the following should work
                loading.dismiss();
                NetworkResponse response = error.networkResponse;
                if (error instanceof ServerError && response != null) {
                    try {
                        String res = new String(response.data,
                                HttpHeaderParser.parseCharset(response.headers, "utf-8"));

                        JSONObject obj = new JSONObject(res);
                        String status=obj.getString("message");
                        CommonUI.showAlert(Register_Activity.this,"kumar Bio Seeds",status);
                        // Now you can use any deserializer to make sense of data

                    } catch (UnsupportedEncodingException e1) {
                        // Couldn't properly decode data to string
                        e1.printStackTrace();
                    } catch (JSONException e2) {
                        // returned data is not JSONObject?
                        e2.printStackTrace();
                    }
                }
            }
        }){
            //Header
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Client-Service", "frontend-client");
                headers.put("Auth-key", "bioseedsapi");
                return headers;

            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                Map<String,String> params = new Hashtable<String, String>();
                params.put("name", edtUserName.getText().toString());
                params.put("email", edtUserEmail.getText().toString());
                params.put("mobile_no", edtUserPhone.getText().toString());
                params.put("place", edtUserCity.getText().toString());
                params.put("state", edtUserState.getText().toString());
                params.put("password", edtUserName.getText().toString());
                params.put("device_id", FirebaseInstanceId.getInstance().getToken());
                Log.e("dddddddddddddddddddddddddddddddddddddddddd",""+FirebaseInstanceId.getInstance().getToken());

                //returning parameters
                return params;
            }

        };

        mRequestQueue.add(stringRequest);

    }
}


