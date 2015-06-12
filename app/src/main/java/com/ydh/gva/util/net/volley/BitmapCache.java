package com.ydh.gva.util.net.volley;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.ydh.gva.util.net.volley.toolbox.ImageLoader.ImageCache;

import gva.ydh.com.util.AppLog;

/**
 * Created by liujianying on 15/5/25.
 */
public class BitmapCache implements ImageCache {
    // 如果想让整个项目共用一个图片缓存,那么这里可以将mCache设置成静态
    private LruCache<String, Bitmap> mCache;

    public BitmapCache() {
        if (mCache == null) {
            // 分配10M的缓存空间
            int maxSize = 10 * 1024 * 1024;
            mCache = new LruCache<String, Bitmap>(maxSize) {
                @Override
                protected int sizeOf(String key, Bitmap value) {
                    return value.getRowBytes() * value.getHeight();
                }
            };
        }
    }

    @Override
    public Bitmap getBitmap(String url) {
        return mCache.get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        mCache.put(url, bitmap);
        AppLog.d(getClass().getSimpleName(), "cacheSize/maxSize:" + mCache.size() + "/" + mCache.maxSize());
    }
}
