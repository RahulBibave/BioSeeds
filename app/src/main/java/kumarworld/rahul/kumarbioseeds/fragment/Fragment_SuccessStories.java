package kumarworld.rahul.kumarbioseeds.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import kumarworld.rahul.kumarbioseeds.R;
import kumarworld.rahul.kumarbioseeds.adapter.ViewPagerAdapter;

public class Fragment_SuccessStories extends Fragment {


    private ArrayList<String>url;
    private String name,story,address,mobile,createdat;
    private TextView mTxtStory,mTxtName,mTxtAddress,mTxtMobile;
    private ImageView leftNav,rightNav,img_arrowback;
    ViewPager viewPager;

    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000; // time in milliseconds between successive task executions.


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.lay_success_details,container,false);
        url=new ArrayList<>();
        Bundle bundle = getArguments();
        url=bundle.getStringArrayList("valuesArray");
        name=bundle.getString("name","No Data Found");
        story=bundle.getString("msg","No Data Found");
        address=bundle.getString("add","No Data Found")+","+bundle.getString("city","No Data Found");
        mobile=bundle.getString("mobile","No Data Found");
        createdat=bundle.getString("created","No Data Found");
        findViews(view);



       // Log.e("vfjdgggggggggggggggggggggggggggggggggggggggggg",""+bundle.getString("msg","No Data Found"));
        viewPager=view.findViewById(R.id.viewPager);
        ViewPagerAdapter adapter=new ViewPagerAdapter(getContext(),url);
        viewPager.setAdapter(adapter);


        // Images right navigatin
        rightNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tab = viewPager.getCurrentItem();
                tab++;
                viewPager.setCurrentItem(tab);
            }
        });

        img_arrowback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        return view;
    }



    private void findViews(View view) {
        mTxtStory=view.findViewById(R.id.txtStory);
        mTxtAddress=view.findViewById(R.id.txtAddress);
        mTxtMobile=view.findViewById(R.id.txtMobileNo);
        mTxtName=view.findViewById(R.id.txtName);
        leftNav = view.findViewById(R.id.left_nav);
        rightNav =  view.findViewById(R.id.right_nav);
        img_arrowback=view.findViewById(R.id.img_arrowback);

        mTxtStory.setText(Html.fromHtml(story));
        mTxtName.setText(name);
        mTxtMobile.setText(mobile);
        mTxtAddress.setText(address);
    }
}
