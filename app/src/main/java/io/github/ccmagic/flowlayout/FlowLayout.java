package io.github.ccmagic.flowlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ccMagic on 2018/5/30.
 * Copyright ：
 * Version ：
 * Reference ：
 * Description ：
 */
public class FlowLayout extends ViewGroup {

    private static final String TAG = "LabelViewGroup";
    private int VIEW_MARGIN_X = 10;//标签之间的横向间距
    private int VIEW_MARGIN_Y = 5;//标签之间的纵向间距
    //

    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FlowLayout);
        try {
            VIEW_MARGIN_X = typedArray.getDimensionPixelOffset(R.styleable.FlowLayout_flow_view_margin_X, VIEW_MARGIN_X);
            VIEW_MARGIN_Y = typedArray.getDimensionPixelOffset(R.styleable.FlowLayout_flow_view_margin_Y, VIEW_MARGIN_Y);
        } catch (Exception e) {
            Log.e(TAG, "LabelViewGroup: ", e);
        } finally {
            typedArray.recycle();
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    /**
     * 通知子组件对自身进行测量
     */
    @Override
    protected void onMeasure(int withMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(withMeasureSpec, heightMeasureSpec);
        //获得此ViewGroup上级容器为其推荐的宽和高，以及计算模式
        int widthMode = MeasureSpec.getMode(withMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(withMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        //
        int width = getPaddingLeft() + getPaddingRight();
        int height = getPaddingBottom() + getPaddingTop();

        //
        int maxHeightEveryLine = 0;//每行的最大高度
        int AllHeight = height;//FlowLayout高度
        //
        measureChildren(withMeasureSpec, heightMeasureSpec);
        //
        for (int i = 0, count = getChildCount(); i < count; i++) {
            View childView = getChildAt(i);
            if (childView.getVisibility() != GONE) {
                width += childView.getMeasuredWidth();
                if (width > sizeWidth) {
                    //换行
                    width = getPaddingLeft() + getPaddingRight() + childView.getMeasuredWidth();
                    maxHeightEveryLine = childView.getMeasuredHeight();//换行后新的一行的第一个View的高度
                    height = AllHeight + VIEW_MARGIN_Y;
                } else {
                    //不换行
                    if (maxHeightEveryLine < childView.getMeasuredHeight()) {//取每行各个子View的最大高度
                        maxHeightEveryLine = childView.getMeasuredHeight();
                    }
                }
                AllHeight = height + maxHeightEveryLine;
                width += VIEW_MARGIN_X;
            }

        }
        setMeasuredDimension(sizeWidth, (heightMode == MeasureSpec.EXACTLY) ? sizeHeight : AllHeight);
    }

    /**
     * @param b      该参数指出当前ViewGroup的尺寸或者位置是否发生了改变
     * @param left   left 当前ViewGroup相对于其父控件的坐标位置
     * @param top    top 当前ViewGroup相对于其父控件的坐标位置
     * @param right  right 当前ViewGroup相对于其父控件的坐标位置
     * @param bottom bottom 当前ViewGroup相对于其父控件的坐标位置
     */
    @Override
    protected void onLayout(boolean b, int left, int top, int right, int bottom) {
        int maxHeightEveryLine = 0;//每行最大高度

        int mPainterPosX = getPaddingLeft();  //当前绘图光标横坐标位置
        int mPainterPosY = getPaddingTop();  //当前绘图光标纵坐标位置
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                int childWidth = child.getMeasuredWidth();
                int childHeight = child.getMeasuredHeight();
                //
                if (mPainterPosX + childWidth > getMeasuredWidth() - getPaddingRight()) {
                    //换行
                    mPainterPosY = mPainterPosY + maxHeightEveryLine + VIEW_MARGIN_Y;
                    mPainterPosX = getPaddingLeft() + childWidth;
                    maxHeightEveryLine = childHeight;
                } else {
                    //每一行最后一个测量需要换行的子View的高度在这里处理
                    if (maxHeightEveryLine < childHeight) {
                        maxHeightEveryLine = childHeight;
                    }
                    mPainterPosX += childWidth;
                }
                child.layout(mPainterPosX - childWidth, mPainterPosY, mPainterPosX, mPainterPosY + childHeight);
                mPainterPosX += VIEW_MARGIN_X;
            }
        }
    }

}
