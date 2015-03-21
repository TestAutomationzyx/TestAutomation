package com.tabhost;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.testautomationclient.R;

public class MessageFragment extends Fragment {

	public static Uri mSmsUri = Uri.parse("content://sms/inbox");
	
	Context context;
	Button msmsure,msmlist,msmdelete;
	EditText msmphoneet,msmconetet,msmnumet;
	Toast toast;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.context = getActivity();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.message, null);
		
		msmphoneet = (EditText) view.findViewById(R.id.msmphoneet);
		msmconetet = (EditText) view.findViewById(R.id.msmconetet);
		msmnumet = (EditText) view.findViewById(R.id.msmnumet);
		
		msmsure = (Button) view.findViewById(R.id.msmsure);
		msmlist = (Button) view.findViewById(R.id.msmlist);
		msmdelete = (Button) view.findViewById(R.id.msmdelete);
		
		msmsure.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String address = msmphoneet.getText().toString();
				String body = msmconetet.getText().toString();
				int nums = Integer.parseInt(msmnumet.getText().toString());
				long startTimes = System.currentTimeMillis();
				for(int i=0; i<nums; i++)
					insertSMS(context, address, body);
				toast = Toast.makeText(context, "耗时"+String.valueOf((System.currentTimeMillis()-startTimes)/1000)+"秒", Toast.LENGTH_SHORT);
				toast.show();
			}
		});
		
		msmlist.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String address = msmphoneet.getText().toString();
				String body = msmconetet.getText().toString();
				int nums = Integer.parseInt(msmnumet.getText().toString());
				long startTimes = System.currentTimeMillis();
				for(int i=0; i<nums; i++){
					insertSMS(context, address+i, body);
				}
				toast = Toast.makeText(context, "耗时"+String.valueOf((System.currentTimeMillis()-startTimes)/1000)+"秒", Toast.LENGTH_SHORT);
				toast.show();				
			}
		});
		
		msmdelete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				long startTimes = System.currentTimeMillis();
				deleteSMS(context);	
				toast = Toast.makeText(context, "耗时"+String.valueOf((System.currentTimeMillis()-startTimes)/1000)+"秒", Toast.LENGTH_SHORT);
				toast.show();	
			}
		});
		
		return view;
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
	private void insertSMS(Context context,String address,String body) 
	{
	        ContentValues values = new ContentValues();
	        values.put("address", address);//来信号码
	        values.put("body", body);//来信内容
	        values.put("date", 20111101);    	
	        values.put("read", 0);
	        values.put("type", 1);
	        values.put("service_center", "+8613010776500");
	    	
	        context.getContentResolver().insert(mSmsUri, values);
	}
	
	public void deleteSMS(Context context) {
		context.getContentResolver().delete(Uri.parse("content://sms"), null, null);
	}
	
}
