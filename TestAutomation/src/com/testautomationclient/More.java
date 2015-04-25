package com.testautomationclient;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

public class More extends Service {

	String TAG = "More";
	
	//���帡�����ڲ���
    LinearLayout mFloatLayout;
    WindowManager.LayoutParams wmParams;
    //���������������ò��ֲ����Ķ���
	WindowManager mWindowManager;
	
	TextView more_icon,activity,step,result,setActivity,setStep,setResult;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		createView();
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
        
        //������������ʾ��ͣ��λ���м�
        wmParams.gravity = Gravity.CENTER; 
        
        // ����Ļ���Ͻ�Ϊԭ�㣬����x��y��ʼֵ
        wmParams.x = 0;
        wmParams.y = 0;
        
        //�����������ڳ�������  
        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        
        LayoutInflater inflater = LayoutInflater.from(getApplication());
        //��ȡ����������ͼ���ڲ���
        mFloatLayout = (LinearLayout) inflater.inflate(R.layout.more, null);
        //���mFloatLayout
        mWindowManager.addView(mFloatLayout, wmParams);
        
        more_icon = (TextView) mFloatLayout.findViewById(R.id.more_icon);
//        activity = (TextView) mFloatLayout.findViewById(R.id.txt_getactivity);
//        step = (TextView) mFloatLayout.findViewById(R.id.txt_step);
//        result = (TextView) mFloatLayout.findViewById(R.id.txt_result);
        
        setActivity = (TextView) mFloatLayout.findViewById(R.id.bt_sureactivity);
        setStep = (TextView) mFloatLayout.findViewById(R.id.bt_showstep);
        setResult = (TextView) mFloatLayout.findViewById(R.id.bt_showresult);
		
  
        more_icon.setMovementMethod(LinkMovementMethod.getInstance());
        more_icon.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				//getRawX�Ǵ���λ���������Ļ�����꣬getX������ڰ�ť������
				wmParams.x = (int) event.getRawX() - more_icon.getMeasuredWidth()/2;
				Log.i(TAG, "RawX" + event.getRawX());
				Log.i(TAG, "X" + event.getX());
				//25Ϊ״̬���ĸ߶�
	            wmParams.y = (int) event.getRawY() - more_icon.getMeasuredHeight()/2 - 25;
	            Log.i(TAG, "RawY" + event.getRawY());
	            Log.i(TAG, "Y" + event.getY());
	             //ˢ��
	            mWindowManager.updateViewLayout(mFloatLayout, wmParams);
				return false;
			}
		});
        mFloatLayout.measure(View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED), View.MeasureSpec
				.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
       
	}
	
	
}
