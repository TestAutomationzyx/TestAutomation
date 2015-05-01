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
	
	//���帡�����ڲ���
    LinearLayout mFloatLayout;
    WindowManager.LayoutParams wmParams;
    //���������������ò��ֲ����Ķ���
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
