package com.example.epidemicsituation.ui.history;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.epidemicsituation.R;
import com.example.epidemicsituation.adapter.AdapterItemClick;
import com.example.epidemicsituation.adapter.HistoryAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HistoryActivity extends AppCompatActivity {
    @BindView(R.id.activity_history_back)
    ImageView activityHistoryBack;
    @BindView(R.id.history_rv)
    RecyclerView historyRv;
    private HistoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ButterKnife.bind(this);
        adapter.seOnItemClickListener(new AdapterItemClick() {
            @Override
            public void onClick(int position) {
                //点击跳转到地图页显示详情

            }
        });
    }

    @OnClick(R.id.activity_history_back)
    public void onViewClicked() {
        //返回地图页
    }
}
