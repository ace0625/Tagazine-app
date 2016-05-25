package com.styletag.tagazine.views;


import com.styletag.tagazine.activity.Main_Activity;
import com.styletag.tagazine.activity.R;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import android.widget.Scroller;

public class SlidingView extends LinearLayout {
	//view groups
	private View menu;
	private View content;
	private LinearLayout touchslide;
	Context context;
//	public static int init()
//	{
//		DisplayMetrics displaymetrics = new DisplayMetrics();
//		activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
//	
//		int width = displaymetrics.widthPixels;
//		return width;
//	}
	
	protected static final int menuMargin =  120;
	
	public enum MenuState
	{	
		CLOSED, OPEN, CLOSING, OPENING
	};
	
	//position
	protected int currentContent = 0; //when content view has no moves
	protected MenuState menuCurrentState = MenuState.CLOSED;
	
	//Animatoin part
	protected Scroller menuAnimationScroller = new Scroller(this.getContext(), new SmoothInterpolator());    //new LinearInterpolator()
	protected Runnable menuAnimationRunnable = new AnimationRunnable(); //Updatding UI
	protected Handler menuAnimationHandler = new Handler();
	
	//Animation constants
	private static final int menuAnimationDuration = 1000;
	private static final int menuAnimationPollingInterval = 16;
	
	
	
	
	public SlidingView(Context context) {
		super(context);
//		init();
	}

	public SlidingView(Context context, AttributeSet attrs) {
		super(context, attrs);
//		init();// TODO Auto-generated constructor stub
	}

	public SlidingView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
//		init();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
	
		if(changed)
		{
			this.calcuateChildDimmensions();
		}
		this.menu.layout(l, t, r - menuMargin, b);
		this.content.layout(l + this.currentContent, t, r + this.currentContent, b);
	}

	
	@Override
	protected void onAttachedToWindow() {
		
		super.onAttachedToWindow();
		this.menu = this.getChildAt(0);
		this.content = this.getChildAt(1);
		this.menu.setVisibility(View.GONE);
	}
	
	public void toggleMenu()
	{
		switch(this.menuCurrentState)
		{
		case CLOSED:
			touchslide = (LinearLayout)findViewById(R.id.slidelayout);
			touchslide.setVisibility(View.VISIBLE);
			touchslide.setEnabled(true);
			touchslide.setOnTouchListener(
					new OnTouchListener() {
						
						@Override
						public boolean onTouch(View v, MotionEvent event) {
							toggleMenu();
							return false;
						}
					});
			
			this.menuCurrentState = MenuState.OPENING;
			this.menu.setVisibility(View.VISIBLE);
			this.menuAnimationScroller.startScroll(0, 0, this.getMenuWidth(), 0, menuAnimationDuration);
			break;
		case OPEN:
			touchslide = (LinearLayout)findViewById(R.id.slidelayout);
			touchslide.setVisibility(View.GONE);
			touchslide.setEnabled(false);
			this.menuCurrentState = MenuState.CLOSING;
			this.menuAnimationScroller.startScroll(this.currentContent, 0, -this.currentContent, 0 , menuAnimationDuration);
			break;
		default:
			return;
				
		}
		this.menuAnimationHandler.postDelayed(this.menuAnimationRunnable, menuAnimationPollingInterval);
//		this.invalidate(); //Important needs to be redrawn
	}
	
	private void adjustContentPosition(boolean isAnimationOngoing)
	{
		int scrollerOffset = this.menuAnimationScroller.getCurrX();
		this.content.offsetLeftAndRight(scrollerOffset - this.currentContent);
		
		this.currentContent = scrollerOffset;
		
		this.invalidate();
		
		if(isAnimationOngoing)
		{
			this.menuAnimationHandler.postDelayed(this.menuAnimationRunnable, menuAnimationPollingInterval);
		}
		else
		{
			this.onMenuTransitionComplete();
		}
	}
	private void onMenuTransitionComplete()
	{
		switch(this.menuCurrentState)
		{
		case OPENING:
			this.menuCurrentState = MenuState.OPEN;
			break;
		case CLOSING:
			this.menuCurrentState = MenuState.CLOSED;
			this.menu.setVisibility(View.GONE);
			break;
		default:
			return;	
		}
	}
	protected class AnimationRunnable implements Runnable
	{

		@Override
		public void run() {
			boolean isAnimationOngoing = SlidingView.this.menuAnimationScroller.computeScrollOffset();
			
			SlidingView.this.adjustContentPosition(isAnimationOngoing);
			
		}
		
	}
	
	protected class SmoothInterpolator implements Interpolator
	{

		@Override
		public float getInterpolation(float f) {
		
			return (float)Math.pow(f-1, 5)+1;
		}
		
	}
	
	private void calcuateChildDimmensions()
	{
		this.content.getLayoutParams().height = this.getHeight();
		this.content.getLayoutParams().width = this.getWidth();
		
		this.menu.getLayoutParams().width = this.getWidth() - menuMargin;
		this.menu.getLayoutParams().height = this.getHeight();
	}
	
	private int getMenuWidth()
	{
		return this.menu.getLayoutParams().width;
	}

}
