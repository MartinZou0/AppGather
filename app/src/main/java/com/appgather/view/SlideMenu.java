package com.appgather.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;


//已取代,自定义滑动菜单
public class SlideMenu extends ViewGroup {
	public static final int SCREEN_MENU = 0;
	public static final int SCREEN_MAIN = 1;
	private static final int SCREEN_INVALID = -1;
	private int mCurrentScreen;
	private int mNextScreen = SCREEN_INVALID;
	private Scroller mScroller;


	public SlideMenu(Context context) {
		this(context, null, 0);
	}

	public SlideMenu(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SlideMenu(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		mScroller = new Scroller(getContext());
		mCurrentScreen = SCREEN_MAIN;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		measureViews(widthMeasureSpec, heightMeasureSpec);
	}

	public void measureViews(int widthMeasureSpec, int heightMeasureSpec) {
		View menuView = getChildAt(0);
		menuView.measure(menuView.getLayoutParams().width + menuView.getLeft()
				+ menuView.getRight(), heightMeasureSpec);

		View contentView = getChildAt(1);
		contentView.measure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int childCount = getChildCount();
		if (childCount != 2) {
			throw new IllegalStateException(
					"The childCount of SlidingMenu must be 2");
		}

		View menuView = getChildAt(0);
		final int width = menuView.getMeasuredWidth();
		menuView.layout(-width, 0, 0, menuView.getMeasuredHeight());

		View contentView = getChildAt(1);
		contentView.layout(0, 0, contentView.getMeasuredWidth(),
				contentView.getMeasuredHeight());
	}
	
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		View child;
		for (int i = 0; i < getChildCount(); i++) {
			child = getChildAt(i);
			child.setFocusable(true);
			child.setClickable(true);
		}
	}

	void enableChildrenCache() {
		final int count = getChildCount();
		for (int i = 0; i < count; i++) {
			final View layout = (View) getChildAt(i);
			layout.setDrawingCacheEnabled(true);
		}
	}

	void clearChildrenCache() {
		final int count = getChildCount();
		for (int i = 0; i < count; i++) {
			final View layout = (View) getChildAt(i);
			layout.setDrawingCacheEnabled(false);
		}
	}

	@Override
	public void computeScroll() {
		if (mScroller.computeScrollOffset()) {
			scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
		} else if (mNextScreen != SCREEN_INVALID) {
			mCurrentScreen = Math.max(0,
					Math.min(mNextScreen, getChildCount() - 1));
			mNextScreen = SCREEN_INVALID;
			clearChildrenCache();
		}
	}

	@Override
	public void scrollTo(int x, int y) {
		super.scrollTo(x, y);
		postInvalidate();
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {
		final int scrollX = getScrollX();
		super.dispatchDraw(canvas);
		canvas.translate(scrollX, 0);
	}

	@Override
	public boolean dispatchUnhandledMove(View focused, int direction) {
		if (direction == View.FOCUS_LEFT) {
			if (getCurrentScreen() > 0) {
				snapToScreen(getCurrentScreen() - 1);
				return true;
			}
		} else if (direction == View.FOCUS_RIGHT) {
			if (getCurrentScreen() < getChildCount() - 1) {
				snapToScreen(getCurrentScreen() + 1);
				return true;
			}
		}
		return super.dispatchUnhandledMove(focused, direction);
	}
	
	protected void snapToScreen(int whichScreen) {

		enableChildrenCache();

		whichScreen = Math.max(0, Math.min(whichScreen, getChildCount() - 1));
		boolean changingScreens = whichScreen != mCurrentScreen;

		mNextScreen = whichScreen;

		View focusedChild = getFocusedChild();
		if (focusedChild != null && changingScreens
				&& focusedChild == getChildAt(mCurrentScreen)) {
			focusedChild.clearFocus();
		}

		final int newX = (whichScreen - 1) * getChildAt(0).getWidth();
		final int delta = newX - getScrollX();
		mScroller.startScroll(getScrollX(), 0, delta, 0, Math.abs(delta) * 2);
		invalidate();
	}

	protected void snapToDestination() {
		if (getScrollX() == 0) {
			return;
		}
		final int screenWidth = getChildAt(0).getWidth();
		final int whichScreen = (screenWidth + getScrollX() + (screenWidth / 2))
				/ screenWidth;
		snapToScreen(whichScreen);
	}
	
	public int getCurrentScreen() {
		return mCurrentScreen;
	}
	
	public boolean isMainScreenShowing() {
		return mCurrentScreen == SCREEN_MAIN;
	}
	
	public void openMenu() {
		mCurrentScreen = SCREEN_MENU;
		snapToScreen(mCurrentScreen);
	}
	
	public void closeMenu() {

		mCurrentScreen = SCREEN_MAIN;
		snapToScreen(mCurrentScreen);
	}

	
}
