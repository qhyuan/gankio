package com.walle.gankio.widget.glide;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;


import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.TransformationUtils;
import com.walle.gankio.utils.LogUtil;

public class GlideRoundTransform extends BitmapTransformation {


    // 边框颜色
    private int borderColor;
    // 边框宽度
    private int borderWidth;
    // 按下的透明度
    private int pressAlpha;
    // 按下的颜色
    private int pressColor;
    // 圆角半径
    private int radius;
    // 图片类型（矩形，圆形）
    private int shapeType;
    private Paint borderPaint;
    public GlideRoundTransform(Context context) {
        this(context, 16);
    }


    public GlideRoundTransform(Context context, int dp) {
        super(context);
        this.radius = (int) (Resources.getSystem().getDisplayMetrics().density * dp);
        init(context);
    }


    @Override protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        final Bitmap toReuse = pool.get(outWidth, outHeight, toTransform.getConfig() != null
                ? toTransform.getConfig() : Bitmap.Config.ARGB_4444);
        Bitmap transformed = TransformationUtils.centerCrop(toReuse, toTransform, outWidth, outHeight);
        if (toReuse != null && toReuse != transformed && !pool.put(toReuse)) {
            toReuse.recycle();
        }
        return roundCrop(pool,toTransform);
    }


    private  Bitmap roundCrop(BitmapPool pool, Bitmap source) {
        if (source == null) return null;

        Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);

        onDraw(canvas,source);
        return result;
    }
    private void init(Context context) {
        //初始化默认值
        borderWidth = 6;
        borderColor = 0x77ffffff;
        pressAlpha = 0x42;
        pressColor = 0x42000000;
        radius = 16;
        shapeType = 2;

        borderPaint = new Paint();
        borderPaint.setStrokeWidth(borderWidth);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setColor(borderColor);
        borderPaint.setAntiAlias(true);
/*
        setDrawingCacheEnabled(true);
        setWillNotDraw(false);*/
    }
    protected void onDraw(Canvas canvas,Bitmap bitmap) {
        drawDrawable(canvas, bitmap);
        drawBorder(canvas,bitmap.getWidth(),bitmap.getHeight());
    }


    /**
     * 实现圆角的绘制
     */
    private void drawDrawable(Canvas canvas, Bitmap bitmap) {
        // 画笔
        Paint paint = new Paint();
        // 颜色设置
        paint.setColor(0xffffffff);
        // 抗锯齿
        paint.setAntiAlias(true);
        //Paint 的 Xfermode，PorterDuff.Mode.SRC_IN 取两层图像的交集部门, 只显示上层图像。
        PorterDuffXfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
        // 标志
        int saveFlags = Canvas.MATRIX_SAVE_FLAG
                | Canvas.CLIP_SAVE_FLAG
                | Canvas.HAS_ALPHA_LAYER_SAVE_FLAG
                | Canvas.FULL_COLOR_LAYER_SAVE_FLAG
                | Canvas.CLIP_TO_LAYER_SAVE_FLAG;
        canvas.saveLayer(0, 0, bitmap.getWidth(), bitmap.getHeight(), null, saveFlags);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
//        int width =
        // 当ShapeType == 2 时 图片为圆角矩形 （这里在宽高上 -1 是为了不让图片超出边框）
        RectF rectf = new RectF(1, 1, width - 1, height - 1);
        canvas.drawRoundRect(rectf, radius + 1, radius + 1, paint);

        paint.setXfermode(xfermode);

        // 空间的大小 / bitmap 的大小 = bitmap 缩放的倍数
        float scaleWidth = ((float) width) / bitmap.getWidth();
        float scaleHeight = ((float) height) / bitmap.getHeight();
        LogUtil.i("bitmap w="+bitmap.getWidth()+", h= "+bitmap.getHeight());
        //1920 * 540

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        LogUtil.i("centerSquareScaleBitmap w="+bitmap.getWidth()+", h= "+bitmap.getHeight());
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        canvas.drawBitmap(bitmap, 0, 0, paint);
        canvas.restore();
    }



    /**
     * 绘制自定义控件边框
     */
    private void drawBorder(Canvas canvas,int w,int h) {
        if (borderWidth > 0) {
            // 根据控件类型的属性去绘制圆形或者矩形
                // 当ShapeType = 1 时 图片为圆角矩形
                RectF rectf = new RectF(borderWidth / 2, borderWidth / 2, w - borderWidth / 2,
                        h - borderWidth / 2);
                canvas.drawRoundRect(rectf, radius, radius, borderPaint);
        }
    }

    @Override public String getId() {
        return getClass().getName() + Math.round(radius);
    }
}