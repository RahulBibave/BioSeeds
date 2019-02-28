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
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import kumarworld.rahul.kumarbioseeds.R;
import kumarworld.rahul.kumarbioseeds.adapter.AdapterVarites;
import kumarworld.rahul.kumarbioseeds.adapter.Adapter_Categories;
import kumarworld.rahul.kumarbioseeds.interfaces.ProductDetails;
import kumarworld.rahul.kumarbioseeds.model.Categories;
import kumarworld.rahul.kumarbioseeds.model.CommonUI;
import kumarworld.rahul.kumarbioseeds.model.Varites;

public class Activity_Variety extends AppCompatActivity implements ProductDetails {
    private RecyclerView mRecyclerViewVariety;
    private RequestQueue mRequestQueue;
    private String mUrl="http://app.kumarbioseeds.com/products-variety";
    private ArrayList<Varites>varitesArrayList;
    private AdapterVarites mAdapterVarites;
    private String productID,cultivation;
    private SharedPreferences sharedpreferences;
    private String lang;
    private ImageView mImgBack;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_variety);
        sharedpreferences = getSharedPreferences(CommonUI.mypreference,
                Context.MODE_PRIVATE);
        lang=sharedpreferences.getString("langKey","engilsh");
        mImgBack=findViewById(R.id.img_arrowback);
        mRecyclerViewVariety=findViewById(R.id.recyclerVarieties);
        mRecyclerViewVariety.setHasFixedSize(true);
        GridLayoutManager recycleLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        mRecyclerViewVariety.setLayoutManager(recycleLayoutManager);
        mRecyclerViewVariety.setItemAnimator(new DefaultItemAnimator());
        productID=getIntent().getStringExtra("productID");
        cultivation=getIntent().getStringExtra("cultivation");
        getVarites();
        mImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void getVarites() {
        mRequestQueue=Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, mUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                varitesArrayList=new ArrayList<>();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if (status==200){
                        JSONArray object=jsonObject.getJSONArray("data");


                        for (int i=0;i<object.length();i++){
                            JSONObject jsonObject1=object.getJSONObject(i);
                            String id=jsonObject1.getString("id");
                            String name=jsonObject1.getString("name");
                            String desc=jsonObject1.getString("variety_details");
                            String img_path=jsonObject1.getString("image");

                            Varites varites=new Varites(id,name,img_path,desc);
                            varitesArrayList.add(varites);
                        }
                       mAdapterVarites=new AdapterVarites(getBaseContext(),varitesArrayList,Activity_Variety.this);
                        mRecyclerViewVariety.setAdapter(mAdapterVarites);


                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }


                Log.e("dsafrefdfregrgsddfgryt",""+response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();

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
                params.put("product_id", productID);
                params.put("language", lang);
                return params;
            }

        };
        mRequestQueue.add(stringRequest);
        progressDialog=new ProgressDialog(Activity_Variety.this);
        progressDialog.setMessage("Please Wait....");
        progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
        progressDialog.show();

    }

    @Override
    public void varityDetails(int id) {
        Intent intent =new Intent(Activity_Variety.this,Activity_ProductDetails.class);
        intent.putExtra("VarDetail",varitesArrayList.get(id).getDetails());
        intent.putExtra("VarName",varitesArrayList.get(id).getVariety_name());
        intent.putExtra("VarImg",varitesArrayList.get(id).getImage());
        intent.putExtra("VarCul",cultivation);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
