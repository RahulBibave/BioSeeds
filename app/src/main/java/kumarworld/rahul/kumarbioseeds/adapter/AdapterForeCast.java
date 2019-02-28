package kumarworld.rahul.kumarbioseeds.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import kumarworld.rahul.kumarbioseeds.R;
import kumarworld.rahul.kumarbioseeds.model.Forecast;
import pl.droidsonroids.gif.GifImageView;

public class AdapterForeCast extends RecyclerView.Adapter<AdapterForeCast.ViewHolderForeCast>{

    private ArrayList<Forecast>mForecastArrayList;
    private Context mContext;


    public AdapterForeCast(ArrayList<Forecast> mForecastArrayList, Context mContext) {
        this.mForecastArrayList = mForecastArrayList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolderForeCast onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.lay_forecast,viewGroup,false);
        return new ViewHolderForeCast(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderForeCast viewHolderForeCast, int i) {
        Forecast forecast=mForecastArrayList.get(i);
        viewHolderForeCast.itemDate.setText(forecast.getmDay()+","+forecast.getmDate());
        int lowTemp= Integer.parseInt(forecast.getmLow());
        lowTemp=lowTemp-32;
        lowTemp=lowTemp*5;
        lowTemp=lowTemp/9;
        viewHolderForeCast.itemLow.setText("Lowest Temp : "+lowTemp+" °C");
        int highTemp= Integer.parseInt(forecast.getmHigh());
        highTemp=highTemp-32;
        highTemp=highTemp*5;
        highTemp=highTemp/9;
        viewHolderForeCast.itemHigh.setText("Highest Temp : "+highTemp+" °C");
        viewHolderForeCast.itemTemperature.setText(highTemp+" °C");
        viewHolderForeCast.itemHumidity.setText(forecast.getmText());
        String url="http://l.yimg.com/a/i/us/we/52/"+forecast.getmCode()+".gif";
        Picasso.with(mContext).load(url).into(viewHolderForeCast.imageView);


    }

    @Override
    public int getItemCount() {
        return mForecastArrayList.size();
    }

    public class ViewHolderForeCast extends RecyclerView.ViewHolder{
        TextView itemDate,itemLow,itemHigh,itemHumidity,itemTemperature;
        GifImageView imageView;
        public ViewHolderForeCast(@NonNull View itemView) {
            super(itemView);
            itemDate=itemView.findViewById(R.id.itemDate);
            itemLow=itemView.findViewById(R.id.itemLow);
            itemHigh=itemView.findViewById(R.id.itemHigh);
            itemHumidity=itemView.findViewById(R.id.itemHumidity);
            itemTemperature=itemView.findViewById(R.id.itemTemperature);
            imageView=itemView.findViewById(R.id.itemIcon);
        }
    }
}
