package com.wangkeke.doutuhelp;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.ImageView;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.wangkeke.doutuhelp.adapter.MyImageAdapter;
import com.wangkeke.doutuhelp.bean.FoldData;
import com.wangkeke.doutuhelp.data.JsoupUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * home page
 */
public class HomeActivity extends AppCompatActivity implements MyImageAdapter.OnItemClickListener {

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.xrefreshview)
    XRefreshView xRefreshView;
    private MyImageAdapter adapter;

    private List<FoldData> listAllData = new ArrayList<>();

    private int currentPage = 1;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            xRefreshView.stopRefresh();
            List<FoldData> listData = (List<FoldData>) msg.obj;
            listAllData.addAll(listData);

            if(currentPage == 1){
                adapter.setData(listData);
            }else {
                for (int i = 0; i < listData.size(); i++) {
                    adapter.insert(listData.get(i),adapter.getAdapterItemCount());
                }
            }
            //每页最多查询出16张图片壁纸
            if(listData.size() < 16){
                xRefreshView.setLoadComplete(true);
            }else {
                xRefreshView.stopLoadMore();
            }
            currentPage++;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        init();

//        initView();
    }

    private void init() {
        xRefreshView.setPullLoadEnable(true);
        recyclerView.setHasFixedSize(true);

        adapter = new MyImageAdapter(HomeActivity.this);
        // 设置静默加载模式
//		xRefreshView1.setSilenceLoadMore();
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        // 静默加载模式不能设置footerview
        recyclerView.setAdapter(adapter);
//        xRefreshView1.setAutoLoadMore(true);
        xRefreshView.setPinnedTime(1000);
        xRefreshView.setMoveForHorizontal(true);
        xRefreshView.setHideFooterWhenComplete(false);
        adapter.setCustomLoadMoreView(new XRefreshViewFooter(this));
//		xRefreshView1.setPullLoadEnable(false);
        //设置静默加载时提前加载的item个数
//		xRefreshView1.setPreLoadCount(2);
        adapter.setOnSetImageClickListener(this);

        xRefreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {

            @Override
            public void onRefresh(boolean isPullDown) {
                currentPage = 1;
                listAllData.clear();
                JsoupUtils.newInstance(handler).getImageFoldByUrl(currentPage);
            }

            @Override
            public void onLoadMore(boolean isSilence) {
                JsoupUtils.newInstance(handler).getImageFoldByUrl(currentPage);
            }
        });

        //数据来源：jsoup解析网页而来，仅学习用
        JsoupUtils.newInstance(handler).getImageFoldByUrl(currentPage);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onItemClick(int position, ImageView image) {
        FoldData foldData = listAllData.get(position);

        Intent intent = new Intent(this, ImageInfoActivity.class);
        intent.putExtra("imagedata", foldData);
        if (Build.VERSION.SDK_INT > 20) {
            //5.0后支持共享动画
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this, image, "transitionImg").toBundle());
        } else {
            startActivity(intent);
        }
    }
}
