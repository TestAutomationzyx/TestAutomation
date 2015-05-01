package com.testautomationclient;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.testautomationservice.AutoTool;

public class More extends Service {

	String TAG = "More";
	
	//定义浮动窗口布局
    LinearLayout mFloatLayout;
    WindowManager.LayoutParams wmParams;
    //创建浮动窗口设置布局参数的对象
	WindowManager mWindowManager;
	
	TextView more_icon,activity,step,result,setActivity,setStep,setResult;
	Button sureButton,cancelButton;
	EditText tEditText;
	
	Context context;
	AutoTool MyAutoTool;
	
	int TIMES;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		createView();
		context = getApplicationContext();
		MyAutoTool = new AutoTool(context,false);
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onDestroy() 
	{
		// TODO Auto-generated method stub
		super.onDestroy();
		if(mFloatLayout != null)
		{
			mWindowManager.removeView(mFloatLayout);
		}
	}
	
	@SuppressWarnings("static-access")
	private void createView() {
		wmParams = new WindowManager.LayoutParams();
		//获取WindowManagerImpl.CompatModeWrapper
		mWindowManager = (WindowManager)getApplication().getSystemService(getApplication().WINDOW_SERVICE);
		//设置window type
		wmParams.type = LayoutParams.TYPE_PHONE; 
		//设置图片格式，效果为背景透明
        wmParams.format = PixelFormat.RGBA_8888; 
        //设置浮动窗口不可聚焦（实现操作除浮动窗口外的其他可见窗口的操作）
        wmParams.flags = 
//          LayoutParams.FLAG_NOT_TOUCH_MODAL |
          LayoutParams.FLAG_NOT_FOCUSABLE
//          LayoutParams.FLAG_NOT_TOUCHABLE
          ;
        
        //调整悬浮窗显示的停靠位置中间
        wmParams.gravity = Gravity.CENTER; 
        
        // 以屏幕左上角为原点，设置x、y初始值
        wmParams.x = 0;
        wmParams.y = 0;
        
        //设置悬浮窗口长宽数据  
        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        
        LayoutInflater inflater = LayoutInflater.from(getApplication());
        //获取浮动窗口视图所在布局
        mFloatLayout = (LinearLayout) inflater.inflate(R.layout.more, null);
        //添加mFloatLayout
        mWindowManager.addView(mFloatLayout, wmParams);
        
        more_icon = (TextView) mFloatLayout.findViewById(R.id.more_icon);
        
        setActivity = (TextView) mFloatLayout.findViewById(R.id.bt_sureactivity);
        setStep = (TextView) mFloatLayout.findViewById(R.id.bt_showstep);
        setResult = (TextView) mFloatLayout.findViewById(R.id.bt_showresult);
		sureButton = (Button) mFloatLayout.findViewById(R.id.more_sure);
		cancelButton = (Button) mFloatLayout.findViewById(R.id.more_cancel);
		tEditText = (EditText) mFloatLayout.findViewById(R.id.moretimes);
  
        more_icon.setMovementMethod(LinkMovementMethod.getInstance());
        more_icon.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				//getRawX是触摸位置相对于屏幕的坐标，getX是相对于按钮的坐标
				wmParams.x = (int) event.getRawX() - more_icon.getMeasuredWidth()/2;
				Log.i(TAG, "RawX" + event.getRawX());
				Log.i(TAG, "X" + event.getX());
				//25为状态栏的高度
	            wmParams.y = (int) event.getRawY() - more_icon.getMeasuredHeight()/2 - 25;
	            Log.i(TAG, "RawY" + event.getRawY());
	            Log.i(TAG, "Y" + event.getY());
	             //刷新
	            mWindowManager.updateViewLayout(mFloatLayout, wmParams);
				return false;
			}
		});
        
        setActivity.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setActivity.setText(MyAutoTool.getCurrentActivity());				
			}
		});
        
        setStep.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			}
		});
        
        setResult.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			}
		});
        
        sureButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				method();				
			}
		});
        
        cancelButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				stopSelf();			
				Intent intent = new Intent();
				intent.setClass(context, MainActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
				context.startActivity(intent);
			}
		});
//        mFloatLayout.measure(View.MeasureSpec.makeMeasureSpec(0,
//				View.MeasureSpec.UNSPECIFIED), View.MeasureSpec
//				.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
       
	}

	protected void method() {
		// TODO Auto-generated method stub
		
	}
	
	
}
