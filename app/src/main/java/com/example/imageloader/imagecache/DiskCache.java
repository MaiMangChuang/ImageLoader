package com.example.imageloader.imagecache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 类描述：SD卡缓存类
 * 创建人：maimanchuang
 * 创建时间：2018/5/11 1:02
 */
public class DiskCache implements ImageCache {
private File sdUrl;
    public   DiskCache(Context context){
        sdUrl= context.getExternalCacheDir();
        if(sdUrl!=null&&!sdUrl.exists()){
            sdUrl.mkdirs();
        }
    }

    @Override
    public void put(String url, Bitmap bitmap) {
        FileOutputStream fileOutputStream=null;
     String   imageName=  getImageName(url);
        try {
            File file=new File(sdUrl,imageName);
            Log.e("TAG", "file: "+file);
          if(!file.exists())  {
              file.createNewFile();
          }
            fileOutputStream=new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG,100,fileOutputStream);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("TAG",  e.toString());
        }finally {
            if(fileOutputStream!=null){
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public Bitmap get(String url) {
        String   imageName=  getImageName(url);
        Log.e("TAG", "从SD卡加载" );
        return BitmapFactory.decodeFile(sdUrl+"/"+imageName);
    }

    /**
     * 取得链接中图片的名字
     * @param url 图片的网络链接
     * @return 图片名字
     */
    private String getImageName(String url){
        String k[]=url.split("/");
       String  imageName=k[k.length-1];
        Log.e("imageName", imageName);
        return imageName;
    }
}
