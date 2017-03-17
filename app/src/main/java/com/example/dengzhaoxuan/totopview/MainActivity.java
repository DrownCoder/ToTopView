package com.example.dengzhaoxuan.totopview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyview;
    private List<String> mDatas;
    private HomeAdapter mAdapter;
    private int mScrollY = 0;

    private ToTopView mTopView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    private void initView() {
        mRecyview = (RecyclerView) findViewById(R.id.rv_view);
        mRecyview.setLayoutManager(new LinearLayoutManager(this));
        mRecyview.setAdapter(mAdapter = new HomeAdapter());

        mTopView = (ToTopView) findViewById(R.id.btn_to_top);
        mTopView.setRecyclerView(mRecyview);
    }


    protected void initData() {
        mDatas = new ArrayList<String>();
        for (int j = 0; j < 10; j++) {
            for (int i = 'A'; i < 'z'; i++) {
                mDatas.add("" + (char) i);
            }
        }
    }

    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    MainActivity.this).inflate(R.layout.item_home, parent,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.tv.setText(mDatas.get(position));
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            TextView tv;

            public MyViewHolder(View view) {
                super(view);
                tv = (TextView) view.findViewById(R.id.id_num);
            }
        }
    }

}

