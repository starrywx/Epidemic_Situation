package com.example.epidemicsituation.ui.history;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.epidemicsituation.R;
import com.example.epidemicsituation.adapter.AdapterItemClick;
import com.example.epidemicsituation.adapter.HistoryAdapter;
import com.example.epidemicsituation.bean.HistoryInfo;
import com.example.epidemicsituation.ui.map.MapActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HistoryActivity extends AppCompatActivity implements HistoryContract.HistoryView{
    @BindView(R.id.activity_history_back)
    ImageView activityHistoryBack;
    @BindView(R.id.history_rv)
    RecyclerView historyRv;
    private List<HistoryInfo.DataBean> mDataBeanList = new ArrayList<>();
    private HistoryAdapter adapter;
    private HistoryContract.HistoryPresent present;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        present = new HistoryPresent();
        present.attachView(this);
        ButterKnife.bind(this);
        initList();
        present.getHistoryInfo();
//        adapter.seOnItemClickListener(new AdapterItemClick() {
//            @Override
//            public void onClick(int position) {
//                //点击跳转到地图页显示详情
//                Intent detailIntent = new Intent(HistoryActivity.this, MapActivity.class);
//                detailIntent.putExtra("flag","detail");
//                startActivity(detailIntent);
//            }
//        });
    }

    @OnClick(R.id.activity_history_back)
    public void onViewClicked() {
        //返回地图页
        finish();
    }

    private void initList(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        historyRv.setLayoutManager(layoutManager);
        present.getHistoryInfo();
    }

    @Override
    public void showHistoryList(List<HistoryInfo.DataBean> dataBeanList) {
        //数据来源暂未定
        mDataBeanList=dataBeanList;
        adapter=new HistoryAdapter(mDataBeanList,this);
        historyRv.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        present.detachView();
    }
}
