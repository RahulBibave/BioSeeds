package kumarworld.rahul.kumarbioseeds.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import kumarworld.rahul.kumarbioseeds.R;

import kumarworld.rahul.kumarbioseeds.activity.Activity_Variety;
import kumarworld.rahul.kumarbioseeds.model.CommonUI;

import static android.app.Activity.RESULT_OK;

public class fragment_Ask_Quation extends Fragment {

    private ImageView imgCapture,imageView;
    private final int requestCode=20;
    private EditText user_question;
    private TextView mTxtCount;
    RequestQueue queue ;
    String url ="http://app.kumarbioseeds.com/ask-questions";
    Bitmap bitmap;
    Button submit_question;

    SharedPreferences sharedpreferences;
    String name,email,mobile,city,state;
    private ProgressDialog progressDialog;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.lay_askquation,container,false);
        queue= Volley.newRequestQueue(getContext());
        sharedpreferences = getContext().getSharedPreferences(CommonUI.mypreference,
                Context.MODE_PRIVATE);
        name=sharedpreferences.getString(CommonUI.SName, "");
        email=sharedpreferences.getString(CommonUI.SEmail, "");
        mobile=sharedpreferences.getString(CommonUI.SMobile, "");
        city=sharedpreferences.getString(CommonUI.SCity, "");
        state=sharedpreferences.getString(CommonUI.Sstate, "");

        imgCapture=view.findViewById(R.id.image1);
        imageView=view.findViewById(R.id.image2);
        submit_question=view.findViewById(R.id.submit_question);
        user_question=view.findViewById(R.id.user_question);
        mTxtCount=view.findViewById(R.id.txtCount);
        user_question.addTextChangedListener(mTextEditorWatcher);


        submit_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

        imgCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCapture=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intentCapture,requestCode);
            }
        });




        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (this.requestCode==requestCode && resultCode==RESULT_OK){
            bitmap=(Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
        }


    }

    private final TextWatcher mTextEditorWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //This sets a textview to the current length
            mTxtCount.setText(String.valueOf(s.length()));
        }

        public void afterTextChanged(Editable s) {
        }
    };



    private void submitQuation(){

    }



    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }



    private void uploadImage(){
        //Showing the progress dialog
        final ProgressDialog loading = ProgressDialog.show(getContext(),"Uploading...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Log.e("rrrrrrrrrrrrrreeeeeeeee",""+s);
                        //Disimissing the progress dialog
                        loading.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int status=jsonObject.getInt("status");
                            String msg=jsonObject.getString("message");
                            if (status==200){
                                //CommonUI.showAlert(getContext(),"Kumar Bio Seeds",msg);

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
                        //Showing toast message of the response
                       // Toast.makeText(getContext(), s , Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();
                        Log.e("EEEEEEEEEEEEEEEEee",""+volleyError);
                        //Showing toast
                        Toast.makeText(getContext(), ""+volleyError, Toast.LENGTH_LONG).show();
                    }
                }){
            //
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Client-Service", "frontend-client");
                headers.put("Auth-key", "bioseedsapi");
                return headers;

            }
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String
                String image = getStringImage(bitmap);

                Map<String,String> params = new Hashtable<String, String>();
                params.put("mobile_no", mobile);
                params.put("name", name);
                params.put("question", "sdsdfdfgfgffghgh");
                params.put("image", image);
                params.put("place", city+","+state);

                //returning parameters
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        //Adding request to the queue
        requestQueue.add(stringRequest);

    }




}
