package com.tabhost;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.testautomationclient.R;

public class CallLogFragment extends Fragment {

	private String[] mItems = { "来电", "已拨", "未接" };
	Spinner mSpinner;
	Context context;
	EditText clphone,clnums;
	Button clsure,cllist,cldelete;
	Toast toast;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.context = getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.calllog, null);

		mSpinner = (Spinner) view.findViewById(R.id.typespinner);
		SpinnerAdapter mAdapter = new SpinnerAdapter(context,
				android.R.layout.simple_spinner_item, mItems);
		mSpinner.setAdapter(mAdapter);
		
		clphone = (EditText) view.findViewById(R.id.clphone);
		clnums = (EditText) view.findViewById(R.id.clnums);
		clsure = (Button) view.findViewById(R.id.clsure);
		cllist = (Button) view.findViewById(R.id.cllist);
		cldelete = (Button) view.findViewById(R.id.cldelete);
		
		clsure.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String number = clphone.getText().toString();
				Log.e("number", "number---------->"+number);
				int type = mSpinner.getSelectedItemPosition()+1;
				Log.e("type", "type---------->"+type);
				int nums = Integer.parseInt(clnums.getText().toString());				
				long startTimes = System.currentTimeMillis();
				for(int i=0; i<nums; i++){
					insertCallLog(context, number, "1000", type, "1", 50000);
				}
				toast = Toast.makeText(context, "耗时"+String.valueOf((System.currentTimeMillis()-startTimes)/1000)+"秒", Toast.LENGTH_SHORT);
				toast.show();
			}
		});
		
		cllist.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				int number = Integer.parseInt(clphone.getText().toString());
				Log.e("number", "number---------->"+number);
				int type = mSpinner.getSelectedItemPosition()+1;
				Log.e("type", "type---------->"+type);
				int nums = Integer.parseInt(clnums.getText().toString());
				long startTimes = System.currentTimeMillis();				
				for(int i=0; i<nums; i++){
					insertCallLog(context, String.valueOf(number+i), "1000", type, "1", 50000);
				}
				toast = Toast.makeText(context, "耗时"+String.valueOf((System.currentTimeMillis()-startTimes)/1000)+"秒", Toast.LENGTH_SHORT);
				toast.show();
			}
		});
		
		cldelete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				long startTimes = System.currentTimeMillis();
				deleteCallLog(context);
				toast = Toast.makeText(context, "耗时"+String.valueOf((System.currentTimeMillis()-startTimes)/1000)+"秒", Toast.LENGTH_SHORT);
				toast.show();
				
			}
		});
		
		return view;
	}

	@SuppressLint("ResourceAsColor")
	private class SpinnerAdapter extends ArrayAdapter<String> {
		Context context;
		String[] items = new String[] {};

		public SpinnerAdapter(final Context context,
				final int textViewResourceId, final String[] objects) {
			super(context, textViewResourceId, objects);
			this.items = objects;
			this.context = context;
		}

		@Override
		public View getDropDownView(int position, View convertView,
				ViewGroup parent) {

			if (convertView == null) {
				LayoutInflater inflater = LayoutInflater.from(context);
				convertView = inflater.inflate(
						android.R.layout.simple_spinner_item, parent, false);
			}

			TextView tv = (TextView) convertView
					.findViewById(android.R.id.text1);
			tv.setText(items[position]);
			tv.setGravity(Gravity.CENTER);
			tv.setTextColor(R.color.LightPink);
			tv.setTextSize(25);
			return convertView;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				LayoutInflater inflater = LayoutInflater.from(context);
				convertView = inflater.inflate(
						android.R.layout.simple_spinner_item, parent, false);
			}

			TextView tv = (TextView) convertView
					.findViewById(android.R.id.text1);
			tv.setText(items[position]);
			tv.setGravity(Gravity.CENTER);
			tv.setTextColor(R.color.LightPink);
			tv.setTextSize(30);
			return convertView;
		}
	}

	private void insertCallLog(Context context, String number, String duration,
			int type, String isnew, long i) {
		ContentValues values = new ContentValues();
		values.put(CallLog.Calls.NUMBER, number);
		values.put(CallLog.Calls.DATE, System.currentTimeMillis() + i);//来电时间
		values.put(CallLog.Calls.DURATION, duration);//通话时长
		values.put(CallLog.Calls.TYPE, type);
		// 来电：CallLog.Calls.INCOMING_TYPE (常量值：1)
		// 已拨：CallLog.Calls.OUTGOING_TYPE (常量值：2)
		// 未接：CallLog.Calls.MISSED_TYPE (常量值：3)
		values.put(CallLog.Calls.NEW, isnew);// 0已看1未看

		context.getContentResolver().insert(CallLog.Calls.CONTENT_URI, values);
	}

	private void deleteCallLog(Context context){
//		ContentResolver resolver = context.getContentResolver();
//		Cursor cursor = resolver.query(CallLog.Calls.CONTENT_URI, new String[]{"_id"}, null,  null,  null);
//		if(cursor!=null && cursor.moveToFirst()) {
//			do{
//				int id = cursor.getInt(0);
//				resolver.delete(CallLog.Calls.CONTENT_URI, "_id=?", new String[] {id + ""});
//			}while(cursor.moveToNext());
//			cursor.close();
//		}
		context.getContentResolver().delete(CallLog.Calls.CONTENT_URI, null, null);
	}
	

}
