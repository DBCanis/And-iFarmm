package com.caniss.ifarmm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Recycler;

import com.squareup.picasso.Picasso;

import java.util.List;

public class PriceAdapter extends RecyclerView.Adapter <PriceAdapter.ImageViewHolder> {
    private Context mContext;
    private List<Price> mUploads;

    public PriceAdapter(Context context, List<Price> upload){
        mContext = context;
        mUploads = upload;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.price_recycler, parent,false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Price uploadCurrent = mUploads.get(position);
        Picasso.with(mContext).load(uploadCurrent.getmImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(holder.imageView);
        holder.tvProduct.setText(uploadCurrent.getmProduct());
        holder.tvPrice.setText(uploadCurrent.getmPrice());
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView tvProduct,tvPrice;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.v_img_upload);
            tvProduct = itemView.findViewById(R.id.product_view);
            tvPrice = itemView.findViewById(R.id.price_view);
        }
    }
}
