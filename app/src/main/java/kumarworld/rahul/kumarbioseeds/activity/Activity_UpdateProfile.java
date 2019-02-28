package kumarworld.rahul.kumarbioseeds.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

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
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import kumarworld.rahul.kumarbioseeds.R;
import kumarworld.rahul.kumarbioseeds.model.CommonUI;

public class Activity_UpdateProfile extends AppCompatActivity {

    private Button mBtnSubmit;
    private TextView txtSkip;
    SharedPreferences sharedpreferences;
    String name,email,mobile,city,state,userID;
    EditText edtName, edtCity, edtState,edtMobile,edtEmail;
    private RequestQueue mRequestQueue;
    private String mUrl="http://app.kumarbioseeds.com/update-profile";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mBtnSubmit=findViewById(R.id.btnLogin);
        txtSkip=findViewById(R.id.txtSkip);
        txtSkip.setVisibility(View.GONE);
        findViews();
        mBtnSubmit.setText("Submit");
        mBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validation()){
                    updateProfile();
                }
            }
        });
    }

    private void findViews() {
        sharedpreferences = getSharedPreferences(CommonUI.mypreference,
                Context.MODE_PRIVATE);
        name=sharedpreferences.getString(CommonUI.SName, "");
        email=sharedpreferences.getString(CommonUI.SEmail, "");
        mobile=sharedpreferences.getString(CommonUI.SMobile, "");
        city=sharedpreferences.getString(CommonUI.SCity, "");
        state=sharedpreferences.getString(CommonUI.Sstate, "");
        userID=sharedpreferences.getString(CommonUI.SuserID,"");
        edtName=findViewById(R.id.edtUserName);
        edtCity=findViewById(R.id.edtUserCity);
        edtState=findViewById(R.id.edtUserState);
        edtMobile=findViewById(R.id.edtUserPhone);
        edtEmail=findViewById(R.id.edtUserEmail);

        edtName.setText(name);
        edtCity.setText(city);
        edtState.setText(state);
        edtMobile.setText(mobile);
        edtEmail.setText(email);


    }

    private boolean validation() {


        String name=edtName.getText().toString().trim();
        String phone=edtMobile.getText().toString().trim();
        String email=edtEmail.getText().toString().trim();
        String city=edtCity.getText().toString().trim();
        String state=edtCity.getText().toString();


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


    private void updateProfile(){
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
                    Log.e("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx",""+response);
                    if (status==200){
                        JSONObject object=jsonObject.getJSONObject("data");
                        String mName=object.getString("name");
                        String mEmail=object.getString("email");
                        String mMobile=object.getString("mobile_no");
                        String mPlace=object.getString("place");
                        String mState=object.getString("state");
                        String mStatus=object.getString("status");
                       // String mUserID=object.getString("user_id");








                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString(CommonUI.SName, mName);
                        editor.putString(CommonUI.SEmail, mEmail);
                        editor.putString(CommonUI.SMobile, mMobile);
                        editor.putString(CommonUI.SCity, mPlace);
                        editor.putString(CommonUI.Sstate, mState);
                        editor.putString(CommonUI.Sstatus, mStatus);
                       // editor.putString(Register_Activity.SuserID,mUserID);
                        editor.commit();





                        Intent intent=new Intent(Activity_UpdateProfile.this,MainActivity.class);
                        startActivity(intent);
                        finish();

                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

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
                params.put("name", edtName.getText().toString());
                params.put("email", edtEmail.getText().toString());
                params.put("mobile_no", edtMobile.getText().toString());
                params.put("place", edtCity.getText().toString());
                params.put("state", edtState.getText().toString());
                params.put("password", edtName.getText().toString());
                params.put("user_id",userID);

                //returning parameters
                return params;
            }
        };

        mRequestQueue.add(stringRequest);
    }
}
