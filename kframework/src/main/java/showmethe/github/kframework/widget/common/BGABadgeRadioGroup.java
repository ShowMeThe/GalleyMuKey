package showmethe.github.kframework.widget.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RadioGroup;

import cn.bingoogolapple.badgeview.BGABadgeViewHelper;
import cn.bingoogolapple.badgeview.BGABadgeable;
import cn.bingoogolapple.badgeview.BGADragDismissDelegate;

/**
 * showmethe.github.kframework.widget.common
 * cuvsu
 * 2019/2/12
 **/
public class BGABadgeRadioGroup extends RadioGroup implements BGABadgeable {
    private BGABadgeViewHelper mBadgeViewHelper;

    public BGABadgeRadioGroup(Context context) {
        this(context, null);
    }

    public BGABadgeRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        mBadgeViewHelper = new BGABadgeViewHelper(this, context, attrs, BGABadgeViewHelper.BadgeGravity.RightCenter);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mBadgeViewHelper.onTouchEvent(event);
    }

    @Override
    public boolean callSuperOnTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        mBadgeViewHelper.drawBadge(canvas);
    }

    @Override
    public void showCirclePointBadge() {
        mBadgeViewHelper.showCirclePointBadge();
    }

    @Override
    public void showTextBadge(String badgeText) {
        mBadgeViewHelper.showTextBadge(badgeText);
    }

    @Override
    public void hiddenBadge() {
        mBadgeViewHelper.hiddenBadge();
    }

    @Override
    public void showDrawableBadge(Bitmap bitmap) {
        mBadgeViewHelper.showDrawable(bitmap);
    }

    @Override
    public void setDragDismissDelegate(BGADragDismissDelegate delegate) {
        mBadgeViewHelper.setDragDismissDelegate(delegate);
    }

    @Override
    public boolean isShowBadge() {
        return mBadgeViewHelper.isShowBadge();
    }

    @Override
    public boolean isDraggable() {
        return mBadgeViewHelper.isDraggable();
    }

    @Override
    public boolean isDragging() {
        return mBadgeViewHelper.isDragging();
    }

    @Override
    public BGABadgeViewHelper getBadgeViewHelper() {
        return mBadgeViewHelper;
    }
}
