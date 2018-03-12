/**TimeButton.java
TODO
 */
package com.mouldandroid.custom.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.mouldandroid.application.App;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Administrator
 *  自定义倒计时Button
 */
@SuppressLint("AppCompatCustomView")
public class TimeButton extends Button implements OnClickListener {
	/** 倒计时长度，这里给了默认秒数  这里默认30秒*/
	private long lenght = 30 * 1000;
	private String textafter = "重新获取（";
	private String textbefore = "获取验证码";
	private final String TIME = "time";
	private final String CTIME = "ctime";
	private OnClickListener mOnclickListener;
	private Timer t;
	private TimerTask tt;
	private long time;
	private Context mContext;
	Map<String, Long> map = new HashMap<String, Long>();

	private boolean isState = false;
	private String message = "请输入正确的手机号码";

	public TimeButton(Context context) {
		super(context);
		setOnClickListener(this);
	}

	public TimeButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		setOnClickListener(this);
	}

	Handler han = new Handler(){
		public void handleMessage(android.os.Message msg) {
//			TimeButton.this.setText(time/1000 + textafter);
			TimeButton.this.setText(textafter+time/1000+"s）");
			time -= 1000;
			if (time < 0) {
				TimeButton.this.setEnabled(true);
				TimeButton.this.setText(textbefore);
				clearTimer();
			}
		};
	};

	private void initTimer(){
		time = lenght;
		t = new Timer();
		tt = new TimerTask() {
			@Override
			public void run() {
				han.sendEmptyMessage(0x01);
			}
		};
	}
	
	private void clearTimer(){
//		ToastUtil.showToast(mContext, "计时结束");
		if (tt != null) {
			tt.cancel();
			tt = null;
		}
		if (t != null) 
			t.cancel();
		t = null;
	}
	
	@Override
	public void setOnClickListener(OnClickListener l) {
        if (l instanceof TimeButton) {
            super.setOnClickListener(l);
        } else
            this.mOnclickListener = l;
    }

    public boolean setIsState(boolean isState){
		if (isState){
			isState = true;
		}else {
			isState = false;
		}
		return isState;
	};

	@Override
	public void onClick(View v) {
//		if (isState){
			if (mOnclickListener != null)
				mOnclickListener.onClick(v);
			initTimer();
//		this.setText(time/1000 + textafter);
			this.setText(textafter + time/1000 +"s）");
			this.setEnabled(false);
			t.schedule(tt, 0,1000);
			// t.scheduleAtFixedRate(task, delay, period);
//		}else {
//			ToastUtil.showToast(mContext,message.toString());
//		}
	}
	
	/**
	 * 和activity的onDestroy()方法同步
	 */
	public void onDestroy(){
		if (App.map == null)
			App.map = new HashMap<String, Long>();
		App.map.put(TIME, time);
		App.map.put(CTIME, System.currentTimeMillis());
		clearTimer();
	}
	
	 /**
     * 和activity的onCreate()方法同步
     */
    public void onCreate(Bundle bundle) {
    	if (App.map == null) 
			return;
    	if (App.map.size() <= 0) //这里表示没有上次未完成的计时
			return;
    	long time = System.currentTimeMillis() - App.map.get(CTIME) - App.map.get(TIME);
    	App.map.clear();
    	if (time > 0) 
			return;
    	else {
			initTimer();
			this.time = Math.abs(time);
			t.schedule(tt, 0,1000);
//			this.setText(time + textafter);
			this.setText(textafter+time +"s）");
			this.setEnabled(false);
		}
    }
	
    /**
     * 设置点击之前的文本
     */
	public TimeButton setTextBefore(String text0){
		this.textbefore = text0;
		this.setText(textbefore);
		return this;
	}
	
	/**
	 * 设置倒计时长度
	 * 
	 * 	时间 默认毫秒
	 */
	public TimeButton setLenght(long lenght){
		this.lenght = lenght;
		return this;
	}
}
