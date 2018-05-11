package com.example.imageloader.imagecache;

import android.graphics.Bitmap;

/**
 * 类描述：缓存接口
 * 创建人：maimanchuang
 * 创建时间：2018/5/11 1:02
 */
public interface ImageCache {
    public void put(String url, Bitmap bitmap);

    public Bitmap get(String url) ;
}
