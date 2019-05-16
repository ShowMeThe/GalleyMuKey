package showmethe.github.kframework.widget.common;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import showmethe.github.kframework.R;


/**
 * PackageName: example.ken.com.library.widget
 * Author : jiaqi Ye
 * Date : 2018/9/27
 * Time : 16:06
 */
public class SideIndexBar extends View {

    /**
     * 英文大写
     */
    private static final String[] DEFAULT_INDEX_ITEMS = { "A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#"};

    private List<String> mIndexItems;
    private float mItemHeight; //每个index的高度
    private int mTextSize;      //sp
    private int mTextColor;
    private int mTextTouchedColor;
    private int mCurrentIndex = -1;

    private Paint mPaint;
    private Paint mTouchedPaint;

    private int mWidth;
    private int mHeight;
    private float mTopMargin;   //居中绘制，文字绘制起点和控件顶部的间隔


    private TextView mOverlayTextView;
    private OnIndexTouchedChangedListener mOnIndexChangedListener;


    public SideIndexBar(Context context) {
        this(context, null);
    }

    public SideIndexBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SideIndexBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }


    private void init(Context context,AttributeSet attrs) {
        mIndexItems = new ArrayList<>();
        mIndexItems.addAll(Arrays.asList(DEFAULT_INDEX_ITEMS));

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SideIndexBar);

        mTextSize = (int) array.getDimension(R.styleable.SideIndexBar_indexBarTextSize,17);

        mTextColor = array.getColor(R.styleable.SideIndexBar_indexBarNormalTextColor, ContextCompat.getColor(context,R.color.text_999999));

        mTextTouchedColor = array.getColor(R.styleable.SideIndexBar_indexBarSelectedTextColor, ContextCompat.getColor(context,R.color.text_333333));

        array.recycle();

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(mTextSize);
        mPaint.setColor(mTextColor);

        mTouchedPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTouchedPaint.setTextSize(mTextSize);
        mTouchedPaint.setColor(mTextTouchedColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        String index;
        for (int i = 0; i < mIndexItems.size(); i++) {
            index = mIndexItems.get(i);
            Paint.FontMetrics fm = mPaint.getFontMetrics();
            canvas.drawText(index,
                    (mWidth - mPaint.measureText(index)) / 2,
                    mItemHeight / 2 + (fm.bottom-fm.top) / 2 - fm.bottom + mItemHeight * i + mTopMargin,
                    i == mCurrentIndex ? mTouchedPaint : mPaint);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getWidth();
        mHeight = Math.max(h, oldh);
        mItemHeight = mHeight / mIndexItems.size();
        mTopMargin = (mHeight - mItemHeight * mIndexItems.size()) / 2;
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        performClick();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float y = event.getY();
                int indexSize = mIndexItems.size();
                int touchedIndex = (int) (y / mItemHeight);
                if (touchedIndex < 0) {
                    touchedIndex = 0;
                }else if (touchedIndex >= indexSize) {
                    touchedIndex = indexSize - 1;
                }
                if (mOnIndexChangedListener != null && touchedIndex >= 0 && touchedIndex < indexSize){
                    if (touchedIndex != mCurrentIndex) {
                        mCurrentIndex = touchedIndex;
                        if (mOverlayTextView != null){
                            mOverlayTextView.setVisibility(VISIBLE);
                            mOverlayTextView.setText(mIndexItems.get(touchedIndex));
                        }
                        mOnIndexChangedListener.onIndexChanged(mIndexItems.get(touchedIndex), touchedIndex);
                        invalidate();
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mCurrentIndex = -1;
                if (mOverlayTextView != null){
                    mOverlayTextView.setVisibility(GONE);
                }
                invalidate();
                break;
        }
        return true;
    }

    public SideIndexBar setOverlayTextView(TextView overlay){
        this.mOverlayTextView = overlay;
        return this;
    }

    public SideIndexBar setOnIndexChangedListener(OnIndexTouchedChangedListener listener){
        this.mOnIndexChangedListener = listener;
        return this;
    }

    public interface OnIndexTouchedChangedListener{
        void onIndexChanged(String index, int position);
    }



}
