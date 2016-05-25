package com.ff.zffcircleprogressbar.ui;

import com.ff.zffcircleprogressbar.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;

public class CircleProgressBar extends View {
	private int mMax = 100;
	private int mProgress = 0;
	private int mColor = Color.parseColor("#cccccccc");
	private int mPaintSize = 2;
	private int mInterval = 6;
	private Paint mPaint;
	private int mSize;
	
	public CircleProgressBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressBar);
		mColor = typedArray.getColor(R.styleable.CircleProgressBar_circleColor, Color.parseColor("#cccccccc"));
		typedArray.recycle();
		init();
	}

	private void init() {
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
		mPaint.setStrokeWidth(mPaintSize);
		mPaint.setColor(mColor);
		mPaint.setStyle(Style.STROKE);
		mPaint.setStrokeCap(Cap.ROUND);
	}

	public void setMax(int max){
		this.mMax = max;
	}
	
	public void setProgress(int progress){
		this.mProgress = progress;
		invalidate();
		if(this.mMax == this.mProgress){
			doDoneAnimation();
		}
	}
	
	private void doDoneAnimation() {
		ScaleAnimation scaleAnim = new ScaleAnimation(1.0f, 1.1f, 1.0f, 1.1f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		scaleAnim.setDuration(500);
		scaleAnim.setRepeatMode(Animation.REVERSE);
		scaleAnim.setRepeatCount(1);
		
		AlphaAnimation alphaAnim = new AlphaAnimation(1.0f, 0.5f);
		alphaAnim.setDuration(500);
		alphaAnim.setRepeatMode(Animation.REVERSE);
		alphaAnim.setRepeatCount(1);
		
		AnimationSet set = new AnimationSet(false);
		set.addAnimation(scaleAnim);
		set.addAnimation(alphaAnim);
		startAnimation(set);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = MeasureSpec.getSize(widthMeasureSpec);
		int heigh = MeasureSpec.getSize(heightMeasureSpec);
		mSize = width < heigh ? width : heigh;
		
		mInterval = mSize / 20;
		int measuredWidth = MeasureSpec.makeMeasureSpec(mSize, MeasureSpec.EXACTLY);
		int measuredHeigh = MeasureSpec.makeMeasureSpec(mSize, MeasureSpec.EXACTLY);
		setMeasuredDimension(measuredWidth, measuredHeigh);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		drawStrokeCircle(canvas);
		drawFillCircle(canvas);
	}

	private void drawFillCircle(Canvas canvas) {
		RectF oval = new RectF(0 + mPaintSize / 2 + mInterval, 0 + mPaintSize / 2 + mInterval, 
				mSize - mPaintSize / 2 - mInterval, mSize - mPaintSize / 2 - mInterval);
		mPaint.setStyle(Style.FILL);
		canvas.drawArc(oval, -90, 360 * (mProgress * 1.0f / mMax), true, mPaint);
	}

	private void drawStrokeCircle(Canvas canvas) {
		mPaint.setStyle(Style.STROKE);
		RectF oval = new RectF(0 + mPaintSize / 2, 0 + mPaintSize / 2, 
				mSize - mPaintSize / 2, mSize - mPaintSize / 2);
		canvas.drawArc(oval, 0, 360, false, mPaint);
	}
}
