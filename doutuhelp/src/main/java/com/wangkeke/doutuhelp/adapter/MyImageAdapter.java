package com.wangkeke.doutuhelp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.wangkeke.doutuhelp.R;
import com.wangkeke.doutuhelp.bean.FoldData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangkeke on 2017/11/21.
 */

public class MyImageAdapter extends BaseRecyclerAdapter<MyImageAdapter.MyAdapterViewHolder> {

    private List<FoldData> listData = new ArrayList<>();

    private Context context;

    private OnItemClickListener listener;

    public MyImageAdapter(Context context) {
        this.context = context;
    }

    @Override
    public MyAdapterViewHolder getViewHolder(View view) {
        return new MyAdapterViewHolder(view,false);
    }

    @Override
    public MyAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_image_fold, parent, false);
        MyAdapterViewHolder vh = new MyAdapterViewHolder(v, true);
        return vh;
    }

    @Override
    public void onBindViewHolder(final MyAdapterViewHolder holder, final int position, boolean isItem) {
        FoldData data = listData.get(position);

        Glide.with(context).load(data.getImgUrl()).asBitmap().into(new BitmapImageViewTarget(holder.image) {

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

        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(position,holder.image);
            }
        });

    }

    @Override
    public int getAdapterItemViewType(int position) {
        return 0;
    }

    @Override
    public int getAdapterItemCount() {
        return null == listData ? 0 : listData.size();
    }

    public void setData(List<FoldData> listData) {
        this.listData = listData;
        notifyDataSetChanged();
    }

    public void insert(FoldData foldData, int position) {
        insert(listData, foldData, position);
    }

    public void remove(int position) {
        remove(listData, position);
    }

    public void clear() {
        clear(listData);
    }

    public class MyAdapterViewHolder extends RecyclerView.ViewHolder {

        public CardView rootView;
        public ImageView image;
        public Button button;

        public MyAdapterViewHolder(View itemView, boolean isItem) {
            super(itemView);
            if (isItem) {
                rootView = itemView
                        .findViewById(R.id.rootView);
                image = itemView.findViewById(R.id.image);
                button = itemView.findViewById(R.id.button);
            }

        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position,ImageView image);
    }

    public void setOnSetImageClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

}
