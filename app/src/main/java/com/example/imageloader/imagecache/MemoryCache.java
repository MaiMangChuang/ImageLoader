package com.example.imageloader.imagecache;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

/**
 * 类描述：内存缓存类
 * 创建人：maimanchuang
 * 创建时间：2018/5/10 8:41
 */
public class MemoryCache implements ImageCache {
  private   LruCache<String, Bitmap> mImageCache;

    public MemoryCache() {
        initImageCache();
    }

    private void initImageCache() {
        //计算可使用的最大内存
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        //取四分之一的可用内存作为缓存
        final int cacheSize = maxMemory / 4;
        mImageCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight() / 1024;
            }
        };

    }

    public void put(String url, Bitmap bitmap) {
        mImageCache.put(url, bitmap);
    }

    public Bitmap get(String url) {
        Log.e("TAG", "从内存加载" );
        return mImageCache.get(url);
    }
}
