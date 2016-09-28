package com.walle.gankio.widget.glide;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.Log;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.TransformationUtils;
import com.walle.gankio.utils.LogUtil;

public class GlideRoundTransform extends BitmapTransformation {

    private int borderWidth;
    private int radius;
    private Paint borderPaint;

    public GlideRoundTransform(Context context) {
        this(context, 16);
    }

    private GlideRoundTransform(Context context, int dp) {
        super(context);
        this.radius = (int) (Resources.getSystem().getDisplayMetrics().density * dp);
        init();
    }

    private void init() {

        borderWidth = 6;
        int borderColor = 0x77ffffff;

        radius = 16;

        borderPaint = new Paint();
        borderPaint.setStrokeWidth(borderWidth);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setColor(borderColor);
        borderPaint.setAntiAlias(true);

    }
    @Override protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        final Bitmap toReuse = pool.get(outWidth, outHeight, toTransform.getConfig() != null
                ? toTransform.getConfig() : Bitmap.Config.ARGB_8888);

        Bitmap transformed = TransformationUtils.centerCrop(toReuse, toTransform, outWidth, outHeight);
        Log.e("demo","---"+transformed.getWidth()+"---"+transformed.getHeight());
        if (toReuse != null && toReuse != transformed && !pool.put(toReuse)) {
            toReuse.recycle();
        }
        return roundCrop(pool,transformed);
    }


    private  Bitmap roundCrop(BitmapPool pool, Bitmap source) {
        if (source == null) return null;
        Canvas canvas = new Canvas(source);
        Log.i("demo","---"+canvas.getWidth()+"---"+canvas.getHeight());
        onDraw(canvas,source);
        return source;
    }

    private void onDraw(Canvas canvas, Bitmap bitmap) {
        drawDrawable(canvas, bitmap);
        drawBorder(canvas,bitmap.getWidth(),bitmap.getHeight());
    }


    /**
     * 实现圆角的绘制
     */
    private void drawDrawable(Canvas canvas, Bitmap bitmap) {

        Paint paint = new Paint();
        paint.setColor(0xffffffff);
        paint.setAntiAlias(true);
        //Paint 的 Xfermode，PorterDuff.Mode.SRC_IN 取两层图像的交集部门, 只显示上层图像。
        PorterDuffXfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
        int saveFlags = Canvas.MATRIX_SAVE_FLAG
                | Canvas.CLIP_SAVE_FLAG
                | Canvas.HAS_ALPHA_LAYER_SAVE_FLAG
                | Canvas.FULL_COLOR_LAYER_SAVE_FLAG
                | Canvas.CLIP_TO_LAYER_SAVE_FLAG;
        canvas.saveLayer(0, 0, bitmap.getWidth(), bitmap.getHeight(), null, saveFlags);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        RectF rectf = new RectF(1, 1, width - 1, height - 1);
        canvas.drawRoundRect(rectf, radius + 1, radius + 1, paint);

        paint.setXfermode(xfermode);

        LogUtil.i("demo w="+bitmap.getWidth()+", h= "+bitmap.getHeight());
        canvas.drawBitmap(bitmap, 0, 0, paint);
        canvas.restore();
    }



    /**
     * 绘制自定义控件边框
     */
    private void drawBorder(Canvas canvas,int w,int h) {
        if (borderWidth > 0) {
            RectF rectf = new RectF(borderWidth / 2, borderWidth / 2, w - borderWidth / 2,
                    h - borderWidth / 2);
            canvas.drawRoundRect(rectf, radius, radius, borderPaint);
        }
    }

    @Override public String getId() {
        return getClass().getName() + Math.round(radius);
    }
}