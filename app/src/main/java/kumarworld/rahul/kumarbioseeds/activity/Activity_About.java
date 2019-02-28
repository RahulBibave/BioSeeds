package kumarworld.rahul.kumarbioseeds.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import kumarworld.rahul.kumarbioseeds.R;


public class Activity_About extends AppCompatActivity {

    private ImageView img_arrowback;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        img_arrowback=findViewById(R.id.img_arrowback);

        img_arrowback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
