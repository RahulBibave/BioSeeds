package kumarworld.rahul.kumarbioseeds.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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

import java.util.ArrayList;

import kumarworld.rahul.kumarbioseeds.R;
import kumarworld.rahul.kumarbioseeds.activity.Activity_Variety;
import kumarworld.rahul.kumarbioseeds.model.Product;

public class Adapter_Product extends RecyclerView.Adapter<Adapter_Product.ViewHolderProduct> {
    private Context mContext;
    private ArrayList<Product>mProductArrayList;

    public Adapter_Product(Context mContext, ArrayList<Product> mProductArrayList) {
        this.mContext = mContext;
        this.mProductArrayList = mProductArrayList;
    }

    @NonNull
    @Override
    public ViewHolderProduct onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.lay_product_view,viewGroup,false);
        ViewHolderProduct viewHolderProduct=new ViewHolderProduct(view);
        return viewHolderProduct;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderProduct viewHolderProduct, final int i) {
        viewHolderProduct.spinner.setVisibility(View.VISIBLE);
        final Product product=mProductArrayList.get(i);
        viewHolderProduct.txtProductName.setText(product.getmProName());
        Picasso.with(mContext).load(product.getmProImage()).skipMemoryCache().into(viewHolderProduct.imgProduct, new Callback() {
            @Override
            public void onSuccess() {
                viewHolderProduct.spinner.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
                viewHolderProduct.spinner.setVisibility(View.GONE);
            }
        });


        viewHolderProduct.linearContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext,Activity_Variety.class);
                intent.putExtra("productID",product.getmProID());
                intent.putExtra("cultivation",product.getmProCultivation());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mProductArrayList.size();
    }

    public class ViewHolderProduct extends RecyclerView.ViewHolder{
        LinearLayout linearContainer;
        TextView txtProductName;
        ImageView imgProduct;
        ProgressBar spinner;
        public ViewHolderProduct(@NonNull View itemView) {
            super(itemView);
            linearContainer=itemView.findViewById(R.id.linearContainer);
            txtProductName=itemView.findViewById(R.id.txt_titleProduct);
            imgProduct=itemView.findViewById(R.id.img_product);
            spinner = itemView.findViewById(R.id.loadings);
        }
    }
}
