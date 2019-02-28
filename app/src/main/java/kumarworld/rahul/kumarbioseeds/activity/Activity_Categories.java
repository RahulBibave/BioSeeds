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
import android.widget.ProgressBar;

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
import kumarworld.rahul.kumarbioseeds.adapter.Adapter_Categories;
import kumarworld.rahul.kumarbioseeds.adapter.Adapter_Product;
import kumarworld.rahul.kumarbioseeds.interfaces.MyInterface;
import kumarworld.rahul.kumarbioseeds.model.Categories;
import kumarworld.rahul.kumarbioseeds.model.CommonUI;
import kumarworld.rahul.kumarbioseeds.model.Product;

public class Activity_Categories extends AppCompatActivity implements MyInterface {
    private ImageView mImgBack;

    private RecyclerView mRecyclerViewCategories;
    private Adapter_Categories mAdapterCategories;
    private RequestQueue requestQueue;
    private String url="http://app.kumarbioseeds.com/all-categories";

    private ArrayList<Categories>categoriesArrayList;

    private ArrayList<Product>productArrayList;
    private Adapter_Product mAdapterProduct;
    private SharedPreferences sharedpreferences;
    private String lang;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        sharedpreferences = getSharedPreferences(CommonUI.mypreference,
                Context.MODE_PRIVATE);
        lang=sharedpreferences.getString("langKey","english");
        mRecyclerViewCategories=findViewById(R.id.recyclerCate);
        mImgBack=findViewById(R.id.img_arrowback);



        mRecyclerViewCategories.setHasFixedSize(true);
        GridLayoutManager recycleLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        mRecyclerViewCategories.setLayoutManager(recycleLayoutManager);
        mRecyclerViewCategories.setItemAnimator(new DefaultItemAnimator());
        getCategories();

        mImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }





    private void getCategories(){
        requestQueue=Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.e("ttttttt",""+response);
                categoriesArrayList=new ArrayList<>();
                categoriesArrayList.clear();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    //  String msg=jsonObject.getString("message");
                    if (status==200){
                        JSONArray object=jsonObject.getJSONArray("data");


                        for (int i=0;i<object.length();i++){
                            JSONObject jsonObject1=object.getJSONObject(i);
                            String id=jsonObject1.getString("id");
                            String img_path=jsonObject1.getString("img_path");
                            String category_name=jsonObject1.getString("name");
                            Categories categories=new Categories(id,category_name,img_path);
                            categoriesArrayList.add(categories);
                        }
                        mAdapterCategories=new Adapter_Categories(getBaseContext(),categoriesArrayList,Activity_Categories.this);
                        mRecyclerViewCategories.setAdapter(mAdapterCategories);

                       /* Log.e("aaaaaaaaaaaaaaaaaaaaaaaa",""+id);
                        for (int i=0;i<object.length();i++){

                            //
                           //
                            Log.e("sssssssssssssssssssssssss",""+jsonObject1);
                        }
*/

                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                error.printStackTrace();


            }
        }) {
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


                Map<String,String> params = new Hashtable<String, String>();
                params.put("language",lang);


                //returning parameters
                return params;
            }

        };
        requestQueue.add(stringRequest);
        progressDialog=new ProgressDialog(Activity_Categories.this);
        progressDialog.setMessage("Please Wait....");
        progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
        progressDialog.show();


    }


    @Override
    public void foo(int i) {

    }

    @Override
    public void moveNext(String x) {
        Intent intent=new Intent(Activity_Categories.this,Activity_Product.class);
        intent.putExtra("CateID",x);
        startActivity(intent);

    }
}
