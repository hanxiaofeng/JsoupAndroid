package com.wangkeke.doutuhelp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.wangkeke.doutuhelp.adapter.MyImageAdapter;
import com.wangkeke.doutuhelp.bean.FoldData;
import com.wangkeke.doutuhelp.data.JsoupUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * home page
 */
public class MainActivity extends AppCompatActivity {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.activity_main)
    LinearLayout activityMain;
    private MyImageAdapter adapter;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            List<FoldData> listData = (List<FoldData>) msg.obj;
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
//        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        JsoupUtils.newInstance(handler).getImageFoldByUrl("http://sj.enterdesk.com/");
    }
}
