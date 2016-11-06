package com.example.recyclerviewdemo;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private RecyclerView recyclerView;
    private ArrayList<String> data;
    private RecyclerViewAdapter recyclerViewAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        data = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            data.add("Data" + i);
        }
        // 设置适配器
        recyclerViewAdapter = new RecyclerViewAdapter(this, data);
        // 设置点击事件处理函数
        recyclerViewAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position, String data) {
                //Toast.makeText(MainActivity.this,"data:"+data,Toast.LENGTH_SHORT).show();

                Intent intent = new Intent();
                intent.setClass(MainActivity.this,DetailActivity.class);
                intent.putExtra("itemData",data);
                startActivity(intent);

            }
        });
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        /**
         * 设置布局管理器
         *  上下文
         *  方向  水平|垂直
         *  是否倒序
         */
        //线性布局
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this,
                LinearLayoutManager.VERTICAL, false));
        //表格布局
        //recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this,3));
        //
        //recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this,3));

        swipeRefreshLayout  =  (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        initSwipeRefresh();


    }
    // 初始化下拉控件
    private void initSwipeRefresh() {
        // 设置圈圈的颜色
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,android.R.color.holo_green_dark,android.R.color.holo_orange_light);
        // 设置刷新控件背景颜色
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(android.R.color.white));
        // 设置下拉距离
        swipeRefreshLayout.setDistanceToTriggerSync(100);
        //设置圈圈大小
        swipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);

        //设置下拉监听
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                //主线程调用
               new Handler().postDelayed(new Runnable() {
                   @Override
                   public void run() {
                       //停止刷新
                       swipeRefreshLayout.setRefreshing(false);
                       //添加数据
                       refreshData();
                       //刷新数据
                       recyclerViewAdapter.notifyItemRangeChanged(0,60);
                       // 跳到最新数据
                       recyclerView.scrollToPosition(0);
                   }
               },2000);
            }
        });
    }

    //刷新数据
    private void refreshData() {
        for (int i = 0; i < 60; i++) {
            data.add(0,"dataR"+i);
        }
    }


    public void btn_grid_click(View view) {
        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this,2));
    }

    public void btn_linear_click(View view) {

        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this,
                LinearLayoutManager.VERTICAL, false));
    }

    public void btn_add_data(View view) {
       recyclerViewAdapter.addData(0,"new data");
        recyclerView.scrollToPosition(0);
    }

    public void btn_remove_data(View view) {
         recyclerViewAdapter.removeData(0);
    }

}

