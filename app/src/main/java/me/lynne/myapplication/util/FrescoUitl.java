package me.lynne.myapplication.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.facebook.binaryresource.BinaryResource;
import com.facebook.binaryresource.FileBinaryResource;
import com.facebook.cache.common.CacheKey;
import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.internal.Supplier;
import com.facebook.common.references.CloseableReference;
import com.facebook.common.util.ByteConstants;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.cache.DefaultCacheKeyFactory;
import com.facebook.imagepipeline.cache.MemoryCacheParams;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.core.ImagePipelineFactory;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FrescoUitl {

    private Context context;
    public FrescoUitl(Context context){
        this.context = context;
    }

    private ImagePipelineConfig getConfigureCaches() {
        int MAX_MEM = 30 * ByteConstants.MB;
        final MemoryCacheParams bitmapCacheParams = new MemoryCacheParams(
                // 内存缓存中总图片的最大大小,以字节为单位。
                MAX_MEM,
                // 内存缓存中图片的最大数量。
                Integer.MAX_VALUE,
                // 内存缓存中准备清除但尚未被删除的总图片的最大大小,以字节为单位。
                MAX_MEM,
                // 内存缓存中准备清除的总图片的最大数量。
                Integer.MAX_VALUE,
                // 内存缓存中单个图片的最大大小。
                Integer.MAX_VALUE);

        Supplier<MemoryCacheParams> mSupplierMemoryCacheParams = new Supplier<MemoryCacheParams>() {
            @Override
            public MemoryCacheParams get() {
                return bitmapCacheParams;
            }
        };
        ImagePipelineConfig.Builder builder = ImagePipelineConfig.newBuilder(context);
        builder.setBitmapMemoryCacheParamsSupplier(mSupplierMemoryCacheParams);
        return builder.build();
    }

    public void frescoInit() {
        Fresco.initialize(context, getConfigureCaches());
    }

    public void saveImageFromUrlThread(final String url, final String path){
        new Thread(new Runnable() {
            @Override
            public void run() {
                saveImageFromUrl(url, path);
            }
        }).start();
    }

    private void saveImageFromUrl(String url, final String path) {
        Log.i("wxbnbb", "Url: " + url);
        Log.i("wxbnbb", "Url: " + path);
        final ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url))            .setProgressiveRenderingEnabled(true).build();

        DataSource<CloseableReference<CloseableImage>> dataSource = Fresco.getImagePipeline()
                .fetchDecodedImage(imageRequest, context);

        dataSource.subscribe(new BaseBitmapDataSubscriber() {

            @Override
            protected void onNewResultImpl(@Nullable Bitmap bitmap) {

                //形参的bitmap即Fresco缓存到内存的Bitmap
                saveBitmap(bitmap, path);
                //下面是获取fresco缓存到磁盘的cnt图片的路径
//                CacheKey cacheKey = DefaultCacheKeyFactory.getInstance()
//                        .getEncodedCacheKey(imageRequest, this);
//                BinaryResource resource = ImagePipelineFactory.getInstance().getMainFileCache().getResource(cacheKey);
//                Log.i("wxbnbb", "saveImageFromUrl: ");
//                if (resource == null) return;
//                File file = ((FileBinaryResource) resource).getFile();
//                if (file == null) return;
//                Log.i("wxbnbb", "saveImageFromUrl: " + file.getAbsolutePath());
            }

            @Override
            public void onFailureImpl(DataSource dataSource) {

            }
        }, CallerThreadExecutor.getInstance());
    }

    private void saveBitmap(Bitmap bitmap, String fileName) {
        Log.d("saveBitmap", fileName);
        File appDir = new File(Environment.getDataDirectory(), "图片文件夹");
        if (!appDir.exists()) appDir.mkdir();
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            //file.getAbsolutePath();//获取保存的图片的文件名
            //    onSaveSuccess(file);
        } catch (IOException e) {
            Log.i("save", "saveimage wrong");
            e.printStackTrace();
        }
    }
}
