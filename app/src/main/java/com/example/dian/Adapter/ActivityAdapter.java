package com.example.dian.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dian.R;
import com.example.dian.bean.ActivityBean;

import java.util.List;

/**
 * Created by Administrator on 2018/3/18.
 */

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ViewHolder> {

    private List<ActivityBean> mActivityList;
    private Context mContext;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView bigImage;
        TextView titleTvItem;
        TextView timeTvItem;
        TextView addressTvItem;
        ImageView smallImage;
        LinearLayout layoutItem;

        public ViewHolder(View itemView) {
            super(itemView);
            layoutItem = itemView.findViewById(R.id.layout_activity_fragment_item);
            bigImage = itemView.findViewById(R.id.img_big);
            titleTvItem = itemView.findViewById(R.id.title_tv_fragment_item);
            timeTvItem = itemView.findViewById(R.id.time_tv_fragment_item);
            addressTvItem = itemView.findViewById(R.id.address_tv_fragment_item);
            smallImage = itemView.findViewById(R.id.img_small);
        }
    }

    public ActivityAdapter(List<ActivityBean> mActivityList, Context context) {
        this.mActivityList = mActivityList;
        this.mContext = context;
    }

    @Override
    public ActivityAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_fragment_item,
                parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ActivityAdapter.ViewHolder holder, int position) {
        ActivityBean activity = mActivityList.get(position);
        holder.bigImage.setImageResource(activity.getImageId_big());
        holder.titleTvItem.setText(activity.getTitle());
        holder.timeTvItem.setText(activity.getTime());
        holder.addressTvItem.setText(activity.getAddress());
        holder.smallImage.setImageResource(activity.getImageId_small());
        holder.layoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "不要再点了，功能未实现ヾ(ﾟ∀ﾟゞ)", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mActivityList.size();
    }


}
