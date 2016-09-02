package com.walle.gankio.widget;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

public class ZoomImageView extends ImageView implements OnGlobalLayoutListener, OnScaleGestureListener, OnTouchListener {

    private boolean mOnce = false;
    private float mInitScale;
    private float mMidScale;
    private float mMaxScale;

    private Matrix mScaleMatrix = null;

    private ScaleGestureDetector mScaleGestureDetector = null;

    private int mLastPointerCount;

    private float mLastX;
    private float mLastY;

    private int mTouchSlop;

    private boolean isCanDrag;

    private boolean isCheckLeftAndRight;
    private boolean isCheckTopAndBottom;

    private GestureDetector mGestureDetector = null;
    private boolean isAutoScale;


    public ZoomImageView(Context context) {
        this(context, null);
    }

    public ZoomImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZoomImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        mScaleMatrix = new Matrix();
        setScaleType(ScaleType.MATRIX);
        mScaleGestureDetector = new ScaleGestureDetector(context, this);
        setOnTouchListener(this);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mGestureDetector = new GestureDetector(
                context,
                new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onDoubleTap(MotionEvent e) {

                        if (isAutoScale) {
                            return true;
                        }

                        float x = e.getX();
                        float y = e.getY();


                        if (getCurrentScale() < mMidScale) {
                            postDelayed(new AutoScaleRunnable(mMidScale, x, y), 16);
                            isAutoScale = true;
                        } else {
                            postDelayed(new AutoScaleRunnable(mInitScale, x, y), 16);
                            isAutoScale = true;
                        }

                        return true;
                    }
                });
    }



    @Override
    public void onGlobalLayout() {

        if (!mOnce) {
            int width = getWidth();
            int height = getHeight();

            Drawable drawable = getDrawable();
            if (drawable == null) {
                return;
            }
            int drawableWidth = drawable.getIntrinsicWidth();
            int drawableHeight = drawable.getIntrinsicHeight();


            float scale = 1.0f;
            if (drawableWidth > width && drawableHeight < height) {
                scale = width * 1.0f / drawableWidth;
            }
            if (drawableWidth < width && drawableHeight > height) {
                scale = height * 1.0f / drawableHeight;
            }
            if (drawableWidth > width && drawableHeight > height) {
                scale = Math.min(width * 1.0f / drawableWidth, height * 1.0f / drawableHeight);
            }
            if (drawableWidth < width && drawableHeight < height) {
                scale = Math.min(width * 1.0f / drawableWidth, height * 1.0f / drawableHeight);
            }


            mInitScale = scale;
            mMidScale = mInitScale * 2;
            mMaxScale = mInitScale * 4;


            int dx = width / 2 - drawableWidth / 2;
            int dy = height / 2 - drawableHeight / 2;


            mScaleMatrix.postTranslate(dx, dy);

            mScaleMatrix.postScale(mInitScale, mInitScale, width / 2, height / 2);

            setImageMatrix(mScaleMatrix);

            mOnce = true;
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }


    @SuppressWarnings("deprecation")
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        getViewTreeObserver().removeGlobalOnLayoutListener(this);
    }

    public float getCurrentScale() {
        float[] values = new float[9];
        mScaleMatrix.getValues(values);
        return values[Matrix.MSCALE_X];
    }


    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        float scale = getCurrentScale();
        float scaleFactor = detector.getScaleFactor();

        if (getDrawable() == null) {
            return true;
        }


        if ((scale < mMaxScale && scaleFactor > 1.0f) || (scale > mInitScale && scaleFactor < 1.0f)) {
            if (scale * scaleFactor < mInitScale) {
                scaleFactor = mInitScale / scale;
            }
            if (scale * scaleFactor > mMaxScale) {
                scaleFactor = mMaxScale / scale;
            }
        }

        mScaleMatrix.postScale(scaleFactor, scaleFactor, detector.getFocusX(), detector.getFocusY());

        checkBorderAndCenterWhenScale();

        setImageMatrix(mScaleMatrix);

        return true;
    }



    private RectF getMatrixRectF() {
        Matrix matrix = mScaleMatrix;
        RectF rectF = new RectF();
        Drawable drawable = getDrawable();
        if (drawable != null) {
            rectF.set(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            matrix.mapRect(rectF);
        }
        return rectF;
    }


    private void checkBorderAndCenterWhenScale() {

        RectF rect = getMatrixRectF();


        float deltaX = 0.0f;
        float deltaY = 0.0f;


        int width = getWidth();
        int height = getHeight();


        if (rect.width() >= width) {
            if (rect.left > 0) {
                deltaX = -rect.left;
            }
            if (rect.right < width) {
                deltaX = width - rect.right;
            }
        }
        if (rect.height() >= height) {
            if (rect.top > 0) {
                deltaY = -rect.top;
            }
            if (rect.bottom < height) {
                deltaY = height - rect.bottom;
            }
        }

        if (rect.width() < width) {
            deltaX = width / 2f - rect.right + rect.width() / 2f;
        }
        if (rect.height() < height) {
            deltaY = height / 2f - rect.bottom + rect.height() / 2f;
        }

        mScaleMatrix.postTranslate(deltaX, deltaY);
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (mGestureDetector.onTouchEvent(event)) {
            return true;
        }

        mScaleGestureDetector.onTouchEvent(event);

        float x = 0;
        float y = 0;

        int pointerCount = event.getPointerCount();

        for (int i = 0; i < pointerCount; i++) {
            x += event.getX(i);
            y += event.getY(i);
        }

        x /= pointerCount;
        y /= pointerCount;

        if (mLastPointerCount != pointerCount) {
            isCanDrag = false;
            mLastX = x;
            mLastY = y;
        }
        mLastPointerCount = pointerCount;
        RectF rectF = getMatrixRectF();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (rectF.width() > getWidth() + 0.01 || rectF.height() > getHeight() + 0.01) {
                    if (getParent() instanceof ViewPager) {
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (rectF.width() > getWidth() + 0.01 || rectF.height() > getHeight() + 0.01) {
                    if (getParent() instanceof ViewPager) {
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                }
                float deltaX = x - mLastX;
                float deltaY = y - mLastY;
                if (!isCanDrag) {
                    isCanDrag = isMoveAction(deltaX, deltaY);
                }
                if (isCanDrag) {

                    if (getDrawable() != null) {
                        isCheckLeftAndRight = true;
                        isCheckTopAndBottom = true;
                        if (rectF.width() < getWidth()) {
                            isCheckLeftAndRight = false;
                            deltaX = 0;
                        }
                        if (rectF.height() < getHeight()) {
                            isCheckTopAndBottom = false;
                            deltaY = 0;
                        }
                       mScaleMatrix.postTranslate(deltaX, deltaY);

                        checkBorderWhenTranslate();
                        setImageMatrix(mScaleMatrix);
                    }
                }
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_UP:
                mLastPointerCount = 0;
                break;
            case MotionEvent.ACTION_CANCEL:
                mLastPointerCount = 0;
                break;
        }

        return true;
    }


    /**
     */
    private void checkBorderWhenTranslate() {

        RectF rectF = getMatrixRectF();

        float deltaX = 0;
        float deltaY = 0;


        int width = getWidth();
        int height = getHeight();

        if (rectF.top > 0 && isCheckTopAndBottom) {
            deltaY = -rectF.top;
        }
        if (rectF.bottom < height && isCheckTopAndBottom) {
            deltaY = height - rectF.bottom;
        }
        if (rectF.left > 0 && isCheckLeftAndRight) {
            deltaX = -rectF.left;
        }
        if (rectF.right < width && isCheckLeftAndRight) {
            deltaX = width - rectF.right;
        }
        mScaleMatrix.postTranslate(deltaX, deltaY);
    }


    /**
     */
    private boolean isMoveAction(float deltaX, float deltaY) {

        return Math.sqrt(deltaX * deltaX + deltaY * deltaY) > mTouchSlop;

    }


    /**
     */
    private class AutoScaleRunnable implements Runnable {


        private float mTargetScale;

        private float x;
        private float y;

        private final float BIGGER = 1.07f;
        private final float SMALL = 0.93f;

        private float tmpScale;


        public AutoScaleRunnable(float mTargetScale, float x, float y) {
            this.mTargetScale = mTargetScale;
            this.x = x;
            this.y = y;

            if (getCurrentScale() < mTargetScale) {
                tmpScale = BIGGER;
            } else if (getCurrentScale() > mTargetScale) {
                tmpScale = SMALL;
            }
        }


        @Override
        public void run() {

            mScaleMatrix.postScale(tmpScale, tmpScale, x, y);
            checkBorderAndCenterWhenScale();
            setImageMatrix(mScaleMatrix);

            float currentScale = getCurrentScale();
             if ((tmpScale > 1.0f && currentScale < mTargetScale) || (tmpScale < 1.0f && currentScale > mTargetScale)) {
                postDelayed(this, 16);
            } else {
                 float scale = mTargetScale / currentScale;
                mScaleMatrix.postScale(scale, scale, x, y);
                checkBorderAndCenterWhenScale();
                setImageMatrix(mScaleMatrix);
                isAutoScale = false;
            }
        }

    }

}
