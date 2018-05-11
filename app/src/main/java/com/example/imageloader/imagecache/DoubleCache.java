package com.example.imageloader.imagecache;

import android.content.Context;
import android.graphics.Bitmap;

/**
 * 类描述：在内存和SD卡都进行图片缓存，双重缓存
 * 创建人：maimanchuang
 * 创建时间：2018/5/11 1:18
 */
public class DoubleCache implements ImageCache {
    private MemoryCache mMemoryCache=null;
    private DiskCache mDiskCache=null;

    public   DoubleCache(Context context){
        mMemoryCache=new MemoryCache();
        mDiskCache=new DiskCache(context);
    }

    @Override
    public void put(String url, Bitmap bitmap) {
        mMemoryCache.put(url,bitmap);
        mDiskCache.put(url,bitmap);
    }

    @Override
    public Bitmap get(String url) {
        Bitmap bitmap=mMemoryCache.get(url);
        if(bitmap==null){
            bitmap=mDiskCache.get(url);
        }
        return bitmap;
    }
}
