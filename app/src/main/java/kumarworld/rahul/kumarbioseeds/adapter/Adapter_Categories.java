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
import kumarworld.rahul.kumarbioseeds.interfaces.MyInterface;
import kumarworld.rahul.kumarbioseeds.model.Categories;

public class Adapter_Categories extends RecyclerView.Adapter<Adapter_Categories.ViewHolderCategories> {
    private Context mContext;
    private ArrayList<Categories>mCategoriesArrayList;
    private MyInterface myInterface;


    public Adapter_Categories(Context mContext, ArrayList<Categories> mCategoriesArrayList, MyInterface myInterface) {
        this.mContext = mContext;
        this.mCategoriesArrayList = mCategoriesArrayList;
        this.myInterface = myInterface;
    }

    @NonNull
    @Override
    public ViewHolderCategories onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.lay_category_view,viewGroup,false);
        ViewHolderCategories viewHolderCategories=new ViewHolderCategories(view);
        return viewHolderCategories;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderCategories viewHolderCategories, int i) {
        final Categories categories=mCategoriesArrayList.get(i);
        viewHolderCategories.loadings.setVisibility(View.VISIBLE);
        viewHolderCategories.txt_titleCate.setText(categories.getmCatName());
        Picasso.with(mContext).load(categories.getmCatImage()).skipMemoryCache().into(viewHolderCategories.imgCate,new Callback() {
            @Override
            public void onSuccess() {
                viewHolderCategories.loadings.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
                viewHolderCategories.loadings.setVisibility(View.GONE);
            }
        });


        viewHolderCategories.categoriesview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myInterface.moveNext(categories.getmCatID());

            }
        });

    }

    @Override
    public int getItemCount() {
        return mCategoriesArrayList.size();
    }

    public class ViewHolderCategories extends RecyclerView.ViewHolder {
        TextView txt_titleCate;
        ImageView imgCate;
        LinearLayout categoriesview;
        ProgressBar loadings;
        public ViewHolderCategories(@NonNull View itemView) {
            super(itemView);
            categoriesview=itemView.findViewById(R.id.categoriesview);
            txt_titleCate=itemView.findViewById(R.id.txt_titleCate);
            imgCate=itemView.findViewById(R.id.img_categies);
            loadings=itemView.findViewById(R.id.loadings);
        }
    }
}
