package com.example.lokeshsoni.dashboard;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.intrusoft.scatter.ChartData;
import com.intrusoft.scatter.PieChart;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adi on 30/03/18.
 */

public class RatingAdapter extends RecyclerView.Adapter<RatingAdapter.MyViewHolder> {

    List<Rating> ratingList;
    Context context;

    public RatingAdapter(List<Rating> ratingList,Context context) {
        this.ratingList = ratingList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_rating, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Rating rating = ratingList.get(position);
        PieChart pieChart = holder.pieChart;
        TextView paramTv = holder.paramTv;
        paramTv.setText(rating.getName());
        pieChart.setAboutChart(""+rating.getPositive());
        List<ChartData> data = new ArrayList<>();
        data.add(new ChartData("", rating.getPercentage(),context.getResources().getColor(R.color.white),context.getResources().getColor(R.color.red_500)));
        data.add(new ChartData("", 100-rating.getPercentage(),context.getResources().getColor(R.color.white),context.getResources().getColor(R.color.green_700)));     //ARGS-> (display text, percentage)
        pieChart.setChartData(data);

    }

    @Override
    public int getItemCount() {
        return ratingList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        public PieChart pieChart;
        public TextView paramTv;
        public MyViewHolder(View itemView) {
            super(itemView);
            pieChart = (PieChart)itemView.findViewById(R.id.pie_chart);
             paramTv = (TextView)itemView.findViewById(R.id.paramNameTv);

        }
    }

}
