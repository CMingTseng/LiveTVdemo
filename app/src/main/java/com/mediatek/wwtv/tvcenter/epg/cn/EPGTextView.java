package com.mediatek.wwtv.tvcenter.epg.cn;

import com.mediatek.wwtv.tvcenter.R;
import com.mediatek.wwtv.tvcenter.epg.EPGProgramInfo;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
import android.text.TextUtils;

public class EPGTextView extends TextView {
	private EPGProgramInfo tvProgramInfo;

	public EPGTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public EPGTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public EPGTextView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public EPGTextView(Context context, EPGProgramInfo mTVProgramInfo) {
		super(context);
		this.tvProgramInfo = mTVProgramInfo;
//		this.setSingleLine(true);
//		this.setGravity(Gravity.CENTER);
//		this.setTextSize(14);
//		this.setTextColor(Color.WHITE);
		this.setEllipsize(TextUtils.TruncateAt.END);
	}

	public void setBackground(boolean isDrawLeftIcon,
			boolean isDrawRightwardIcon, boolean selected, boolean isBgHighLight) {
		if (isBgHighLight) {
			if (isDrawRightwardIcon && isDrawLeftIcon) {
				if (selected) {
					setBackgroundResource(R.drawable.epg_left_right);
				} else {
					setBackgroundResource(R.drawable.epg_img_pbb_cate_1_lr_ar);
				}
			} else if (isDrawLeftIcon) {
				if (selected) {
					setBackgroundResource(R.drawable.epg_left_hi);
				} else {
					setBackgroundResource(R.drawable.epg_img_pbb_cate_1_l_ar);
				}
			} else if (isDrawRightwardIcon) {
				if (selected) {
					setBackgroundResource(R.drawable.epg_right_hi);
				} else {
					setBackgroundResource(R.drawable.epg_img_pbb_cate_1_r_ar);
				}
			} else {
				if (selected) {
                                        setBackgroundResource(R.drawable.epg_no_left_right_hi);
				} else {
					setBackgroundResource(R.drawable.epg_img_pbb_cate_1);
				}
			}
		} else {
			if (isDrawRightwardIcon && isDrawLeftIcon) {
				if (selected) {
					setBackgroundResource(R.drawable.epg_left_right);
				} else {
					setBackgroundResource(R.drawable.epg_left_right_no);
				}
			} else if (isDrawLeftIcon) {
				if (selected) {
					setBackgroundResource(R.drawable.epg_left_hi);
				} else {
					setBackgroundResource(R.drawable.epg_left_no);
				}
			} else if (isDrawRightwardIcon) {
				if (selected) {
					setBackgroundResource(R.drawable.epg_right_hi);
				} else {
					setBackgroundResource(R.drawable.epg_right_no);
				}
			} else {
				if (selected) {
                                        setBackgroundResource(R.drawable.epg_no_left_right_hi);
				} else {
                                        setBackgroundResource(R.drawable.epg_no_left_right_normal);
				}
			}
		}
	}
	
	public void setProgramInfo(EPGProgramInfo tvProgramInfo) {
		this.tvProgramInfo = tvProgramInfo;
	}

	public String getShowTitle() {
		return tvProgramInfo != null ? tvProgramInfo.getTitle() : null;

	}

	public int getMainTypeStr() {
		if (tvProgramInfo != null) {
			return tvProgramInfo.getMainType();
		}
		return -1;
	}
	
	public int getSubTypeStr() {
		if (tvProgramInfo != null) {
			return tvProgramInfo.getSubType();
		}
		return -1;
	}

	// @Override
	// protected void onLayout(boolean changed, int left, int top, int right,
	// int bottom) {
	// super.onLayout(changed, left, top, right, bottom);
	// }
	//
	// @Override
	// protected void onSizeChanged(int w, int h, int oldw, int oldh) {
	// // TODO Auto-generated method stub
	// super.onSizeChanged(w, h, oldw, oldh);
	//
	// }

	//
	// @Override
	// protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	// super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	// }

}
