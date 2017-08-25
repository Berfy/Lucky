package cn.berfy.demo.lucky;

import android.app.Application;

import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import cn.berfy.demo.lucky.db.DBHelper;
import cn.berfy.demo.lucky.db.DbConstants;
import cn.berfy.framework.utils.ToastUtil;

/**
 * Created by Berfy on 2017/8/23.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DBHelper.init(getApplicationContext(), 1, DbConstants.DB_NAME);
        ToastUtil.init(getApplicationContext());
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .memoryCacheExtraOptions(480, 800)
                .threadPriority(Thread.NORM_PRIORITY - 1) // default
                .tasksProcessingOrder(QueueProcessingType.FIFO) // default
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024)
                .memoryCacheSizePercentage(13) // default
                .imageDownloader(new BaseImageDownloader(getApplicationContext())) // default
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
                .writeDebugLogs().build();
        ImageLoader.getInstance().init(configuration);
    }
}
