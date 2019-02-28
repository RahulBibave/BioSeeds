package kumarworld.rahul.kumarbioseeds.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.PatternsCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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

public class fragment_Enquiry extends Fragment {
    private EditText edtName;
    private EditText edtEmail;
    private EditText edtMobile;
    private EditText edtCity;
    private EditText edtPinCode;
    private EditText edtState;
    private EditText edtEqMessage;
    private Button btnSubmitEq;
    private RequestQueue mRequestQueue;
    private String url="http://app.kumarbioseeds.com/enquiry";
    SharedPreferences sharedpreferences;
    String name,email,mobile,city,state;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.lay_fragment_enquiry,container,false);
        sharedpreferences = getContext().getSharedPreferences(CommonUI.mypreference,
                Context.MODE_PRIVATE);
        name=sharedpreferences.getString(CommonUI.SName, "");
        email=sharedpreferences.getString(CommonUI.SEmail, "");
        mobile=sharedpreferences.getString(CommonUI.SMobile, "");
        city=sharedpreferences.getString(CommonUI.SCity, "");
        state=sharedpreferences.getString(CommonUI.Sstate, "");
        findViews(view);
        btnSubmitEq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validation()){
                    submitEnquiry();
                }
            }
        });

        return view;
    }
    private void findViews(View view) {
        edtName =view.findViewById( R.id.edt_name );
        edtEmail = view.findViewById( R.id.edt_email );
        edtMobile = view.findViewById( R.id.edt_Mobile );
        edtCity = view.findViewById( R.id.edt_City );
        edtPinCode =view.findViewById( R.id.edt_PinCode );
        edtState = view.findViewById( R.id.edt_State );
        edtEqMessage = view.findViewById( R.id.edtEqMessage );
        btnSubmitEq = view.findViewById( R.id.btnSubmitEq );
        edtName.setText(name);
        edtCity.setText(city);
        edtState.setText(state);
        edtMobile.setText(mobile);
        edtEmail.setText(email);


    }
    private void submitEnquiry(){
        final ProgressDialog loading = ProgressDialog.show(getContext(),"Uploading...","Please wait...",false,false);
        mRequestQueue=Volley.newRequestQueue(getContext());
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("sfgudssssssssssssssssssssssssshkd",""+response);
                loading.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    String msg=jsonObject.getString("message");
                    if (status==200){
                        final Dialog dialog = new Dialog(getContext());
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.setContentView(R.layout.layout_custom_alert_dialog);
                        TextView txtContent=(TextView)dialog.findViewById(R.id.tvCustomAlertContent);
                        TextView txtTitle=(TextView)dialog.findViewById(R.id.tvCustomAlertTitle);
                        txtTitle.setText("Kumar Bio-Seeds");
                        txtContent.setText(msg);

                        Button no = (Button) dialog.findViewById(R.id.btnNo);

                        no.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                                getActivity().finish();
                                //  onBackPressed();
                                // finish();
                            }
                        });

                        dialog.show();


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
                params.put("message", edtEqMessage.getText().toString());





                //returning parameters
                return params;
            }
        };

        mRequestQueue.add(stringRequest);
    }



    private boolean validation() {

        String name=edtName.getText().toString().trim();
        String phone=edtMobile.getText().toString().trim();
        String email=edtEmail.getText().toString().trim();
        String city=edtCity.getText().toString().trim();
        String state=edtState.getText().toString();


        if (name.equalsIgnoreCase(""))
            CommonUI.showAlert(getContext(), getResources().getString(R.string.app_name), "Please Enter valid User Name");
        else if (phone.equalsIgnoreCase(""))
            CommonUI.showAlert(getContext(), getResources().getString(R.string.app_name), "Please Enter Mobile No.");
        else if (phone.length()<10)
            CommonUI.showAlert(getContext(), getResources().getString(R.string.app_name), "Please Enter Valid Mobile No.");
        else if (!email.equalsIgnoreCase("")&&!(PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()))
            CommonUI.showAlert(getContext(), getResources().getString(R.string.app_name), "Please Enter Valid Email Address.");
        else if (city.equalsIgnoreCase(""))
            CommonUI.showAlert(getContext(), getResources().getString(R.string.app_name), "Please Enter City.");
        else if (state.equalsIgnoreCase(""))
            CommonUI.showAlert(getContext(), getResources().getString(R.string.app_name), "Please Enter State");
        else
            return true;

        return false;
    }






}
