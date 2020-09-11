package com.hao.starbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Created by Hao on 2020/9/11
 * 星星评分
 */
public class StarBarView extends View {

    private float itemWidth;
    private int mFullStar;
    private int mHalfStar;
    private int mEmptyStar;
    private float mStarPadding;
    private boolean ratable;    //是否点击滑动
    private Bitmap mStarBitMap;
    private float mScale;
    private float[] points;//星星坐标
    private int mScore;
    private OnScoreChangeListener onScoreChangeListener;


    public StarBarView(Context context) {
        super(context);
        init(context, null);
    }

    public StarBarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        try {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.StarBarView);
            this.mFullStar = array.getResourceId(R.styleable.StarBarView_star_full, R.drawable.ic_star_full);
            this.mHalfStar = array.getResourceId(R.styleable.StarBarView_star_half, R.drawable.ic_star_half);
            this.mEmptyStar = array.getResourceId(R.styleable.StarBarView_star_empty, R.drawable.ic_star_empty);
            this.ratable = array.getBoolean(R.styleable.StarBarView_star_ratable, false);
            this.mStarPadding = array.getDimensionPixelOffset(R.styleable.StarBarView_star_padding, 10);
            array.recycle();
            mStarBitMap = BitmapFactory.decodeResource(getResources(), mEmptyStar);
            points = new float[11];
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        itemWidth = (w - (mStarPadding * 4)) / 5;
        mScale = itemWidth / mStarBitMap.getWidth();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        itemWidth = (getMeasuredWidth() - (mStarPadding * 4)) / 5;
        mScale = itemWidth / mStarBitMap.getWidth();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawStar(canvas);
    }

    private void drawStar(Canvas canvas) {
        int count = mScore / 2;
        boolean isOdd = mScore % 2 != 0;
        for (int i = 0; i < 5; i++) {
            Matrix matrix = new Matrix();
            matrix.setScale(mScale, mScale);
            if (i != 0) {
                canvas.translate(itemWidth + mStarPadding, 0);
            }
            //存储坐标
            if (i != 0 && i != 4) {
                points[i * 2] = i * itemWidth + i * mStarPadding;
                points[i * 2 + 1] = points[i * 2] + itemWidth / 2f;
            }
            if (i == 0) {
                points[0] = 0;
                points[1] = itemWidth;
            }
            if (i == 4) {
                points[i * 2] = i * itemWidth + i * mStarPadding;
                points[i * 2 + 1] = points[i * 2] + itemWidth / 2f;
                points[10] = points[9] + itemWidth / 2f;
            }
            if (i < count) {
                mStarBitMap = BitmapFactory.decodeResource(getResources(), mFullStar);
            } else {
                mStarBitMap = BitmapFactory.decodeResource(getResources(), mEmptyStar);
            }
            if (isOdd && i == count) {
                mStarBitMap = BitmapFactory.decodeResource(getResources(), mHalfStar);
            }
            canvas.drawBitmap(mStarBitMap, matrix, new Paint());
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!ratable) {
            return super.onTouchEvent(event);
        }
        mScore = calculateStar(event.getX());
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (null != onScoreChangeListener) {
                onScoreChangeListener.onScoreChange(mScore);
            }
        }
        invalidate();
        return true;
    }

    /**
     * 根据坐标计算分数
     */
    private int calculateStar(float x) {
        for (int i = 0; i < points.length; i++) {
            if (points[i] > x) {
                return i;
            }
        }
        return 10;
    }

    public void setRatable(boolean ratable) {
        this.ratable = ratable;
    }

    public int getScore() {
        return mScore;
    }

    public void setScore(int score) {
        this.mScore = score;
    }

    public void setOnScoreChangeListener(OnScoreChangeListener onScoreChangeListener) {
        this.onScoreChangeListener = onScoreChangeListener;
    }

    public interface OnScoreChangeListener {
        void onScoreChange(int score);
    }

}
