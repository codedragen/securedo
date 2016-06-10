package com.eetrust.securedoc.ui;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.eetrust.securedoc.R;

public class MdCheckbox extends View implements View.OnClickListener {

	private Paint borderPaint;
	private Paint bitmapPaint;
	private int size = 22;
	private int bitmapColor = 0xFF3F51B5;
	private int borderColor = 0xffffff;
	private boolean checked;
	private ObjectAnimator checkAnim;
	private float progress;
	private Paint bitmapEraser;
	private Drawable checkDrawable;
	private Bitmap drawBitmap;
	private Canvas bitmapCanvas;

	public void setChecked(boolean checked) {
		if (checked == this.checked) {
			return;
		}
		this.checked = checked;
		addAnim(checked);

	}

	public boolean isChecked() {

		return checked;
	}

	private void addAnim(boolean isChecked) {
		checkAnim = ObjectAnimator.ofFloat(this, "progress", isChecked ? 1.0f
				: 0.0f);
		checkAnim.setDuration(300);
		checkAnim.start();
	}

	public void setProgress(float progress) {
		this.progress = progress;
		invalidate();
	}

	public float getProgress() {
		return progress;
	}

	public MdCheckbox(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		TypedArray ta = getContext().obtainStyledAttributes(attrs,
				R.styleable.CheckBox_Sample);
		size = ta.getDimensionPixelSize(R.styleable.CheckBox_Sample_size,
				dp(size));
		bitmapColor = ta.getColor(R.styleable.CheckBox_Sample_color_background,
				bitmapColor);
		borderColor = ta.getColor(R.styleable.CheckBox_Sample_color_border,
				borderColor);
		borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		borderPaint.setColor(borderColor);
		borderPaint.setStyle(Style.STROKE);
		borderPaint.setStrokeWidth(dp(3));
		bitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		bitmapPaint.setColor(bitmapColor);
		bitmapEraser = new Paint(Paint.ANTI_ALIAS_FLAG);
		// bitmapEraser.setColor(Color.parseColor("#00000000"));
		bitmapEraser.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
		checkDrawable = context.getResources().getDrawable(R.mipmap.check);

		drawBitmap = Bitmap.createBitmap(dp(size), dp(size),
				Bitmap.Config.ARGB_8888);
		bitmapCanvas = new Canvas(drawBitmap);
		setOnClickListener(this);
	}

	public MdCheckbox(Context context, AttributeSet attrs) {
		this(context, attrs, 0);

	}

	public MdCheckbox(Context context) {
		this(context, null);

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int newSpec = MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY);
		super.onMeasure(newSpec, newSpec);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		int r = getMeasuredHeight() / 2;

		canvas.drawCircle(getMeasuredHeight() / 2, getMeasuredHeight() / 2, r
				- dp(5), borderPaint);
		drawBitmap.eraseColor(0);
		bitmapCanvas.drawCircle(getMeasuredHeight() / 2,
				getMeasuredHeight() / 2, r - dp(1), bitmapPaint);
		bitmapCanvas.drawCircle(getMeasuredHeight() / 2,
				getMeasuredHeight() / 2, (r - dp(1)) * (1 - progress),
				bitmapEraser);
		canvas.drawBitmap(drawBitmap, 0, 0, null);
		if (progress == 1) {

			int w = checkDrawable.getIntrinsicWidth();
			int h = checkDrawable.getIntrinsicHeight();
			int x = (getMeasuredWidth() - w) / 2;
			int y = (getMeasuredHeight() - h) / 2;

			checkDrawable.setBounds(x, y, x + w, y + h);
			checkDrawable.draw(canvas);
		}

	}

	private int dp(int size) {
		float i = getContext().getResources().getDisplayMetrics().density;
		return (int) (size * i);
	}

	@Override
	public void onClick(View v) {
		setChecked(!checked);
	}
}
