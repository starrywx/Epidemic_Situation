package com.example.epidemicsituation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.example.epidemicsituation.R;
import com.example.epidemicsituation.bean.HistoryInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private List<HistoryInfo.DataBean> mDataBeanList;
    private AdapterItemClick adapterItemClick;
    GeocodeSearch geocodeSearch;
    private Context context;



    static class ViewHolder extends RecyclerView.ViewHolder {

        View view;
        @BindView(R.id.item_history_card_tv_time)
        TextView itemHistoryCardTvTime;
        @BindView(R.id.item_history_card_tv_location)
        TextView itemHistoryCardTvLocation;
        @BindView(R.id.item_history_card_tv_spot)
        TextView itemHistoryCardTvSpot;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, itemView);
        }
    }

    //构造器传入数据
    public HistoryAdapter(List<HistoryInfo.DataBean> dataBeanList, Context context) {
        this.mDataBeanList = dataBeanList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
//        viewHolder.view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int position = viewHolder.getAdapterPosition();
//                if(adapterItemClick != null){
//                    adapterItemClick.onClick(position);
//                }
//            }
//        });
        return viewHolder;
    }

    //对子项数据进行赋值
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (position < mDataBeanList.size()) {
            HistoryInfo.DataBean dataBean = mDataBeanList.get(position);
            ViewHolder viewHolder = (ViewHolder) holder;
            if(dataBean.getTime()!=null){
                viewHolder.itemHistoryCardTvTime.setText(dataBean.getTime());
            }
            if(dataBean.getLat()!=0&&dataBean.getLon()!=0){
                geocodeSearch = new GeocodeSearch(context);
                geocodeSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
                    @Override
                    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
                        RegeocodeAddress regeocodeAddress = regeocodeResult.getRegeocodeAddress();
                        String formatAddress = regeocodeAddress.getFormatAddress();
                        viewHolder.itemHistoryCardTvLocation.setText(formatAddress.substring(9));
                    }

                    @Override
                    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

                    }
                });
                RegeocodeQuery query = new RegeocodeQuery(new LatLonPoint(dataBean.getLat(),dataBean.getLon()),100,GeocodeSearch.AMAP);
                geocodeSearch.getFromLocationAsyn(query);
            }
            //根据获得的经度和纬度填入具体地点
//            viewHolder.itemHistoryTvProbability.setText((int) dataBean.getValue());
//            //设置时间
//            if(dataBean.getValue()<0.50){
//                viewHolder.itemHistoryTvTitle.setTextColor(Color.parseColor("#2DBF30"));
//                viewHolder.itemHistoryTvProbability.setTextColor(Color.parseColor("#10AD13"));
//                viewHolder.itemHistoryTvSuggestion.setText("感染风险较低");
//                viewHolder.itemHistoryTvSuggestion.setTextColor(Color.parseColor("#2DBF30"));
//            }else if(dataBean.getValue()<0.70){
//                viewHolder.itemHistoryTvTitle.setTextColor(Color.parseColor("#FA8048"));
//                viewHolder.itemHistoryTvProbability.setTextColor(Color.parseColor("#E44B04"));
//                viewHolder.itemHistoryTvSuggestion.setText("感染风险中等，请尽量避免去人群密集的地方");
//                viewHolder.itemHistoryTvSuggestion.setTextColor(Color.parseColor("#FA8048"));
//            }else{
//                viewHolder.itemHistoryTvTitle.setTextColor(Color.parseColor("#EA6F6F"));
//                viewHolder.itemHistoryTvProbability.setTextColor(Color.parseColor("#EB1F1F"));
//                viewHolder.itemHistoryTvSuggestion.setText("感染风险极高，请自行在家隔离");
//                viewHolder.itemHistoryTvSuggestion.setTextColor(Color.parseColor("#EA6F6F"));
//            }
        }
    }

    @Override
    public int getItemCount() {
        return mDataBeanList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void seOnItemClickListener(AdapterItemClick adapterItemClick) {
        this.adapterItemClick = adapterItemClick;
    }
}
