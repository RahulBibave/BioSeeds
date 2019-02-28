package kumarworld.rahul.kumarbioseeds.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import kumarworld.rahul.kumarbioseeds.R;
import kumarworld.rahul.kumarbioseeds.interfaces.MyInterface;
import kumarworld.rahul.kumarbioseeds.model.SuccessStories;

public class Adapter_SuccessStories extends RecyclerView.Adapter<Adapter_SuccessStories.ViewHolderSuccess>{
     MyInterface movetoNext;
     ArrayList<SuccessStories>successStories;
     Context context;


    public Adapter_SuccessStories(MyInterface movetoNext, ArrayList<SuccessStories> successStories, Context context) {
        this.movetoNext = movetoNext;
        this.successStories = successStories;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderSuccess onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.lay_success_stories_view,viewGroup,false);
        return new ViewHolderSuccess(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderSuccess viewHolderSuccess, final int i) {
        final SuccessStories success=successStories.get(i);
        String url=success.getmImages().get(0);

        viewHolderSuccess.txtName.setText(success.getmName());
        viewHolderSuccess.txtMsg.setText(Html.fromHtml(success.getmStory()));

        // Convert input string into a date


        try {
            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date  date = inputFormat.parse(success.getmCreated_at());

            // Format date into output format
            DateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
            String outputString = outputFormat.format(date);
            viewHolderSuccess.txtDate.setText(outputString);
        } catch (ParseException e) {
            e.printStackTrace();
        }



        viewHolderSuccess.txtAddress.setText(success.getmAddress()+","+success.getmCity());
        viewHolderSuccess.progressBar.setVisibility(View.VISIBLE);
        Picasso.with(context).load(url).skipMemoryCache().into(viewHolderSuccess.success_img, new Callback() {
            @Override
            public void onSuccess() {
                viewHolderSuccess.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
                viewHolderSuccess.progressBar.setVisibility(View.GONE);
            }
        });

        viewHolderSuccess.lin_success.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               movetoNext.foo(i);

            }
        });


    }

    @Override
    public int getItemCount() {
        return successStories.size();
    }

    public class ViewHolderSuccess extends RecyclerView.ViewHolder{
        ImageView success_img;
        TextView txtDate,txtMsg,txtAddress,txtName;
        LinearLayout lin_success;
        ProgressBar progressBar;
        public ViewHolderSuccess(@NonNull View itemView) {
            super(itemView);
            success_img=itemView.findViewById(R.id.success_img);
            txtAddress=itemView.findViewById(R.id.txtAddress);
            txtDate=itemView.findViewById(R.id.txtDate);
            txtMsg=itemView.findViewById(R.id.txtMsg);
            txtName=itemView.findViewById(R.id.txtName);
            lin_success=itemView.findViewById(R.id.lin_success);
            progressBar=itemView.findViewById(R.id.loadings);
        }
    }

}
