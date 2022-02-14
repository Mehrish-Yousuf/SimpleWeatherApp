package com.test.simpleweatherapp.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.test.simpleweatherapp.R;
import com.test.simpleweatherapp.data.DailyWeatherDetail;
import com.test.simpleweatherapp.services.ImageDownloadService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DailyRecyclerViewAdapter extends RecyclerView.Adapter<DailyRecyclerViewAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView topTextView;
        public TextView bottomTextView;
        public TextView rightTextView;


        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            topTextView = (TextView) itemView.findViewById(R.id.topTextView);
            bottomTextView = (TextView) itemView.findViewById(R.id.bottomTextView);
            rightTextView = (TextView) itemView.findViewById(R.id.rightTextView);
        }
    }

    private List<DailyWeatherDetail> dailyWeatherDetails;

    public DailyRecyclerViewAdapter(List<DailyWeatherDetail> dailyWeatherDetails) {
        this.dailyWeatherDetails = dailyWeatherDetails;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public DailyRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.daily_recycler_view_adapter_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder

    @Override
    public void onBindViewHolder(DailyRecyclerViewAdapter.ViewHolder holder, int position) {
        DailyWeatherDetail dailyWeatherDetail = dailyWeatherDetails.get(position);
        //set temp
        holder.rightTextView.setText("" + dailyWeatherDetail.temp.day + "° " + dailyWeatherDetail.temp.min + "°");
        //set description
        holder.bottomTextView.setText(dailyWeatherDetail.weather.get(0).desc);
        //set date
        Date date=new Date( dailyWeatherDetail.dt * 1000);
        SimpleDateFormat df = new SimpleDateFormat("EEE, MMM dd");
        String dateText = df.format(date);
        holder.topTextView.setText(dateText);
        // set image
        if (!dailyWeatherDetail.weather.isEmpty())
            ImageDownloadService.getInstance().downloadImage(holder.imageView,
                    dailyWeatherDetail.weather.get(0).icon);
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return dailyWeatherDetails.size();
    }
}
