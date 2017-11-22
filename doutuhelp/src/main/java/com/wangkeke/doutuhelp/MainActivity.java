package com.wangkeke.doutuhelp;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wangkeke.doutuhelp.adapter.MyImageAdapter;
import com.wangkeke.doutuhelp.bean.FoldData;
import com.wangkeke.doutuhelp.data.JsoupUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 已废弃，首页请查看HomeActivity
 */
public class MainActivity extends AppCompatActivity implements MyImageAdapter.OnItemClickListener {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.activity_main)
    LinearLayout activityMain;
    private MyImageAdapter adapter;

    private List<FoldData> listAllData = null;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            swipeRefreshLayout.setRefreshing(false);
            List<FoldData> listData = (List<FoldData>) msg.obj;
            listAllData = new ArrayList<>();
            listAllData.addAll(listData);
            adapter.setData(listData);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        adapter = new MyImageAdapter(MainActivity.this);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setOnSetImageClickListener(this);
        JsoupUtils.newInstance(handler).getImageFoldByUrl(1);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                JsoupUtils.newInstance(handler).getImageFoldByUrl(1);
            }
        });
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onItemClick(int position, ImageView image) {
        FoldData foldData = listAllData.get(position);

        Intent intent = new Intent(this, ImageInfoActivity.class);
        intent.putExtra("imagedata", foldData);
        if (android.os.Build.VERSION.SDK_INT > 20) {
            //5.0后支持共享动画
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this, image, "transitionImg").toBundle());
        } else {
            startActivity(intent);
        }
    }
}
