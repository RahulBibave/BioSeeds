package kumarworld.rahul.kumarbioseeds.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;

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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import kumarworld.rahul.kumarbioseeds.R;
import kumarworld.rahul.kumarbioseeds.model.CommonUI;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private CardView cardProduct;
    private LinearLayout linproduct;
    private TextView txtProduct;
    private CardView cardEnquiry;
    private TextView txtEnquiry;
    private CardView cardSucess;
    private TextView txtSuccess;
    private CardView cardWeather;
    private TextView txtWeather;
    private CardView cardTechSupport;
    private TextView txtTechSupport;
    private CardView cardAbout;
    private TextView txtAbout;
    private View navHeader;
    LinearLayout linear_main;
    private Toolbar toolbar;

    private NavigationView navigationView, navForCart;
    private DrawerLayout drawerLayout;
    SharedPreferences sharedpreferences;
    private RequestQueue mRequestQueue;
    private String mUrlgetSupport="http://app.kumarbioseeds.com/technical-support";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedpreferences = getSharedPreferences(CommonUI.mypreference,
                Context.MODE_PRIVATE);
        findViews();
        getSupportNo();
        cardProduct.setOnClickListener(this);
        cardEnquiry.setOnClickListener(this);
        cardSucess.setOnClickListener(this);
        cardTechSupport.setOnClickListener(this);
        cardWeather.setOnClickListener(this);
        cardAbout.setOnClickListener(this);


    }


    private void findViews() {
        cardProduct = findViewById(R.id.cardProduct);
        linproduct = findViewById(R.id.linproduct);
        txtProduct = findViewById(R.id.txtProduct);
        cardEnquiry = findViewById(R.id.cardEnquiry);
        txtEnquiry = findViewById(R.id.txtEnquiry);
        cardSucess = findViewById(R.id.cardSucess);
        txtSuccess = findViewById(R.id.txtSuccess);
        cardWeather = findViewById(R.id.cardWeather);
        txtWeather = findViewById(R.id.txtWeather);
        cardTechSupport = findViewById(R.id.cardTechSupport);
        txtTechSupport = findViewById(R.id.txtTechSupport);
        cardAbout = findViewById(R.id.cardAbout);
        txtAbout = findViewById(R.id.txtAbout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);


        navigationView.inflateMenu(R.menu.activity_home_drawer);
        navHeader = navigationView.inflateHeaderView(R.layout.lay_navigation_drawer_header);
        linear_main = (LinearLayout) findViewById(R.id.linear_main);
        setUpNavigationView();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cardProduct:
                final Intent intent = new Intent(MainActivity.this, Activity_Categories.class);
                startActivity(intent);
                break;

            case R.id.cardEnquiry:
                if (sharedpreferences.getString(CommonUI.Sstatus, "").equalsIgnoreCase("1")){
                    Intent intentEnq = new Intent(MainActivity.this, Activity_Enquiry.class);
                    startActivity(intentEnq);
                    break;
                }else {
                    final Dialog dialog = new Dialog(this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.setContentView(R.layout.layout_custom_alert_withoption);
                    TextView txtContent=dialog.findViewById(R.id.tvCustomAlertContent);
                    TextView txtTitle=dialog.findViewById(R.id.tvCustomAlertTitle);
                    txtTitle.setText("Kumar BioSeeds");
                    txtContent.setText("For Enquiry First You have to Register");

                    Button no =  dialog.findViewById(R.id.btnYes);
                    Button yes=dialog.findViewById(R.id.btnNo);

                    no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                            //  onBackPressed();
                            // finish();
                        }
                    });
                    yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                           Intent intent1=new Intent(MainActivity.this,Register_Activity.class);
                           startActivity(intent1);
                           finish();
                        }
                    });

                    dialog.show();



                }
                break;

            case R.id.cardSucess:
                Intent intentSuccess = new Intent(MainActivity.this, Activity_SuccessStories.class);
                startActivity(intentSuccess);
                break;

            case R.id.cardWeather:
                Intent intentWeather = new Intent(MainActivity.this, Activity_WetherReport.class);
                startActivity(intentWeather);
                break;

            case R.id.cardTechSupport:
                Intent intentTech = new Intent(MainActivity.this, Activity_TechSupport.class);
                startActivity(intentTech);
                break;
            case R.id.cardAbout:
                Intent intentAbout = new Intent(MainActivity.this, Activity_About.class);
                startActivity(intentAbout);
                break;
        }


    }


    private void getSupportNo() {
        mRequestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, mUrlgetSupport, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject=new JSONObject(response);
                    int Status=jsonObject.getInt("status");
                    if (Status==200){
                        JSONArray jsonArray=jsonObject.getJSONArray("data");
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject1=jsonArray.getJSONObject(0);
                            String smsNo=jsonObject1.getString("sms_no");
                            String whatsappNo =jsonObject1.getString("whatsapp_no");
                            CommonUI.MobileNo=smsNo;
                            CommonUI.WhatsappNo=whatsappNo;

                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

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


        };

        mRequestQueue.add(stringRequest);

    }


    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                drawerLayout.closeDrawers();
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;

                    case R.id.nav_home:
                        Intent intenSearch=new Intent(MainActivity.this,Activity_Product.class) ;
                        startActivity(intenSearch);

                        return true;

                    case R.id.nav_profile:
                        Intent intent=new Intent(MainActivity.this,Activity_UpdateProfile.class);
                        startActivity(intent);

                        return true;

                    case R.id.nav_lang:

                        Intent intenLang=new Intent(MainActivity.this,Activity_SelectLang.class);
                        startActivity(intenLang);

                        break;

                    case R.id.nav_serach_byCat:
                        Intent intent1=new Intent(MainActivity.this,Activity_Categories.class);
                        startActivity(intent1);
                        break;

                    case R.id.nav_shareapp:
                        shareApplication();
                        break;


                    case R.id.nav_logout:
                      SharedPreferences sharedpreferences = getSharedPreferences(CommonUI.mypreference,
                                Context.MODE_PRIVATE);



                      SharedPreferences.Editor editor = sharedpreferences.edit();
                      editor.putString(CommonUI.SName, "");
                      editor.putString(CommonUI.SEmail, "");
                      editor.putString(CommonUI.SMobile, "");
                      editor.putString(CommonUI.SCity, "");
                      editor.putString(CommonUI.Sstate, "");
                      editor.putString(CommonUI.Sstatus, "");
                      editor.commit();
                      // logoutApplication(getResources().getString(R.string.app_name),"Are You sure you want to Logout ?");


                        return true;

                    default:

                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                linear_main.setTranslationX(slideOffset * drawerView.getWidth());
                drawerLayout.bringChildToFront(drawerView);
                drawerLayout.requestLayout();
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();

    }




    private void shareApplication() {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);


        share.putExtra(Intent.EXTRA_SUBJECT, "Download Kumar BioSeed App");
        share.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=velociter.kumar.property");

        startActivity(Intent.createChooser(share, "Share link!"));

    }

}
