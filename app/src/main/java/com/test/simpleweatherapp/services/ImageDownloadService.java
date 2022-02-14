package com.test.simpleweatherapp.services;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ImageDownloadService {
    private static String Image_Base_URL = "https://openweathermap.org/img/wn/";

    private static ImageDownloadService instance;
    private ImageDownloadService() {
    }

    public static ImageDownloadService getInstance() {
        if (instance == null)
            instance = new ImageDownloadService();
        return instance;
    }

    public void downloadImage(ImageView imageView, String iconName) {
        String imageURL = new StringBuilder()
                .append(Image_Base_URL)
                .append(iconName)
                .append("@2x.png")
                .toString();

        Picasso.get().load(imageURL)
//                .resize(50, 50)
//                .centerCrop()
                .into(imageView);
    }
}
