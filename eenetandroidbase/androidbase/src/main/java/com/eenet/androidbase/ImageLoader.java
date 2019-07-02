package com.eenet.androidbase;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.eenet.androidbase.widget.imageloader.config.ImageLoaderConfig;
import com.eenet.androidbase.widget.imageloader.glide.transformation.GlideCircleTransform;
import com.eenet.androidbase.widget.imageloader.glide.transformation.GlideRoundImage;

import jp.wasabeef.glide.transformations.BlurTransformation;


/**
 * 图片加载框架,插拔式设计,可自由配置具体加载框架
 */
public class ImageLoader {

    private static ImageLoaderConfig loderConfig;

    /**
     * 初始化,推荐在Application中调用(仅赋值操作,不会阻塞线程)
     *
     * @param config
     */
    public static void init(ImageLoaderConfig config) {
        if (loderConfig != null) {
            return;
        }
        if (config == null)
            throw new IllegalArgumentException("ImageLoader初始化出错: ImageLoderConfig不能为null");
        if (config.getLoderClient() == null)
            throw new IllegalArgumentException("ImageLoader初始化出错: ImageLoderClient不能为null");
        loderConfig = config;
    }

    /**
     * 加载图片
     * 占位图和错误图为统一配置的图片
     *
     * @param imageView
     * @param url
     */
    public static void load(String url, ImageView imageView) {
        String newUrl = loderConfig.getInterceptor().InterceptorUrl(url);//url拦截变换
        loderConfig.getLoderClient().loadImage(imageView, newUrl, loderConfig.getPlacePicRes(),
                loderConfig.getErrorPicRes());

    }

    /**
     * 加载图片,过程中展示缩略图
     * 占位图和错误图为统一配置的图片
     *
     * @param imageView
     * @param url
     * @param thumbnail 缩略图栈原图的比例:float类型,0到1
     */
    public static void load(String url, ImageView imageView, float thumbnail) {
        String newUrl = loderConfig.getInterceptor().InterceptorUrl(url);//url拦截变换
        loderConfig.getLoderClient().loadImage(imageView, newUrl, loderConfig.getPlacePicRes(),
                loderConfig.getErrorPicRes(), thumbnail);

    }

    public static void load(String url, ImageView view,int place,int err) {
        Glide.with(BaseApplication.getContext())
                .load(url)
                .dontAnimate()
                .centerCrop()
                .placeholder(place)
                .error(err)
                .into(view);
    }

    public static void withoutCrop(String url, ImageView view,int place,int err) {
        Glide.with(BaseApplication.getContext())
                .load(url)
                .dontAnimate()
                .placeholder(place)
                .error(err)
                .into(view);
    }


    /**
     * 加载gif图
     * @param context
     * @param resourceId
     * @param imageView
     */
    public static void load(Context context,int resourceId, ImageView imageView) {
        Glide.with(context)
                .load(resourceId)
                .asGif()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);

    }

    public static void loadRoundImage(Context context,String url,ImageView imageView,int place,int fail,int radius){
        Glide.with(context).load(url).placeholder(place).error(fail).bitmapTransform(new CenterCrop(context),(new GlideRoundImage(context,radius,0))).into(imageView);
    }

    public static void loadRoundImage(Context context,String url,ImageView imageView,int radius){
        loadRoundImage(context,url,imageView,loderConfig.getPlacePicRes(),
                loderConfig.getErrorPicRes(), radius);
    }

    public static void loadRoundImage(String url,ImageView imageView,int place,int fail){
        Glide.with(BaseApplication.getContext()).load(url).placeholder(place).error(fail).bitmapTransform(new GlideCircleTransform(BaseApplication.getContext())).into(imageView);
    }

    public static void loadRoundImage(String url,ImageView imageView){
        Glide.with(BaseApplication.getContext()).load(url).bitmapTransform(new GlideCircleTransform(BaseApplication.getContext())).into(imageView);
    }

    public static void load(String url, ImageView view,int place) {
        Glide.with(BaseApplication.getContext())
                .load(url)
                .centerCrop()
                .placeholder(place)
                .error(place)
                .into(view);
    }

    public static void load(String url, ImageView view,int place,int width,int height) {
        Glide.with(BaseApplication.getContext())
                .load(url)
                .fitCenter()
                .override(width,height)
                .placeholder(place)
                .error(place)
                .into(view);
    }

    public static void loadFit(String url, ImageView view,int place) {
        Glide.with(BaseApplication.getContext())
                .load(url)
                .fitCenter()
                .placeholder(place)
                .error(place)
                .into(view);
    }

    public static void loadTopRoundImage(Context context,String url,ImageView imageView,int radius){
        Glide.with(context).load(url).placeholder(loderConfig.getPlacePicRes()).error(loderConfig.getErrorPicRes()).bitmapTransform(new CenterCrop(context),(new GlideRoundImage(context,radius,0, GlideRoundImage.CornerType.TOP))).into(imageView);
    }

    public static void loadBlurBackground(Context context,ImageView view,String url,int place){
        Glide.with(context)
                .load(url)
                .bitmapTransform(new BlurTransformation(context,14,3))
                .placeholder(place)
                .error(place)
                .into(view);
    }


    public static void loadBlurBackground(Context context,ImageView view,int resourceId,int place){
        Glide.with(context)
                .load(resourceId)
                .bitmapTransform(new BlurTransformation(context,14,3))
                .placeholder(place)
                .error(place)
                .into(view);
    }

}  