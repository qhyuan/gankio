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

    public ResizeImageView(Context context) {
        this(context, null);
    }

    public ResizeImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Drawable drawable = getDrawable();

        if (drawable != null) {

            if (drawable.getIntrinsicWidth() != getWidth()|| drawable.getIntrinsicHeight()!=getHeight()) {
                float scalX = ((float) getWidth() / (float) drawable.getIntrinsicWidth());
//                float scalY = ((float) getHeight() / (float) bitmap.getHeight());
                widthMeasureSpec = MeasureSpec.makeMeasureSpec(getWidth(),MeasureSpec.EXACTLY);
                heightMeasureSpec = MeasureSpec.makeMeasureSpec((int) (drawable.getIntrinsicHeight()*scalX+0.5),MeasureSpec.EXACTLY);
            }
        }
       super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}
