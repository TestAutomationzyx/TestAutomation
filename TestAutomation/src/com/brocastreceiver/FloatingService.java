package com.brocastreceiver;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.testautomationclient.R;

public class FloatingService extends Service {

	//定义浮动窗口布局
    LinearLayout mFloatLayout;
    WindowManager.LayoutParams wmParams;
    //创建浮动窗口设置布局参数的对象
	WindowManager mWindowManager;
	
	ImageView mFloatView;
	TextView tv;
	
//	private  String TAG = "FloatingService";
	
	@Override
	public void onCreate() 
	{
		// TODO Auto-generated method stub
		super.onCreate();
		createFloatView();		
	}

	@Override
	public IBinder onBind(Intent intent)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("static-access")
	private void createFloatView()
	{
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
        
        //调整悬浮窗显示的停靠位置为左侧置顶
        wmParams.gravity = Gravity.LEFT | Gravity.TOP; 
        
        // 以屏幕左上角为原点，设置x、y初始值
        wmParams.x = 0;
        wmParams.y = 0;

        /*// 设置悬浮窗口长宽数据
        wmParams.width = 200;
        wmParams.height = 80;*/
        
        //设置悬浮窗口长宽数据  
        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        
        LayoutInflater inflater = LayoutInflater.from(getApplication());
        //获取浮动窗口视图所在布局
        mFloatLayout = (LinearLayout) inflater.inflate(R.layout.float_layout, null);
        //添加mFloatLayout
        mWindowManager.addView(mFloatLayout, wmParams);
        
//        Log.i(TAG, "mFloatLayout-->left" + mFloatLayout.getLeft());
//        Log.i(TAG, "mFloatLayout-->right" + mFloatLayout.getRight());
//        Log.i(TAG, "mFloatLayout-->top" + mFloatLayout.getTop());
//        Log.i(TAG, "mFloatLayout-->bottom" + mFloatLayout.getBottom());      
//        
        //浮动窗口按钮
        mFloatView = (ImageView)mFloatLayout.findViewById(R.id.float_id);
        tv = (TextView) mFloatLayout.findViewById(R.id.textView1);
		String string = "测试即将开始，正在初始化数据，请耐心等候。。。。。。。。。。。。。。。。。。";
		CharSequence charSequence = Html.fromHtml(string);
		tv.setText(charSequence);
		tv.setMovementMethod(LinkMovementMethod.getInstance());
		
        mFloatLayout.measure(View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED), View.MeasureSpec
				.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
//        Log.i(TAG, "Width/2--->" + mFloatView.getMeasuredWidth()/2);
//        Log.i(TAG, "Height/2--->" + mFloatView.getMeasuredHeight()/2);
       
        mFloatView.setOnClickListener(new OnClickListener() 
        {
			
			@Override
			public void onClick(View v) 
			{
				clickIcon();
			}
		});        
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
	
	public String getTextViewContent(){
		return tv.getText().toString();
	}
	
	public void setTextViewContent(String string){
		CharSequence charSequence = Html.fromHtml(string);
		tv.setText(charSequence);
	}
	
	public void setTextViewSize(int Wpixels,int Hpixels){
		tv.setWidth(Wpixels);
		tv.setHeight(Hpixels);
	}
	
	public void clickIcon(){
		
	}
	
	public void setIconSrc(int resId){
		mFloatView.setImageResource(resId);
	}
	
	public void setIconMoveable(boolean moveable){
		if(moveable){
			//设置监听浮动窗口的触摸移动
	        mFloatView.setOnTouchListener(new OnTouchListener() 
	        {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) 
				{
					// TODO Auto-generated method stub
					//getRawX是触摸位置相对于屏幕的坐标，getX是相对于按钮的坐标
					wmParams.x = (int) event.getRawX() - mFloatView.getMeasuredWidth()/2;
					//Log.i(TAG, "Width/2--->" + mFloatView.getMeasuredWidth()/2);
//					Log.i(TAG, "RawX" + event.getRawX());
//					Log.i(TAG, "X" + event.getX());
					//25为状态栏的高度
		            wmParams.y = (int) event.getRawY() - mFloatView.getMeasuredHeight()/2 - 25;
		           // Log.i(TAG, "Width/2--->" + mFloatView.getMeasuredHeight()/2);
//		            Log.i(TAG, "RawY" + event.getRawY());
//		            Log.i(TAG, "Y" + event.getY());
		             //刷新
		            mWindowManager.updateViewLayout(mFloatLayout, wmParams);
					return false;
				}
			});
		}
	}
	
	public void setIconUnvisual(){
		mFloatView.setVisibility(View.GONE);
	}
	
	public void setTextViewUnvisual(){
		tv.setVisibility(View.GONE);
	}
	
	public void setTextViewLine(int i){
		tv.setLines(i);
	}
	
	public void setTextColor(int color){
		tv.setTextColor(color);
	}
	
	public void setTextBackground(int color){
		tv.setBackgroundColor(color);
	}

}
