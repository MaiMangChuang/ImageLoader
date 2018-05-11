package com.example.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;

import com.example.imageloader.imagecache.ImageCache;
import com.example.imageloader.imagecache.MemoryCache;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 类描述：
 * 创建人：maimanchuang
 * 创建时间：2018/5/10 8:40
 */
public class ImageLoader {
    private ImageCache mImageCache =null ;


    public void setmImageCache(ImageCache mImageCache) {
        this.mImageCache = mImageCache;
    }

    //建立线程池，线程数量为cpu的数量
    private  ExecutorService mExecutorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    //
   private Handler mUiHandler = new Handler(Looper.getMainLooper());

   private Request creatRequest(URL url){
       Request request = new Request.Builder()
               .url(url)
               .build();
       return  request;
   }

    private void updateImageView(final ImageView imageView, final Bitmap bmp) {
        mUiHandler.post(new Runnable() {
            @Override
            public void run() {
                imageView.setImageBitmap(bmp);
            }
        });
    }

    //加载图片
    public void displayImage(final String url, final ImageView imageView) {
       if(mImageCache==null){
           //当用户未指定使用那类缓存时，默认让其使用内存缓存
           mImageCache=new MemoryCache();
       }
        final Bitmap bitmap = mImageCache.get(url);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            return;
        }
        imageView.setTag(url);
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = downloadImage(url);
                if (bitmap == null) {
                    //网络加载图片失败
                    return;
            }
                if (imageView.getTag().equals(url)) {
                    updateImageView(imageView, bitmap);

                }

                mImageCache.put(url, bitmap);
            }
        });

    }

    private Bitmap downloadImage(String imageUrl) {
        Bitmap bitmap = null;
        try {

            URL url = new URL(imageUrl);
//            final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            bitmap = BitmapFactory.decodeStream(conn.getInputStream());
//            conn.disconnect();
            OkHttpClient client = new OkHttpClient();
            Request request=  creatRequest(url);
             Response response = client.newCall(request).execute();
            bitmap = BitmapFactory.decodeStream(response.body().byteStream());
            Log.e("TAG", "从网络上加载" );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
