package com.walle.gankio.widget.glide;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by yqh on 2016/7/13.
 */
public class ResizeImageView extends ImageView {
    private static final String TAG = "ResizeImageView";
    private Drawable drawable;

    public ResizeImageView(Context context) {
        this(context, null);
    }

    public ResizeImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        drawable = getDrawable();

        if (drawable != null) {
//            LogUtil.i("onMeasure:"+drawable.getIntrinsicHeight());
        /*    if (drawable instanceof GlideBitmapDrawable)
                bitmap = ((GlideBitmapDrawable) drawable).getBitmap();
            else
                bitmap = ((BitmapDrawable) drawable).getBitmap();
*/
            if (drawable.getIntrinsicWidth() != getWidth()|| drawable.getIntrinsicHeight()!=getHeight()) {
                float scalX = ((float) getWidth() / (float) drawable.getIntrinsicWidth());
//                float scalY = ((float) getHeight() / (float) bitmap.getHeight());
                widthMeasureSpec = MeasureSpec.makeMeasureSpec(getWidth(),MeasureSpec.EXACTLY);
                heightMeasureSpec = MeasureSpec.makeMeasureSpec((int) (drawable.getIntrinsicHeight()*scalX+0.5),MeasureSpec.EXACTLY);
            }
        }
       super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {

      /*  Drawable drawable = getDrawable();

        if (drawable != null) {
            Bitmap bitmap = null;
            if (drawable instanceof GlideBitmapDrawable)
                bitmap = ((GlideBitmapDrawable) drawable).getBitmap();
            else
                bitmap = ((BitmapDrawable) drawable).getBitmap();
            LogUtil.i(TAG, "bitmap=" + bitmap.getWidth() + "--getHeight=" + bitmap.getHeight());
            LogUtil.i(TAG, "getWidth=" + getWidth() + "--getHeight=" + getHeight());
            LogUtil.i(TAG, "getMeasuredWidth=" + getMeasuredWidth() + "--getMeasuredHeight=" + getMeasuredHeight());
            if (bitmap.getWidth() != getWidth()) {
                float scal = ((float) getWidth() / (float) bitmap.getWidth());
                LogUtil.i(TAG, "scal=" + scal);
                canvas.scale(scal, scal);
            }
        }*/
        super.onDraw(canvas);

    }

}
