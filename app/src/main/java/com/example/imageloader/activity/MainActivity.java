package com.example.imageloader.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.imageloader.ImageLoader;
import com.example.imageloader.R;
import com.example.imageloader.imagecache.DoubleCache;

public class MainActivity extends AppCompatActivity {
ImageView imageView;
Button button;
    ImageLoader imageLoader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         imageLoader=new ImageLoader();
        imageLoader.setmImageCache(new DoubleCache(this));
        imageView=  findViewById(R.id.image);
        button= findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageLoader.displayImage("http://pic23.nipic.com/20120830/7103350_081925405000_2.jpg",imageView);
            }
        });

    }
}
