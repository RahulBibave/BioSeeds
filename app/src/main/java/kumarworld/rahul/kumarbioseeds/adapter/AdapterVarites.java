package kumarworld.rahul.kumarbioseeds.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import kumarworld.rahul.kumarbioseeds.R;
import kumarworld.rahul.kumarbioseeds.interfaces.ProductDetails;
import kumarworld.rahul.kumarbioseeds.model.Varites;

public class AdapterVarites extends RecyclerView.Adapter<AdapterVarites.ViewHolderVarites>{
    private Context mContext;
    private ArrayList<Varites>mVaritesArrayList;
    private ProductDetails productDetails;

    public AdapterVarites(Context mContext, ArrayList<Varites> mVaritesArrayList, ProductDetails productDetails) {
        this.mContext = mContext;
        this.mVaritesArrayList = mVaritesArrayList;
        this.productDetails = productDetails;
    }

    @NonNull
    @Override
    public ViewHolderVarites onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.lay_varity_view,viewGroup,false);
        return new ViewHolderVarites(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderVarites viewHolderVarites, final int i) {
        Varites varites=mVaritesArrayList.get(i);
        viewHolderVarites.loadings.setVisibility(View.VISIBLE);
        viewHolderVarites.txtVarityName.setText(varites.getVariety_name());
        Picasso.with(mContext).load(varites.getImage()).skipMemoryCache().into(viewHolderVarites.imgVarity,new Callback() {
            @Override
            public void onSuccess() {
                viewHolderVarites.loadings.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
                viewHolderVarites.loadings.setVisibility(View.GONE);
            }
        });


        viewHolderVarites.linVarity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productDetails.varityDetails(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mVaritesArrayList.size();
    }


    public class ViewHolderVarites extends RecyclerView.ViewHolder{
        LinearLayout linVarity;
        ImageView imgVarity;
        TextView txtVarityName;
        ProgressBar loadings;
        public ViewHolderVarites(@NonNull View itemView) {
            super(itemView);
            linVarity=itemView.findViewById(R.id.linearvarity);
            imgVarity=itemView.findViewById(R.id.img_varity);
            txtVarityName=itemView.findViewById(R.id.txt_titleVarity);
            loadings=itemView.findViewById(R.id.loadings);
        }
    }
}
