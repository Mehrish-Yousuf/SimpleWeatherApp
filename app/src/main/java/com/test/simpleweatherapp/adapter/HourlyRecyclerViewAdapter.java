package com.test.simpleweatherapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.test.simpleweatherapp.R;
import com.test.simpleweatherapp.data.DailyWeatherDetail;
import com.test.simpleweatherapp.data.HourlyWeatherDetail;
import com.test.simpleweatherapp.services.ImageDownloadService;

import java.util.List;

public class HourlyRecyclerViewAdapter extends RecyclerView.Adapter<HourlyRecyclerViewAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public TextView textView1;
        public ImageView imageView;
        public TextView textView2;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textView);
            textView1 = (TextView) itemView.findViewById(R.id.textView1);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            textView2 = (TextView) itemView.findViewById(R.id.textView2);
        }
    }

    private List<HourlyWeatherDetail> hourlyWeatherDetails;

    public HourlyRecyclerViewAdapter(List<HourlyWeatherDetail> hourlyWeatherDetails) {
        this.hourlyWeatherDetails = hourlyWeatherDetails;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public HourlyRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.hourly_recycler_view_adapter_item, parent, false);

        // Return a new holder instance
        HourlyRecyclerViewAdapter.ViewHolder viewHolder = new HourlyRecyclerViewAdapter.ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(HourlyRecyclerViewAdapter.ViewHolder holder, int position) {
        HourlyWeatherDetail hourlyWeatherDetail = hourlyWeatherDetails.get(position);
        holder.textView.setText("" + hourlyWeatherDetail.temp + "%");
        holder.textView1.setText("" + hourlyWeatherDetail.humidity + "°");
        holder.textView2.setText(hourlyWeatherDetail.weather.get(0).desc);

        if (!hourlyWeatherDetail.weather.isEmpty())
            ImageDownloadService.getInstance().downloadImage(holder.imageView,
                    hourlyWeatherDetail.weather.get(0).icon);
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return hourlyWeatherDetails.size();
    }
}
