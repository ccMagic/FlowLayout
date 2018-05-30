package io.github.ccmagic.flowlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.support.annotation.ColorRes;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ccMagic on 2017/11/16.
 * Copyright :
 * Version :
 * Reference :
 * Description : 显示标签的自定义ViewGroup
 */

public class LabelViewGroup extends ViewGroup {

    private static final String TAG = "LabelViewGroup";
    private Context mContext;
    private int VIEW_MARGIN_X = 10;//标签之间的横向间距
    private int VIEW_MARGIN_Y = 5;//标签之间的纵向间距
    private int mTextSize = 7;//字体大小
    //设置标签内容
    private int mTextPaddingLeft = 15;//单个标签的内部左侧padding
    private int mTextPaddingRight = 15;//单个标签的内部右侧padding
    private int mTextPaddingTop = 10;//单个标签的内部上侧padding
    private int mTextPaddingBottom = 10;//单个标签的内部下侧padding
    //边框宽度
    private int mBorder = 3;
    //

    public LabelViewGroup(Context context) {
        super(context);
    }

    public LabelViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LabelViewGroup);
        try {
            mTextSize = typedArray.getDimensionPixelOffset(R.styleable.LabelViewGroup_label_text_size, mTextSize);
            mBorder = typedArray.getDimensionPixelOffset(R.styleable.LabelViewGroup_label_border_width, mBorder);
            VIEW_MARGIN_X = typedArray.getDimensionPixelOffset(R.styleable.LabelViewGroup_view_margin_X, VIEW_MARGIN_X);
            VIEW_MARGIN_Y = typedArray.getDimensionPixelOffset(R.styleable.LabelViewGroup_view_margin_Y, VIEW_MARGIN_Y);
            mTextPaddingLeft = typedArray.getDimensionPixelOffset(R.styleable.LabelViewGroup_single_padding_left, mTextPaddingLeft);
            mTextPaddingRight = typedArray.getDimensionPixelOffset(R.styleable.LabelViewGroup_single_padding_right, mTextPaddingRight);
            mTextPaddingTop = typedArray.getDimensionPixelOffset(R.styleable.LabelViewGroup_single_padding_top, mTextPaddingTop);
            mTextPaddingBottom = typedArray.getDimensionPixelOffset(R.styleable.LabelViewGroup_single_padding_bottom, mTextPaddingBottom);
        } catch (Exception e) {
            Log.e(TAG, "LabelViewGroup: ", e);
        } finally {
            typedArray.recycle();
        }
        mContext = context;
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

    /**
     * 设置标签数据
     * 将标签颜色和文本颜色一致
     */
    @Deprecated
    public void setData(List<LabelBean> labelBeanList) {
        removeAllViews();
        for (LabelBean labelBean : labelBeanList) {
            TextView labelView = new TextView(mContext);
            labelView.setText(labelBean.getLabelName());
            labelView.setTextColor(getResources().getColor(labelBean.getLabelColor()));
            labelView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
            labelView.setGravity(Gravity.CENTER);
            labelView.setPadding(mTextPaddingLeft, mTextPaddingTop, mTextPaddingRight, mTextPaddingBottom);
            float outerRound = mTextSize + mTextPaddingLeft + mTextPaddingRight;
            float innerRound = outerRound - mBorder;
            RectF rect = new RectF(mBorder, mBorder, mBorder, mBorder);
            float[] inner = new float[]{innerRound, innerRound, innerRound, innerRound, innerRound, innerRound, innerRound, innerRound};
            float[] outer = new float[]{outerRound, outerRound, outerRound, outerRound, outerRound, outerRound, outerRound, outerRound,};
            RoundRectShape roundRectShape = new RoundRectShape(outer, rect, inner);
            ShapeDrawable shapeDrawable = new ShapeDrawable(roundRectShape);
            shapeDrawable.getPaint().setColor(getResources().getColor(labelBean.getLabelColor()));
            shapeDrawable.getPaint().setStyle(Paint.Style.FILL);
            labelView.setBackground(shapeDrawable);
            addView(labelView);
        }
    }

    /**
     * 设置标签数据
     * 将标签颜色和文本颜色分开
     */
    public void setColorSeparationData(List<LabelBean> labelBeanList) {
        removeAllViews();
        for (LabelBean labelBean : labelBeanList) {
            TextView labelView = new TextView(mContext);
            labelView.setText(labelBean.getLabelName());
            labelView.setTextColor(getResources().getColor(labelBean.getLabelTextColor()));
            labelView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
            labelView.setGravity(Gravity.CENTER);
            labelView.setPadding(mTextPaddingLeft, mTextPaddingTop, mTextPaddingRight, mTextPaddingBottom);
            float outerRound = mTextSize + mTextPaddingLeft + mTextPaddingRight;
            float innerRound = outerRound - mBorder;
            RectF rect = new RectF(mBorder, mBorder, mBorder, mBorder);
            float[] inner = new float[]{innerRound, innerRound, innerRound, innerRound, innerRound, innerRound, innerRound, innerRound};
            float[] outer = new float[]{outerRound, outerRound, outerRound, outerRound, outerRound, outerRound, outerRound, outerRound,};
            RoundRectShape roundRectShape = new RoundRectShape(outer, rect, inner);
            ShapeDrawable shapeDrawable = new ShapeDrawable(roundRectShape);
            shapeDrawable.getPaint().setColor(getResources().getColor(labelBean.getLabelColor()));
            shapeDrawable.getPaint().setStyle(Paint.Style.FILL);
            labelView.setBackground(shapeDrawable);
            addView(labelView);
        }
    }

    public static class LabelBean {

        private String labelName;
        private int labelColor;
        private int labelTextColor;

        /**
         * @param labelName 标签文本内容
         * @param color     标签文字/边框颜色
         */
        public LabelBean(String labelName, int color) {
            this.labelName = labelName;
            this.labelColor = color;
            this.labelTextColor = color;
        }

        /**
         * @param labelName      标签文本内容
         * @param labelColor     标签文字颜色
         * @param labelTextColor 标签边框颜色
         */
        public LabelBean(String labelName, int labelColor, int labelTextColor) {
            this.labelName = labelName;
            this.labelColor = labelColor;
            this.labelTextColor = labelTextColor;
        }

        public String getLabelName() {
            return labelName;
        }

        public void setLabelName(String labelName) {
            this.labelName = labelName;
        }

        public int getLabelColor() {
            return labelColor;
        }

        public void setLabelColor(@ColorRes int labelColor) {
            this.labelColor = labelColor;
        }

        public int getLabelTextColor() {
            return labelTextColor;
        }

        public void setLabelTextColor(@ColorRes int labelTextColor) {
            this.labelTextColor = labelTextColor;
        }
    }
}
