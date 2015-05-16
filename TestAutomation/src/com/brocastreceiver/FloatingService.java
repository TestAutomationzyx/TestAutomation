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

	//���帡�����ڲ���
    LinearLayout mFloatLayout;
    WindowManager.LayoutParams wmParams;
    //���������������ò��ֲ����Ķ���
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
		//��ȡWindowManagerImpl.CompatModeWrapper
		mWindowManager = (WindowManager)getApplication().getSystemService(getApplication().WINDOW_SERVICE);
		//����window type
		wmParams.type = LayoutParams.TYPE_PHONE; 
		//����ͼƬ��ʽ��Ч��Ϊ����͸��
        wmParams.format = PixelFormat.RGBA_8888; 
        //���ø������ڲ��ɾ۽���ʵ�ֲ���������������������ɼ����ڵĲ�����
        wmParams.flags = 
//          LayoutParams.FLAG_NOT_TOUCH_MODAL |
          LayoutParams.FLAG_NOT_FOCUSABLE
//          LayoutParams.FLAG_NOT_TOUCHABLE
          ;
        
        //������������ʾ��ͣ��λ��Ϊ����ö�
        wmParams.gravity = Gravity.LEFT | Gravity.TOP; 
        
        // ����Ļ���Ͻ�Ϊԭ�㣬����x��y��ʼֵ
        wmParams.x = 0;
        wmParams.y = 0;

        /*// �����������ڳ�������
        wmParams.width = 200;
        wmParams.height = 80;*/
        
        //�����������ڳ�������  
        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        
        LayoutInflater inflater = LayoutInflater.from(getApplication());
        //��ȡ����������ͼ���ڲ���
        mFloatLayout = (LinearLayout) inflater.inflate(R.layout.float_layout, null);
        //���mFloatLayout
        mWindowManager.addView(mFloatLayout, wmParams);
        
//        Log.i(TAG, "mFloatLayout-->left" + mFloatLayout.getLeft());
//        Log.i(TAG, "mFloatLayout-->right" + mFloatLayout.getRight());
//        Log.i(TAG, "mFloatLayout-->top" + mFloatLayout.getTop());
//        Log.i(TAG, "mFloatLayout-->bottom" + mFloatLayout.getBottom());      
//        
        //�������ڰ�ť
        mFloatView = (ImageView)mFloatLayout.findViewById(R.id.float_id);
        tv = (TextView) mFloatLayout.findViewById(R.id.textView1);
		String string = "���Լ�����ʼ�����ڳ�ʼ�����ݣ������ĵȺ򡣡���������������������������������";
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
			//���ü����������ڵĴ����ƶ�
	        mFloatView.setOnTouchListener(new OnTouchListener() 
	        {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) 
				{
					// TODO Auto-generated method stub
					//getRawX�Ǵ���λ���������Ļ�����꣬getX������ڰ�ť������
					wmParams.x = (int) event.getRawX() - mFloatView.getMeasuredWidth()/2;
					//Log.i(TAG, "Width/2--->" + mFloatView.getMeasuredWidth()/2);
//					Log.i(TAG, "RawX" + event.getRawX());
//					Log.i(TAG, "X" + event.getX());
					//25Ϊ״̬���ĸ߶�
		            wmParams.y = (int) event.getRawY() - mFloatView.getMeasuredHeight()/2 - 25;
		           // Log.i(TAG, "Width/2--->" + mFloatView.getMeasuredHeight()/2);
//		            Log.i(TAG, "RawY" + event.getRawY());
//		            Log.i(TAG, "Y" + event.getY());
		             //ˢ��
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
