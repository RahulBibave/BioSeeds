package kumarworld.rahul.kumarbioseeds.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import kumarworld.rahul.kumarbioseeds.R;
import kumarworld.rahul.kumarbioseeds.adapter.Adapter_Categories;
import kumarworld.rahul.kumarbioseeds.adapter.Adapter_Product;
import kumarworld.rahul.kumarbioseeds.model.Categories;
import kumarworld.rahul.kumarbioseeds.model.CommonUI;
import kumarworld.rahul.kumarbioseeds.model.Product;

public class Activity_Product extends AppCompatActivity {

    private ImageView mImgBack;
    private RecyclerView mRecyclerViewProduct;
    private Adapter_Product mAdapterProduct;
    private RequestQueue mRequestQueue;
    private String url="http://app.kumarbioseeds.com/all-products";
    String CateID;
    private EditText mEdtSearch;
    ArrayList<Product>productArrayList;
    private String mUrlSearch="http://app.kumarbioseeds.com/search";
    private SharedPreferences sharedpreferences;
    private String lang;
    private TextView txt_search_cancel;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        sharedpreferences = getSharedPreferences(CommonUI.mypreference,
                Context.MODE_PRIVATE);
        lang=sharedpreferences.getString("langKey","english");
        mRecyclerViewProduct=findViewById(R.id.recycler_product);
       // hideSoftKeyboard();
        mImgBack=findViewById(R.id.img_arrowback);
        mEdtSearch=findViewById(R.id.edt_Search);
        txt_search_cancel=findViewById(R.id.txt_search_cancel);
        CateID=getIntent().getStringExtra("CateID");



        mRecyclerViewProduct.setHasFixedSize(true);
        GridLayoutManager recycleLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        mRecyclerViewProduct.setLayoutManager(recycleLayoutManager);
        mRecyclerViewProduct.setItemAnimator(new DefaultItemAnimator());
        getProduct();

        mImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        txt_search_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();
                mEdtSearch.setText("");
            }
        });

        mEdtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    getSearchResult();


                    return true;
                }
                return false;
            }
        });
    }


    public void moveToNext(){

    }


    //get Product
    private void getProduct(){

        mRequestQueue=Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                productArrayList=new ArrayList<>();
                Log.e("fgdahfsifehjfrorgt",""+response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if (status==200){
                        JSONArray object=jsonObject.getJSONArray("data");
                        for (int i=0;i<object.length();i++){
                            JSONObject jsonObject1=object.getJSONObject(i);
                            String id=jsonObject1.getString("id");
                            String img=jsonObject1.getString("image");
                            String name=jsonObject1.getString("name");
                            String cultivation_manual=jsonObject1.getString("cultivation_manual");
                            Product product=new Product(id,name,img,cultivation_manual);
                            productArrayList.add(product);

                        }
                        mAdapterProduct=new Adapter_Product(getBaseContext(),productArrayList);
                        mRecyclerViewProduct.setAdapter(mAdapterProduct);


                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.e("fgdahfsifehjfrorgt",""+error);
            }
        }) {
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
                params.put("category_id", CateID);
                params.put("language", lang);
                return params;
            }

        };
        mRequestQueue.add(stringRequest);
        progressDialog=new ProgressDialog(Activity_Product.this);
        progressDialog.setMessage("Please Wait....");
        progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
        progressDialog.show();
    }





    private void getSearchResult(){
        mRequestQueue =Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, mUrlSearch, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hideSoftKeyboard();
                progressDialog.dismiss();
                productArrayList=new ArrayList<>();
                Log.e("fgdahfsifehjfrorgt",""+response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if (status==200){
                        JSONArray object=jsonObject.getJSONArray("data");
                        for (int i=0;i<object.length();i++){
                            JSONObject jsonObject1=object.getJSONObject(i);
                            String id=jsonObject1.getString("id");
                            String img=jsonObject1.getString("image");
                            String name=jsonObject1.getString("name");
                            String cultivation_manual=jsonObject1.getString("cultivation_manual");
                            Product product=new Product(id,name,img,cultivation_manual);
                            productArrayList.add(product);

                        }
                        mAdapterProduct=new Adapter_Product(getBaseContext(),productArrayList);
                        mRecyclerViewProduct.setAdapter(mAdapterProduct);


                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                NetworkResponse response = error.networkResponse;
                if (error instanceof ServerError && response != null) {
                    try {
                        hideSoftKeyboard();
                        String res = new String(response.data,
                                HttpHeaderParser.parseCharset(response.headers, "utf-8"));

                        JSONObject obj = new JSONObject(res);
                        String status=obj.getString("message");
                        if (lang.equalsIgnoreCase("hindi")){
                            CommonUI.showAlert(Activity_Product.this,"kumar BioSeeds", "कोई डेटा नहीं मिला");

                        }else {
                            CommonUI.showAlert(Activity_Product.this,"kumar BioSeeds",status);
                        }
                       // CommonUI.showAlert(Register_Activity.this,"kumar BioSeeds",status);
                        // Now you can use any deserializer to make sense of data

                    } catch (UnsupportedEncodingException e1) {
                        // Couldn't properly decode data to string
                        e1.printStackTrace();
                    } catch (JSONException e2) {
                        // returned data is not JSONObject?
                        e2.printStackTrace();
                    }
                }



                //CommonUI.showAlert(getBaseContext(),"Kumar BioSeeds","कृपया हिंदी में टाइप करें");
                //Log.e("fgdahfsifehjfrorgt",""+error);
            }
        }) {
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
                params.put("search_key", mEdtSearch.getText().toString().trim());
                params.put("language", lang);
                return params;
            }

        };
        mRequestQueue.add(stringRequest);
        progressDialog=new ProgressDialog(Activity_Product.this);
        progressDialog.setMessage("Please Wait....");
        progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
        progressDialog.show();
    }
    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
}
