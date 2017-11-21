package com.wangkeke.doutuhelp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.wangkeke.doutuhelp.R;
import com.wangkeke.doutuhelp.bean.FoldData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangkeke on 2017/11/21.
 */

public class MyImageAdapter extends RecyclerView.Adapter<MyImageAdapter.MyAdapterViewHolder> {

    private List<FoldData> listData = new ArrayList<>();

    private Context context;

    public MyImageAdapter(Context context) {
        this.context = context;
    }

    @Override
    public MyAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_image_fold, parent, false);
        MyAdapterViewHolder vh = new MyAdapterViewHolder(v, true);
        return vh;
    }

    @Override
    public void onBindViewHolder(final MyAdapterViewHolder holder, int position) {

        FoldData data = listData.get(position);

        holder.title.setText(data.getTitle());
        holder.date.setText(data.getDate());
        Glide.with(context).load(data.getImgUrl()).asBitmap().centerCrop().into(new BitmapImageViewTarget(holder.image) {

            @Override
            public void onLoadStarted(Drawable placeholder) {
                holder.image.setImageDrawable(context.getResources().getDrawable(R.mipmap.timeline_image_failure));
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                holder.image.setImageDrawable(context.getResources().getDrawable(R.mipmap.timeline_image_failure));
            }

            @Override
            protected void setResource(Bitmap resource) {
                holder.image.setImageBitmap(resource);
            }
        });
    }

    @Override
    public int getItemCount() {
        return null == listData ? 0 : listData.size();
    }

    public void setData(List<FoldData> listData) {
        this.listData = listData;
        notifyDataSetChanged();
    }

    public class MyAdapterViewHolder extends RecyclerView.ViewHolder {

        public CardView rootView;
        public ImageView image;
        public TextView title, date;

        public MyAdapterViewHolder(View itemView, boolean isItem) {
            super(itemView);
            if (isItem) {
                rootView = itemView
                        .findViewById(R.id.rootView);
                image = itemView.findViewById(R.id.image);
                title = itemView.findViewById(R.id.title);
                date = itemView.findViewById(R.id.date);
            }

        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

}
