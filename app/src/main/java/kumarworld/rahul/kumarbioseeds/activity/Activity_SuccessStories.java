package kumarworld.rahul.kumarbioseeds.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
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
import java.util.IllegalFormatCodePointException;
import java.util.Map;

import kumarworld.rahul.kumarbioseeds.R;
import kumarworld.rahul.kumarbioseeds.adapter.Adapter_SuccessStories;
import kumarworld.rahul.kumarbioseeds.fragment.Fragment_SuccessStories;
import kumarworld.rahul.kumarbioseeds.interfaces.MyInterface;
import kumarworld.rahul.kumarbioseeds.model.CommonUI;
import kumarworld.rahul.kumarbioseeds.model.SuccessStories;

public class Activity_SuccessStories extends AppCompatActivity implements MyInterface {

    private RecyclerView mRecyclerView;
    private Adapter_SuccessStories mAdapterSuccessStories;
    private RequestQueue mRequestQueue;
    private String mUrl="http://app.kumarbioseeds.com/success-stories";
    ArrayList<SuccessStories>successStories;
    FragmentTransaction fragmentTransaction;
    private SharedPreferences sharedpreferences;
    private String lang;
    private String cropID="";
    private ImageView mImgBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_stories);
        sharedpreferences = getSharedPreferences(CommonUI.mypreference,
                Context.MODE_PRIVATE);
        lang=sharedpreferences.getString("langKey","english");
        cropID=getIntent().getStringExtra("cropID");

        mImgBack=findViewById(R.id.img_arrowback);
        mImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        mRecyclerView=findViewById(R.id.recyclerStories);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        getSuccessStories();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void getSuccessStories(){
        mRequestQueue=Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, mUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                successStories=new ArrayList<>();
               try {
                   JSONObject jsonObject=new JSONObject(response);
                   int status=jsonObject.getInt("status");

                   Log.e("xxxxxxxxxxxxxxxxxxxxxxxxxx",""+response);

                   if (status==200){
                       JSONArray jsonArray=jsonObject.getJSONArray("data");
                       for (int i=0;i<jsonArray.length();i++){
                           JSONObject jsonObject1=jsonArray.getJSONObject(i);
                           String id=jsonObject1.getString("id");
                           String mobile_no=jsonObject1.getString("mobile_no");
                           String address = jsonObject1.getString("address");
                           String pin_code = jsonObject1.getString("pin_code");
                           String name = jsonObject1.getString("name");
                           String message = jsonObject1.getString("message");
                           String created_at = jsonObject1.getString("created_at");
                           JSONArray jsonArray1 = jsonObject1.getJSONArray("success_image");

                           ArrayList<String>imagesss=new ArrayList<>();
                           imagesss.clear();
                           for (int j = 0; j<jsonArray1.length(); j++){
                               String img=jsonArray1.getString(j);
                               imagesss.add(img);
                           }

                           SuccessStories success=new SuccessStories(name,message,address,"",created_at,pin_code,mobile_no,imagesss);

                           successStories.add(success);


                       }
                       Log.e("nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn",""+successStories.get(0).getmImages());
                       mAdapterSuccessStories=new Adapter_SuccessStories(Activity_SuccessStories.this,successStories,getBaseContext());
                       mRecyclerView.setAdapter(mAdapterSuccessStories);

                       Log.e("dasdadadadada",""+jsonArray);

                   }


               }catch (JSONException e){
                   e.printStackTrace();
               }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

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


                Map<String,String> params = new Hashtable<String, String>();

                if (cropID==null){
                    params.put("crop_id", "");
                }else {
                    params.put("crop_id", cropID);
                }
                params.put("language", lang);


                Log.e("dfwuiefyweffregr5g",""+cropID);





                //returning parameters
                return params;
            }
        };

        mRequestQueue.add(stringRequest);


    }

    @Override
    public void foo(int i) {
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("valuesArray",successStories.get(i).getmImages() );
        bundle.putString("msg",successStories.get(i).getmStory());
        bundle.putString("add",successStories.get(i).getmAddress());
        bundle.putString("city",successStories.get(i).getmCity());
        bundle.putString("mobile",successStories.get(i).getmMobile_no());
        bundle.putString("name",successStories.get(i).getmName());
        bundle.putString("created",successStories.get(i).getmCreated_at());


        Log.e("djjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj",""+successStories.get(i).getmImages());
        Fragment_SuccessStories myFragment = new Fragment_SuccessStories();
        myFragment.setArguments(bundle);
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_container, myFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void moveNext(String x) {

    }


}
